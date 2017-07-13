package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.controller.adapter.TrafficTuple;
import com.cetc.security.ddos.flow.GuideFlow;
import com.cetc.security.ddos.flow.ProtectObjectFlow;
import com.cetc.security.ddos.flow.RateLimit;
import com.cetc.security.ddos.persistence.DefenseType;
import com.cetc.security.ddos.persistence.EventType;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.web.client.RestClientException;

/**
 * Created by lb on 2015/6/25.
 */
public class ProcessAttack {
    private static Logger logger = AntiLogger.getLogger(ProcessAttack.class);
    private String trafficFloorKey;
    private String poKey;

    private int detectionDeviationPercentage;
    private int attackSuspicionsThreshold;
    private int backToNormalThreshold; //绑定meter后，速率恢复达到该次数，可以取消meter绑定
    private TrafficTuple currentRate;

    private static final long KB2B=1024;//为了便于看数字对应关系，采用1KB=1000B
    private static final double backToNormalPercentage=0.8;//用于固定阀值，实时速率<阀值*backToNormalPercentage则认为攻击消除
    private int 	  	numofAttackSuspicions = 0;

    private boolean attackProcessed = false;
    private int 	  	numofNormal = 0; //绑定meter后，速率恢复正常次数

    //绑定meter时的时间
    private int weekday;
    private int hour;

    private RateLimit rateLimit;
    private GuideFlow guideFlow;
    private OverViewSave overView;
    private FlowEventSave flowEvent;

    public ProcessAttack(String trafficFloorKey, String poKey,ProtectObjectFlow poFlow, int flowid) {
        this.trafficFloorKey = trafficFloorKey;
        this.poKey = poKey;
        this.weekday = -1;
        this.hour = -1;

        this.currentRate = new TrafficTuple();
        this.rateLimit = new RateLimit(poFlow,flowid);
        this.guideFlow = new GuideFlow(poFlow,flowid);
        this.overView = new OverViewSave(flowid);
        this.flowEvent = new FlowEventSave(flowid);

        this.numofAttackSuspicions = 0;
        this.numofNormal = 0;

        this.attackProcessed = false;
    }

    //自动学习和固定阀值间切换时，需要重置计数变量
    public void ProcessAttackInit() {
        this.numofAttackSuspicions = 0;
        this.numofNormal = 0;

        this.attackProcessed = false;
        this.weekday = -1;
        this.hour = -1;
    }

    public String getTrafficFloorKey() {
        return trafficFloorKey;
    }

    public void setTrafficFloorKey(String trafficFloorKey) {
        this.trafficFloorKey = trafficFloorKey;
    }

    public String getPoKey() {
        return poKey;
    }

    public void setPoKey(String poKey) {
        this.poKey = poKey;
    }

    public int getDetectionDeviationPercentage() {
        return detectionDeviationPercentage;
    }

    public int getNumofAttackSuspicions() {
        return numofAttackSuspicions;
    }

    public void setNumofAttackSuspicions(int numofAttackSuspicions) {
        this.numofAttackSuspicions = numofAttackSuspicions;
    }

    public boolean isAttackProcessed() {
        return attackProcessed;
    }

    public void setAttackProcessed(boolean attackProcessed) {
        this.attackProcessed = attackProcessed;
    }

    public int getNumofNormal() {
        return numofNormal;
    }

