/**
 * Copyright (c) <2015> <ss-cetc> and others.  All rights reserved.
 *
 *
 * @author lingbo on 2015/6/18
 * @version 0.1
 */

package com.cetc.security.ddos.statanalysis;

import java.util.concurrent.ConcurrentHashMap;



public class POStat {
	private String poKey;
	//public String trafficFloorKey;
    private ConcurrentHashMap<String,FlowStatAnalysis> flowStatAnalysisHash; // key is trafficFloorKey,value is FlowStatAnalysis
	
	public POStat(String poKey) {
		this.poKey = poKey;
		flowStatAnalysisHash = new ConcurrentHashMap<String,FlowStatAnalysis>();
	}

    public String getPoKey() {
        return poKey;
    }

    public void setPoKey(String poKey) {
        this.poKey = poKey;
    }
}
