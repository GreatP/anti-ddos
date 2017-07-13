/**
 * Copyright (c) <2015> <ss-cetc> and others.  All rights reserved.
 *
 *
 * @author lingbo on 2015/6/18
 * @version 0.1
 */

package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.controller.adapter.TrafficTuple;
import com.cetc.security.ddos.flow.ProtectObjectFlow;
import com.cetc.security.ddos.persistence.ThresholdType;
import com.cetc.security.ddos.statanalysis.AutoLearningProcess.Status;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

//基于流的计数，每条流一个CounterStat
// 控制器、netnode、flowid中任一改变，都需要先删除对应的FlowStatAnalysis
public class FlowStatAnalysis {
    private static Logger logger = AntiLogger.getLogger(FlowStatAnalysis.class);
    private String trafficFloorKey;
    private String poKey;
    private  AutoLearningProcess auto_learn;
    private ProcessAttack processAttack;
    private FlowStatSave flowStatSave;


    private TrafficTuple lastReading;	// bytes/packets counter reading 上次获取的统计
    private TrafficTuple latestRate;		// bytes/packets per second 最新速率
    private long   lastReadTime = 0;		// Time of last reading
    private long   firstReadTime = 0;	// Time of first reading
    private static final String FLOW_DATA_PONAME="anti";//保存实时流量时poname使用此相同值

    //private int baseValueType = -1;//0表示自动学习，1表示固定阀值   //已经不需要咯，因为每次配置改变，主程序都会重新new类


	// to allow locking of the Counter object from Detectors methods
	/*private final ReentrantLock lock = new ReentrantLock();
	public void lock() { this.lock.lock(); }
	public void unlock() { this.lock.unlock(); }*/


	public FlowStatAnalysis(String trafficFloorKey, String poKey, ProtectObjectFlow poFlow, int flowid,int protocol) {
		this.trafficFloorKey = trafficFloorKey;
		this.poKey = poKey;
        processAttack = new ProcessAttack(trafficFloorKey, poKey,poFlow,flowid);
        //auto_learn = null;
        auto_learn = new AutoLearningProcess(poKey,protocol,flowid);
        lastReading = new TrafficTuple();
        latestRate = new TrafficTuple();

        flowStatSave = new FlowStatSave(FLOW_DATA_PONAME,flowid);

        lastReadTime = 0;
        firstReadTime = 0;
        //baseValueType = -1;
	}


    public void latestRateCalc(StatReport statsReport) {
        TrafficTuple new_stat = statsReport.getStats();
        TrafficTuple old_stat = lastReading;
        long this_period = statsReport.getStats().getDuration() - lastReading.getDuration();

        if (new_stat.getPackets() < old_stat.getPackets() || new_stat.getBytes() < old_stat.getBytes()) {
            System.out.printf("error stat new(%f ,%f) < old(%f ,%f)\n",
                    new_stat.getPackets(),old_stat.getPackets(),
                    new_stat.getBytes(),old_stat.getBytes());
            return;
        }
        double tmp = (new_stat.getPackets() - old_stat.getPackets())/this_period;
        latestRate.setPackets(tmp);

        tmp = (new_stat.getBytes() - old_stat.getBytes())/this_period;
        latestRate.setBytes(tmp);

        latestRate.setDuration(this_period);
        logger.debug(statsReport.getPoKey()+":"+statsReport.getTrafficFloorKey()+ " current rate is "+
                latestRate.getBytes()+"-"+latestRate.getPackets()+"-"+latestRate.getDuration());

        //保存实时流量速率到数据库
        //long now = System.currentTimeMillis();
        long now = statsReport.getReadingTime();
        flowStatSave.latestRateSave(FLOW_DATA_PONAME,statsReport.getFlowid(),latestRate.getBytes(),
                latestRate.getPackets(),now);
    }