    public void setNumofNormal(int numofNormal) {
        this.numofNormal = numofNormal;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    private void attackHandler(StatReport statReport,double kbps,double pps) {
        ProtectObjectFlow poFlow;
        poFlow = statReport.getPoFlow();
        if (poFlow.getPo().getDefenseType() == DefenseType.LIMIT_RATE) {
            try{
                rateLimit.startRateLimit(kbps,pps);
                attackProcessed = true;
                logger.info(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" ,it is attacking, so start limit rate"
                    +",kbps " +kbps+",pps " +pps);
            } catch (Exception re){
                logger.error(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" start limit rate failed");
            }
        } else {
            String guideport = statReport.getPoFlow().getPo().getGuidePort();
            try {
                guideFlow.startGuideFlow(guideport);
                attackProcessed = true;
                logger.info(statReport.getPoKey() + ":" + statReport.getTrafficFloorKey() + " ,it is attacking, start Guide Flow"
                        + guideport);
            } catch (Exception e) {
                logger.error(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" start guide flow failed");
            }
        }

        overView.countIncrease(statReport,false,false,true,true,false);
        flowEvent.flowEventAdd(EventType.EVENT_ATTACKED,this.currentRate);
        flowEvent.flowEventAdd(EventType.EVENT_START_DEFENSE,null);
    }

    private void attackEndHandler(StatReport statReport) {
        ProtectObjectFlow poFlow;
        poFlow = statReport.getPoFlow();
        if (poFlow.getPo().getDefenseType() == DefenseType.LIMIT_RATE) {
            try{
                rateLimit.endRateLimit();
                attackProcessed = false;
                numofNormal = 0;
                numofAttackSuspicions = 0;
                logger.info(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" attack has been stopped,end limit rate");
            }catch (Exception re){
                logger.error(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" endRateLimit failed");
            }
        } else {
            try {
                //结束引流
                guideFlow.endGuideFlow();
                attackProcessed = false;
                numofNormal = 0;
                numofAttackSuspicions = 0;
                logger.info(statReport.getPoKey() + ":" + statReport.getTrafficFloorKey() + " attack has been stopped,end Guide Flow");
            } catch (Exception e) {
                logger.error(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" endGuideFlow failed");
            }
        }

        overView.countIncrease(statReport,false,false,false,false,true);
        flowEvent.flowEventAdd(EventType.EVENT_STOP_DEFENSE,null);
    }
    private void updateNumofNormal (StatReport statReport,boolean is_normal) {
        if (attackProcessed == false) {//未绑定meter，numofNormal没有任何作用
            return;
        }
        if (is_normal) {
            numofNormal++;
            logger.debug(statReport.getPoKey() + ":" + statReport.getTrafficFloorKey() + " increase numofNormal to " + numofNormal);
            if (numofNormal > backToNormalThreshold) {
                //解除绑定meter
                attackEndHandler(statReport);
            }
        } else {
            numofNormal--;//numofNormal可以为负数
            logger.debug(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" decrease numofNormal to " + numofNormal);
        }
    }

    private  void  updateNumofAttackSuspicions(StatReport statReport,boolean is_suspicions,TrafficTuple average_rate,
                                               TrafficTuple current_rate) {
        if (is_suspicions) {//速率异常
            numofAttackSuspicions++;
            overView.countIncrease(statReport,false,true,false,false,false);

            if (attackProcessed == false && numofAttackSuspicions > attackSuspicionsThreshold) {
                double n = 1+((double)detectionDeviationPercentage)/100;
                double kbps = (average_rate.getBytes()*8*n)/KB2B;
                double pps = average_rate.getPackets() * n;
                logger.info("it is attacking，attack rate is"+current_rate.getBytes()+"B/s, "
                        +current_rate.getPackets()+"Packets/s");
                attackHandler(statReport,kbps,pps);
            } else {
                logger.debug(statReport.getPoKey() + ":" + statReport.getTrafficFloorKey()
                        + " increase attack suspicions,numofAttackSuspicions="+numofAttackSuspicions);
            }
        } else {
            numofAttackSuspicions--;
            if (current_rate.getBytes() != 0) {//速率不为0时，正常速率次数统计才进行加1操作。
                overView.countIncrease(statReport,true,false,false,false,false);
            }

            if (numofAttackSuspicions < 0) {
                numofAttackSuspicions = 0;
            }
            logger.debug(statReport.getPoKey() + ":" + statReport.getTrafficFloorKey()
                    + " decrease attack suspicions,numofAttackSuspicions="+numofAttackSuspicions);
        }
    }

    public boolean need_update_meter_rate(int weekday,int hour) {
        if (attackProcessed == false) {//未绑定meter，不会更新meter速率
            return false;
        }
        if (this.weekday == -1 && this.hour == -1) {//第一次
            this.weekday = weekday;
            this.hour = hour;
            return false;
        }

        int hour_max = 23;
        if ( (this.hour == hour_max && hour == 0)
                || (this.hour < hour_max && this.hour+1 == hour)) {
            this.weekday = weekday;
            this.hour = hour;
            return true;
        }

        return false;
    }

    //自动学习异常检测
    public boolean checkRateForAttack(StatReport statReport,TrafficTuple average_rate,TrafficTuple current_rate,int weekday,int hour) {
        boolean is_suspicions_attack = false;
        double deviationBytes;
        double deviationPackets;

        /*this.detectionDeviationPercentage = 50;
        this.attackSuspicionsThreshold = 3;
        this.backToNormalThreshold = 3;*/
        this.detectionDeviationPercentage = statReport.getPoFlow().getControl().getDetectionDeviationPercentage();
        this.attackSuspicionsThreshold = statReport.getPoFlow().getControl().getAttackSuspicionsThreshold();
        this.backToNormalThreshold = statReport.getPoFlow().getControl().getRecoverNormalThreshold();
        this.currentRate.setBytes(current_rate.getBytes());
        this.currentRate.setPackets(current_rate.getPackets());

        double deviationFraction = ((double) detectionDeviationPercentage) / 100;
        this.weekday = weekday;
        this.hour = hour;
        if (need_update_meter_rate(weekday,hour)
                && statReport.getPoFlow().getPo().getDefenseType() == DefenseType.LIMIT_RATE) {//更新限速速率
            double n = 1 + deviationFraction;
            double kbps = average_rate.getBytes()*8*n/KB2B;
            double pps = average_rate.getPackets() * n;
            try{
                rateLimit.startRateLimit(kbps,pps);
                /*System.out.printf("%s:%s ,update meter rate to kbps %d,pps %d,weekday %d,hour %d\n",
                        poKey,trafficFloorKey,kbps,pps,weekday,hour);*/
                logger.info(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" ,update meter rate to"
                        +"kbps " +kbps+",pps " +pps+",weekday "+weekday+",hour "+hour);
            }catch (Exception re){
                logger.error(statReport.getPoKey()+":"+statReport.getTrafficFloorKey()+" startRateLimit failed");
            }
        }

        if (current_rate.getBytes()  <= average_rate.getBytes()
                && current_rate.getPackets()  <= average_rate.getPackets()) {
            updateNumofNormal(statReport,true);
            updateNumofAttackSuspicions(statReport,false,average_rate,current_rate);
            return false;
        }

        deviationBytes = current_rate.getBytes()  - average_rate.getBytes();
        deviationPackets = current_rate.getPackets() - average_rate.getPackets();
        if (deviationBytes / average_rate.getBytes() > deviationFraction ||
                deviationPackets / average_rate.getPackets() > deviationFraction) {
            updateNumofAttackSuspicions(statReport,true,average_rate,current_rate);
            is_suspicions_attack = true;
        } else {
            updateNumofAttackSuspicions(statReport,false,average_rate,current_rate);
        }

        return is_suspicions_attack;
    }

    //固定阀值异常检测
    public boolean checkRateForAttack(StatReport statReport,TrafficTuple current_rate) {
        //this.attackSuspicionsThreshold = 3;
        //this.backToNormalThreshold = 3;
        this.attackSuspicionsThreshold = statReport.getPoFlow().getControl().getAttackSuspicionsThreshold();
        this.backToNormalThreshold = statReport.getPoFlow().getControl().getRecoverNormalThreshold();
        this.currentRate.setBytes(current_rate.getBytes());
        this.currentRate.setPackets(current_rate.getPackets());

        double KBpsThreshold = statReport.getKBps_threshold();
        double ppsThreshold = statReport.getPps_threshold();
        if (current_rate.getBytes()> KBpsThreshold*KB2B
                || current_rate.getPackets() > ppsThreshold) {//攻击速率
            numofAttackSuspicions++;
            overView.countIncrease(statReport,false,true,false,false,false);

            if (attackProcessed == false && numofAttackSuspicions > this.attackSuspicionsThreshold) {
                logger.info("fixedThreshold:it is attacking，attack rate "+current_rate.getBytes()+"B/s "
                        +current_rate.getPackets()+"Packets/s");

                attackHandler(statReport,KBpsThreshold*8,ppsThreshold);
                return true;
            } else {
                logger.debug(statReport.getPoKey() + ":" + statReport.getTrafficFloorKey()
                        + " fixedThreshold:increase attack suspicions,numofAttackSuspicions="+numofAttackSuspicions);
            }
        } else {//正常速率
            numofAttackSuspicions--;
            overView.countIncrease(statReport,true,false,false,false,false);

            if (numofAttackSuspicions < 0) {
                numofAttackSuspicions = 0;
            }

            logger.debug(statReport.getPoKey() + ":" + statReport.getTrafficFloorKey()
                    + " fixedThreshold:decrease attack suspicions,numofAttackSuspicions="+numofAttackSuspicions);
            if (current_rate.getBytes() < KBpsThreshold*KB2B*backToNormalPercentage
                    && current_rate.getPackets() < ppsThreshold*backToNormalPercentage) {//攻击恢复速率
                updateNumofNormal(statReport,true);
            }
        }
        return false;
    }

	public void startDefense(){

		System.out.printf("Start Defense!\n");
	}


	public void endDefense(){

		System.out.printf("End Defense!\n");

	}
}
