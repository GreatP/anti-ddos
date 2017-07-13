/**
 * Copyright (c) <2015> <ss-cetc> and others.  All rights reserved.
 *
 * 
 * @author lingbo on 2015/6/18
 * @version 0.1
 */

package com.cetc.security.ddos.statanalysis;

//import java.util.concurrent.ArrayBlockingQueue;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

//基于流，不考虑PO，PO由主流程控制
public class RateBasedDetectorImpl {
    private static Logger logger = AntiLogger.getLogger(RateBasedDetectorImpl.class);
    //static Calendar cc = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

	/*private ArrayBlockingQueue<StatReport> statsQueue;
	private int statsQueueCapacity;*/

	/*private ConcurrentHashMap<String,POStat> poStatHash = null;*/ // key is poKey,value is POStat
    protected ConcurrentHashMap<String,FlowStatAnalysis> flowStatAnalysisHash; // key is trafficFloorKey,value is FlowStatAnalysis
	
	/* Constructor*/
	public RateBasedDetectorImpl(int statsQueueCapacity) {
		/*this.statsQueueCapacity = statsQueueCapacity;
		statsQueue = new ArrayBlockingQueue<StatReport>(statsQueueCapacity);
		poStatHash = new ConcurrentHashMap<String,POStat>();*/
        flowStatAnalysisHash = new ConcurrentHashMap<String,FlowStatAnalysis>();
	}

    /*public int getStatsQueueCapacity() {
        return statsQueueCapacity;
    }

    public void setStatsQueueCapacity(int statsQueueCapacity) {
        this.statsQueueCapacity = statsQueueCapacity;
    }


    public ArrayBlockingQueue<StatReport> getStatsQueue() {
        return statsQueue;
    }

    public void setStatsQueue(ArrayBlockingQueue<StatReport> statsQueue) {
        this.statsQueue = statsQueue;
    }

    public void handleStatReport(StatReport statsReport) {
		try {
			statsQueue.put(statsReport);
		} 
		catch (InterruptedException e1) {
		} 
		catch ( Throwable e ){
			System.out.println("Failed to handleStatReport. PN key: " + statsReport.getPoKey()+
                    " Reading time: "+statsReport.getReadingTime() + e);
		} 
	}

    public void processStatReports() {
		StatReport statReport = null;		
		while(true) {			
			try {
				statReport = statsQueue.take();
				processStatReport(statReport);
			}
			catch (InterruptedException e1) {
				break;
			}  
			catch (Throwable e) {
				String msg = "Failed to process stat report: PN key: " + ( statReport  != null ?statReport.getPoKey():"");
				System.out.println(msg+e);
			}
		}
	}*/

    public void processStatReport(StatReport statReport) {
		if ( statReport == null || statReport.getStats() == null ) {
			return;
		}
		
		if (statReport.getStats().isZero()) {//流统计为0，不进行流量模型学习
            logger.debug(statReport.getPoKey() +":" + statReport.getTrafficFloorKey() + " stat is zero");
			return;
		}

        //测试时注释掉，由测试代码来设置这些时间
        Calendar cc = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int tmp = cc.get(Calendar.DAY_OF_WEEK)-1;//从星期天开始计算，如果今天星期二，那么返回3
        statReport.setWeekday(tmp);
        tmp = cc.get(Calendar.HOUR_OF_DAY);
        statReport.setHour(tmp);
        long now = System.currentTimeMillis();
        statReport.setReadingTime(now);

		/*POStat po_stat = poStatHash.get(statReport.getPoKey());
		if (po_stat == null) {
            po_stat = new POStat(statReport.getPoKey());
			poStatHash.put(statReport.getPoKey(), po_stat);
            logger.debug("creat PN Stat pnkey:" + statReport.getPoKey());
		}*/
		
		FlowStatAnalysis flow_stat = flowStatAnalysisHash.get(statReport.getTrafficFloorKey());
		if(flow_stat == null) {
            flow_stat = new FlowStatAnalysis(statReport.getTrafficFloorKey(), statReport.getPoKey(),
                    statReport.getPoFlow(),statReport.getFlowid(),statReport.getProtocol());
            flowStatAnalysisHash.put(statReport.getTrafficFloorKey(), flow_stat);
			//System.out.printf("creat Counter Stat %s:%s\n" ,statReport.getPoKey() ,statReport.getTrafficFloorKey());
            logger.debug("creat flow Stat " + statReport.getPoKey() + ":"+ statReport.getTrafficFloorKey());
		}

        flow_stat.processStat(statReport);
	}

    public void delete_pnStat(String poKey) {

    }

}
