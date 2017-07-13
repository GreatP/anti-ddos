/**
 * Copyright (c) <2015> <ss-cetc> and others.  All rights reserved.
 *
 *
 * @author lingbo on 2015/6/18
 * @version 0.1
 */

package com.cetc.security.ddos.statanalysis;
import com.cetc.security.ddos.controller.adapter.TrafficTuple;
import com.cetc.security.ddos.persistence.ThresholdType;
import com.cetc.security.ddos.flow.ProtectObjectFlow;

public class StatReport {
	private int protocol;		// 6-tcp, 17-udp, 1-icmp, 0- other
    private int port;			// Relevant only for tcp and udp
    private TrafficTuple stats;
    private long   readingTime; //时间戳，毫秒
    private String poKey;
    private String trafficFloorKey;
    private int flowid;
    private ProtectObjectFlow poFlow;

    private int weekday;
    private int hour;

    //暂时配置
    /*public enum ThresholdType {
        AUTO_LEARNING,
        FIXED_THRESHOLD
    }*/

    private ThresholdType type;
    private double KBps_threshold;
    private double pps_threshold;

    /*private int detectionDeviationPercentage = 50;
    private int attackSuspicionsThreshold = 3;
    private int backToNormalThreshold = 3;*/

    public StatReport(int protocol, int port, TrafficTuple stats, long readingTime, String pnKey,
                      String trafficFloorKey,ProtectObjectFlow poFlow,int flowid,
                      ThresholdType type,double KBps_threshold,double pps_threshold) {
        this.protocol = protocol;
        this.port = port;
        this.stats = stats;
        this.readingTime = readingTime;
        this.poKey = pnKey;
        this.trafficFloorKey = trafficFloorKey;
        this.poFlow = poFlow;
        this.flowid = flowid;
        this.type = type;
        this.KBps_threshold = KBps_threshold;
        this.pps_threshold = pps_threshold;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public TrafficTuple getStats() {
        return stats;
    }

    public void setStats(TrafficTuple stats) {
        this.stats = stats;
    }

    public long getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(long readingTime) {
        this.readingTime = readingTime;
    }

    public String getPoKey() {
        return poKey;
    }

    public void setPoKey(String poKey) {
        this.poKey = poKey;
    }

    public String getTrafficFloorKey() {
        return trafficFloorKey;
    }

    public void setTrafficFloorKey(String trafficFloorKey) {
        this.trafficFloorKey = trafficFloorKey;
    }


    public ProtectObjectFlow getPoFlow() {
        return poFlow;
    }

    public void setPoFlow(ProtectObjectFlow poFlow) {
        this.poFlow = poFlow;
    }

    public int getFlowid() {
        return flowid;
    }

    public void setFlowid(int flowid) {
        this.flowid = flowid;
    }

    public ThresholdType getType() {
        return type;
    }

    public void setType(ThresholdType type) {
        this.type = type;
    }

    public double getKBps_threshold() {
        return KBps_threshold;
    }

    public void setKBps_threshold(double KBps_threshold) {
        this.KBps_threshold = KBps_threshold;
    }

    public double getPps_threshold() {
        return pps_threshold;
    }

    public void setPps_threshold(double pps_threshold) {
        this.pps_threshold = pps_threshold;
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
}