	public void processStat(StatReport statsReport) {

        /* Check if first reading */
        if (firstReadTime == 0) {
            firstReadTime = lastReadTime = statsReport.getReadingTime();
            lastReading.setTrafficTuple(statsReport.getStats());
            if (statsReport.getType() == ThresholdType.AUTO_LEARNING) {
                if (auto_learn.getStatus() == Status.INIT) {
                    auto_learn.setStatus(Status.LEARNING);
                    //System.out.printf("%s:%s start learning\n", statsReport.getPoKey(), statsReport.getTrafficFloorKey());
                    logger.info(statsReport.getPoKey() + ":" + statsReport.getTrafficFloorKey() + " start learning ");
                }
                logger.debug(statsReport.getPoKey()+":"+statsReport.getTrafficFloorKey()+","+statsReport.getType()+",Kbps_threshold "
                        + statsReport.getKBps_threshold()+",Pps_threshold "+statsReport.getPps_threshold());
                //baseValueType = 1;
            } else {
                //baseValueType = 0;
                logger.debug(statsReport.getPoKey()+":"+statsReport.getTrafficFloorKey()+",start "+statsReport.getType()+",Kbps_threshold "
                        + statsReport.getKBps_threshold()+",Pps_threshold "+statsReport.getPps_threshold());
            }

            return;
        }

        if (statsReport.getStats().getDuration() <= lastReading.getDuration()) {//本次获取统计时间小于等于上次时间，不做任何处理
           /* System.out.printf("%s:%s invalid duration new%d <= old%d\n",
                    statsReport.getPoKey(), statsReport.getTrafficFloorKey(), statsReport.getStats().getDuration(),
                    lastReading.getDuration());*/
            logger.info(statsReport.getPoKey()+":"+statsReport.getTrafficFloorKey()+" invalid duration \n"
                        +statsReport.getStats().getDuration() + " <= " + lastReading.getDuration());

            //更新时间和数据，防止重下流表引起问题
            lastReadTime = statsReport.getReadingTime();
            lastReading.setTrafficTuple(statsReport.getStats());
            return;
        }

        TrafficTuple stat = statsReport.getStats();

        logger.debug("last stat is " + lastReading.getBytes() + "-"+lastReading.getPackets() +
                "-"+lastReading.getDuration());
        logger.debug(statsReport.getPoKey()+":"+statsReport.getTrafficFloorKey()+ " new stat is "+
                stat.getBytes()+"-"+stat.getPackets()+"-"+stat.getDuration());
        //计算本次速率
        latestRateCalc(statsReport);

        if (statsReport.getType() == ThresholdType.AUTO_LEARNING) {
            //表示从固定阀值转为自动学习
            /*if (baseValueType != 1) {
                processAttack.ProcessAttackInit();
                baseValueType = 1;
                logger.debug(statsReport.getPoKey() + ":" + statsReport.getTrafficFloorKey() +
                        " from FIXED_THRESHOLD to AUTO_LEARNING");
            }*/

            if (auto_learn.getStatus() == Status.LEARNING
                    && statsReport.getReadingTime() - firstReadTime > AutoLearningProcess.LEARNINT_PERIOD) {
                auto_learn.setStatus(Status.ACTIVE);
                logger.info(statsReport.getPoKey() + ":" + statsReport.getTrafficFloorKey() + " is active...");

                //把基线值保存到数据库中
                auto_learn.allBaseValueSave(statsReport);
            }

            boolean is_suspicions_attack = false;
            int weekday = statsReport.getWeekday();
            int hour = statsReport.getHour();
            TrafficTuple average = auto_learn.getAverage()[weekday][hour];
            logger.debug("average rate is " + average.getBytes() + "-"+average.getPackets() +
                    "-"+average.getDuration() + ",weekday "+weekday+",hour "+hour);


            if (auto_learn.getStatus() == Status.ACTIVE && average.getBytes() != 0) {//ACTIVE状态才进行攻击检测
                //检查是否需要更新数据库中的基线值
                auto_learn.baseValueUpdate(statsReport,weekday,hour,average);

                is_suspicions_attack = processAttack.checkRateForAttack(statsReport, average,
                        latestRate, weekday, hour);
            }

            if (processAttack.isAttackProcessed()) {//已经绑定了meter即确认为攻击，不再更新流量模型

            } else {
                if (auto_learn.getStatus() == Status.LEARNING || !is_suspicions_attack) {//ACTIVE状态下的正常流量速率和学习周期内的流量才能用于更新流量模型
                    auto_learn.updateAvarage(average, latestRate);
                    logger.debug("latest average rate is " + average.getBytes() + "-"+average.getPackets() +
                            "-"+average.getDuration());
                }
            }
        } else {
            //表示自动学习转为固定阀值
            /*if (baseValueType != 0) {
                processAttack.ProcessAttackInit();
                baseValueType = 0;
                logger.debug(statsReport.getPoKey() + ":" + statsReport.getTrafficFloorKey() +
                        " from AUTO_LEARNING to FIXED_THRESHOLD");
            }*/

            logger.debug(statsReport.getPoKey()+":"+statsReport.getTrafficFloorKey()+","+statsReport.getType()+",Kbps_threshold "
                    + statsReport.getKBps_threshold()+",Pps_threshold "+statsReport.getPps_threshold());
            processAttack.checkRateForAttack(statsReport,latestRate);
        }

        //更新最后读取值
        lastReadTime = statsReport.getReadingTime();
        lastReading.setTrafficTuple(statsReport.getStats());
        System.out.printf("\n");
    }
}
