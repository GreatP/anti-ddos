package com.cetc.security.ddos.statanalysis;

/**
 * Created by lb on 2015/8/24.
 */
public class FlowRate {
    private double Bps;
    private double pps;
    private long timestarp;

    public FlowRate() {
        this.Bps = 0;
        this.pps = 0;
        this.timestarp = 0;
    }

    public double getBps() {
        return Bps;
    }

    public void setBps(double bps) {
        Bps = bps;
    }

    public double getPps() {
        return pps;
    }

    public void setPps(double pps) {
        this.pps = pps;
    }

    public long getTimestarp() {
        return timestarp;
    }

    public void setTimestarp(long timestarp) {
        this.timestarp = timestarp;
    }

    public void SetAll(double Bps, double pps, long timestarp) {
        this.Bps = Bps;
        this.pps = pps;
        this.timestarp = timestarp;
    }
}
