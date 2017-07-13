package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.FlowDataService;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

/**
 * Created by lb on 2015/8/12.
 */
//流量统计保存，用于流量展示
public class FlowStatSave {
    private static Logger logger = AntiLogger.getLogger(FlowStatSave.class);
    private long firstTimestrap = 0;
    private static long halfHour = 1800000;//30*60*1000;
    private static FlowDataService flowData = PersistenceEntry.getInstance().getFlowDataService();
    private static final String HOUR_TABLE_NAME="hour";
    private static final String DAY_TABLE_NAME="day";
    private static final int max_num_hourTable = 360;//半小时内采集的最大次数，假设采集最小时间间隔为5秒
    private FlowRate[] flowRate;
    private int index = 0;

    //周流量展示，每4小时计算出一个平均速率，以下变量用于求4小时的平均速率
    private static final int max_num_ForWeek = 8;//半小时保存一个速率，4小时保存8个
    private FlowRate[] flowRateForWeek;
    private int indexForWeek = 0;
    private static final String WEEK_TABLE_NAME="week";

    //月流量展示，每天计算出一个平均速率，以下变量用于求一天的平均速率
    private static final int max_num_ForMonth = 6;//4小时保存一个速率，一天有6个
    private FlowRate[] flowRateForMonth;
    private int indexForMonth = 0;
    private static final String MONTH_TABLE_NAME="month";

    //年流量展示，每周计算出一个平均速率，以下变量用于求一周的平均速率
    private static final int max_num_ForYear = 7;//一天保存一个速率，一周有7个
    private FlowRate[] flowRateForYear;
    private int indexForYear = 0;
    private static final String YEAR_TABLE_NAME="year";

    public FlowStatSave(String poname,int flowid) {
        int i;

        flowRate = new FlowRate[max_num_hourTable];
        for (i=0; i < max_num_hourTable;i++) {
            flowRate[i] = new FlowRate();
        }

        flowRateForWeek = new FlowRate[max_num_ForWeek];
        for (i=0; i < max_num_ForWeek;i++) {
            flowRateForWeek[i] = new FlowRate();
        }

        flowRateForMonth = new FlowRate[max_num_ForMonth];
        for (i=0; i < max_num_ForMonth;i++) {
            flowRateForMonth[i] = new FlowRate();
        }

        flowRateForYear = new FlowRate[max_num_ForYear];
        for (i=0; i < max_num_ForYear;i++) {
            flowRateForYear[i] = new FlowRate();
        }

        flowData.addFlowData(poname,flowid,HOUR_TABLE_NAME,0,0,0);
        flowData.addFlowData(poname,flowid,DAY_TABLE_NAME,0,0,0);
        flowData.addFlowData(poname,flowid,WEEK_TABLE_NAME,0,0,0);
        flowData.addFlowData(poname,flowid,MONTH_TABLE_NAME,0,0,0);
        flowData.addFlowData(poname,flowid,YEAR_TABLE_NAME,0,0,0);

        index = 0;
        indexForWeek = 0;
        indexForMonth = 0;
        indexForYear = 0;
    }

    private FlowRate calcAverageRate_hour() {
        FlowRate rate = new FlowRate();

        int i;
        double total_Bps = 0;
        double total_pps = 0;

        for (i=0;i<index;i++) {
            total_Bps += flowRate[i].getBps();
            total_pps += flowRate[i].getPps();
        }

        rate.setBps(total_Bps/index);
        rate.setPps(total_pps/index);
        rate.setTimestarp((flowRate[0].getTimestarp() + flowRate[index - 1].getTimestarp()) / 2);

        return rate;
    }

    private FlowRate calcAverageRate(FlowRate[] rate,int totalNum) {
        FlowRate averageRate = new FlowRate();

        int i;
        double total_Bps = 0;
        double total_pps = 0;

        for (i=0;i<totalNum;i++) {
            total_Bps += rate[i].getBps();
            total_pps += rate[i].getPps();
        }

        averageRate.setBps(total_Bps/totalNum);
        averageRate.setPps(total_pps/totalNum);
        //averageRate.setTimestarp((rate[0].getTimestarp()+rate[totalNum-1].getTimestarp())/2);
        averageRate.setTimestarp(rate[totalNum-1].getTimestarp());

        return averageRate;
    }

