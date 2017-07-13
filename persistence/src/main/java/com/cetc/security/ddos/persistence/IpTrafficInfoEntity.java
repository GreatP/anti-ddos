package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by hanqsheng on 2016/5/12.
 */
@Entity
@Table(name="ip_trafficinfo")
public class IpTrafficInfoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="ip")
    private String ip;
    @Column(name="flowrate_pps")
    private long flowrate_pps;
    @Column(name="flowrate_bps")
    private long flowrate_bps;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getFlowrate_pps() {
        return flowrate_pps;
    }

    public void setFlowrate_pps(long flowrate_pps) {
        this.flowrate_pps = flowrate_pps;
    }

    public long getFlowrate_bps() {
        return flowrate_bps;
    }

    public void setFlowrate_bps(long flowrate_bps) {
        this.flowrate_bps = flowrate_bps;
    }
}
