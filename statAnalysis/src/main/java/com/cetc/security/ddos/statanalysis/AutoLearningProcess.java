package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.controller.adapter.TrafficTuple;
import com.cetc.security.ddos.persistence.AutoLearnBaseValueEntity;
import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.dao.AutoLearnBaseValueDao;
import com.cetc.security.ddos.persistence.service.AutoLearnBaseValueService;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

/**
 * Created by lb on 2015/7/23.
 */
public class AutoLearningProcess {
    private static Logger logger = AntiLogger.getLogger(AutoLearningProcess.class);
    /* learning statuses */
    public enum Status {
        INIT,
        LEARNING, 			// Averages are updated, but attacks are not suspected
        ACTIVE				// Attacks may be suspected
    }
    public static final long LEARNINT_PERIOD = 180*1000;
    //public static final long LEARNINT_PERIOD = (7*24*60*60+3600)*1000;//学习时间为7天零一小时
    public static final int WEEKDAY = 7;
    public static final int DAY_HOUR = 24;

    private static long maxAveragePeriod  = 3600;
    private static long maxThisPeriod  = 300;
    private static long minBaseValue = 1024;//单位B/s

    private TrafficTuple[][] average;	   // bytes/packets per second 平均速率
    private Status status;

    //用于数据库中基线值更新的变量
    private int week_backup = -1;
    private int hour_backup = -1;
    private double Bps_backup;
    private double pps_backup;


    private static AutoLearnBaseValueService baseValueService = PersistenceEntry.getInstance().getAutoLearnBaseValueService();

    protected AutoLearningProcess(String poname,int protocol,int flowid) {
        average = new TrafficTuple[WEEKDAY][DAY_HOUR];

        AutoLearnBaseValueEntity entity;
        int cnt=0;
        for(int i=0; i < WEEKDAY;i++) {
            for (int j=0; j < DAY_HOUR;j++) {
                entity = baseValueService.getBaseValue(flowid, i, j);
                if(entity == null) {
                    average[i][j] = new TrafficTuple();
                    logger.debug(poname + ":protocol " + protocol + ",week " + i + "hour " + j + " ,init average to 0.");
                } else {
                    average[i][j] = new TrafficTuple(Double.parseDouble(entity.getBps()),
                            Double.parseDouble(entity.getPps()),maxAveragePeriod);
                    logger.debug(poname + ":protocol " + protocol + ",week " + i + "hour " + j + " ,init average Bps to "
                            + average[i][j].getBytes() + " ,pps to" + average[i][j].getPackets());
                    cnt++;
                }
            }
        }

        if (cnt == WEEKDAY*DAY_HOUR) {
            status = Status.ACTIVE;
            logger.info(poname + ":protocol " + protocol +" has been active");
        } else {
            status = Status.INIT;
            if (cnt != 0) {
                logger.error(poname + ":protocol " + protocol +" status is init "+cnt);
            }
        }

    }

    public Status getStatus() {
        return status;
    }

    public TrafficTuple[][] getAverage() {
        return average;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public void allBaseValueSave(StatReport statsReport) {
        AutoLearnBaseValueEntity baseValueEntity;
        double Bps;
        for(int i=0; i < WEEKDAY;i++) {
            for (int j=0; j < DAY_HOUR;j++) {
                baseValueEntity = new AutoLearnBaseValueEntity();
                baseValueEntity.setPoname(statsReport.getPoKey());
                baseValueEntity.setProtocal(statsReport.getProtocol());
                baseValueEntity.setFlowid(statsReport.getFlowid());
                baseValueEntity.setWeek(i);
                baseValueEntity.setHour(j);
                if (average[i][j].getBytes() < minBaseValue) {
                    Bps = minBaseValue;
                } else {
                    Bps = average[i][j].getBytes();
                }
                baseValueEntity.setBps(String.valueOf(Bps));
                baseValueEntity.setPps(String.valueOf(average[i][j].getPackets()));

                baseValueService.addBaseValue(baseValueEntity);
            }
        }

        week_backup = statsReport.getWeekday();
        hour_backup = statsReport.getHour();
        logger.info(statsReport.getPoKey() + ":" + statsReport.getTrafficFloorKey() + " save all base value");
    }

    public void baseValueUpdate(StatReport statsReport,int week,int hour,TrafficTuple update_average) {
        if (week_backup == -1) {
            Bps_backup = update_average.getBytes();
            pps_backup = update_average.getPackets();
            week_backup = week;
            hour_backup = hour;
            return;
        }

        if (hour_backup != hour) {
            AutoLearnBaseValueEntity baseValueEntity;
            baseValueEntity = baseValueService.getBaseValue(statsReport.getFlowid(), week_backup, hour_backup);

            if (baseValueEntity == null) {
                logger.error("error for" + statsReport.getPoKey() + ":" + statsReport.getTrafficFloorKey() + " baseValueDao.find null" +
                        ",weekday:" + week_backup + ",hour:" + hour_backup);
            }
            baseValueEntity.setBps(String.valueOf(Bps_backup));
            baseValueEntity.setPps(String.valueOf(pps_backup));
            baseValueService.updateBaseValue(baseValueEntity);
            logger.info(statsReport.getPoKey() + ":" + statsReport.getTrafficFloorKey() + " update base value"+
                ",weekday:"+week_backup+",hour:"+hour_backup);
        }

        Bps_backup = update_average.getBytes();
        pps_backup = update_average.getPackets();
        week_backup = week;
        hour_backup = hour;
    }
    public void updateAvarage(TrafficTuple update_average, TrafficTuple latest_rate) {
        double latest_rate_Bps = latest_rate.getBytes()+minBaseValue;//计算基线值时速率均增加1000bps，即125Bps
        double latest_rate_pps = latest_rate.getPackets();

        if (update_average.isZero()) {//第一次更新平均值
            update_average.setTrafficTuple2(latest_rate_Bps,latest_rate_pps,latest_rate.getDuration());

            logger.debug("update average firest," + latest_rate_Bps + "-" + latest_rate_pps + "-" + update_average.getDuration());
            //logger.debug("update average firest,%f-%f-%d",
            //        update_average.getBytes(), update_average.getPackets(), update_average.getDuration());
            return;
        }

        if (update_average.getDuration() > maxAveragePeriod) {//平均速率所占比例不能太大，否则后续的速率可能起不到对平均速率的修正作用
            update_average.setDuration(maxAveragePeriod);
        }
        if (latest_rate.getDuration() > maxThisPeriod) {//防止本次速率所占比例过大
            latest_rate.setDuration(maxThisPeriod);
        }

        long average_period_Time = update_average.getDuration();
        long latest_period_time = latest_rate.getDuration();
        long sumTime = average_period_Time + latest_period_time;
        double rate_bytes = (update_average.getBytes() * average_period_Time + latest_rate_Bps * latest_period_time)/sumTime;
        double rate_packets = (update_average.getPackets() * average_period_Time + latest_rate_pps * latest_period_time)/sumTime;

        update_average.setTrafficTuple2(rate_bytes, rate_packets, sumTime);
    }



}