    private void rateSaveForYear(String poname,int flowid,double Bps,double pps,long time) {
        FlowRate averageRate;

        flowRateForYear[indexForYear].SetAll(Bps, pps, time);
        indexForYear++;

        if (indexForYear >= max_num_ForYear) {
            averageRate = calcAverageRate(flowRateForYear,indexForYear);

            logger.debug("save rate into year Table: poname "+poname+",flowid "+flowid+
                    ",Bps "+averageRate.getBps()+",pps "+averageRate.getPps()+",timestrap "+averageRate.getTimestarp());
            flowData.addFlowData(poname,flowid,YEAR_TABLE_NAME,averageRate.getTimestarp(),
                    averageRate.getPps(),averageRate.getBps());
            indexForYear = 0;
        }
    }

    private void rateSaveForMonth(String poname,int flowid,double Bps,double pps,long time) {
        FlowRate averageRate;

        flowRateForMonth[indexForMonth].SetAll(Bps, pps, time);
        indexForMonth++;

        if (indexForMonth >= max_num_ForMonth) {
            averageRate = calcAverageRate(flowRateForMonth,indexForMonth);

            logger.debug("save rate into month Table: poname "+poname+",flowid "+flowid+
                    ",Bps "+averageRate.getBps()+",pps "+averageRate.getPps()+",timestrap "+averageRate.getTimestarp());
            flowData.addFlowData(poname,flowid,MONTH_TABLE_NAME,averageRate.getTimestarp(),
                    averageRate.getPps(),averageRate.getBps());
            indexForMonth = 0;

            rateSaveForYear(poname, flowid, averageRate.getBps(), averageRate.getPps(), averageRate.getTimestarp());
        }
    }

    private void rateSaveForWeek(String poname,int flowid,double Bps,double pps,long time) {
        FlowRate averageRate;

        flowRateForWeek[indexForWeek].SetAll(Bps, pps, time);
        indexForWeek++;

        if (indexForWeek >= max_num_ForWeek) {
            averageRate = calcAverageRate(flowRateForWeek,indexForWeek);

            logger.debug("save rate into week Table: poname "+poname+",flowid "+flowid+
                    ",Bps "+averageRate.getBps()+",pps "+averageRate.getPps()+",timestrap "+averageRate.getTimestarp());
            flowData.addFlowData(poname,flowid,WEEK_TABLE_NAME,averageRate.getTimestarp(),
                    averageRate.getPps(),averageRate.getBps());
            indexForWeek = 0;

            rateSaveForMonth(poname,flowid,averageRate.getBps(),averageRate.getPps(),averageRate.getTimestarp());
        }
    }

    public void latestRateSave(String poname,int flowid,double Bps,double pps,long time) {
        //保存到小时表
        logger.debug("save rate into hour Table: poname "+poname+",flowid "+flowid+
                ",Bps "+Bps+",pps "+pps+",timestrap "+time);
        flowData.addFlowData(poname,flowid,HOUR_TABLE_NAME,time,pps,Bps);

        if (index < max_num_hourTable) {
            flowRate[index].SetAll(Bps, pps, time);
            index++;
        } else {
            //max_num_hourTable需要保证不能走到这个分支，否则数据库中day 表保存的数据会有误差
            logger.error("error for too small index : poname "+poname+",flowid "+flowid);
        }

        if (firstTimestrap == 0) {
            firstTimestrap = time;
            return;
        }

        FlowRate averageRate;
        if (time-firstTimestrap >= halfHour && index != 0) {//计算出半小时
            averageRate = calcAverageRate(flowRate,index);
            logger.debug("save rate into day Table: poname "+poname+",flowid "+flowid+
                    ",Bps "+averageRate.getBps()+",pps "+averageRate.getPps()+",timestrap "+averageRate.getTimestarp());
            flowData.addFlowData(poname,flowid,DAY_TABLE_NAME,averageRate.getTimestarp(),
                    averageRate.getPps(),averageRate.getBps());
            index = 0;//完成day表中一个点，归0

            rateSaveForWeek(poname,flowid,averageRate.getBps(),averageRate.getPps(),averageRate.getTimestarp());
            firstTimestrap = time;
        }
    }
}
