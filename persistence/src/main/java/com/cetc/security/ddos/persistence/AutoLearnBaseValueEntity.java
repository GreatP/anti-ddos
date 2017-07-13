package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by lb on 2015/8/13.
 */
@Entity
@Table(name="autolearnbasevalue")
public class AutoLearnBaseValueEntity {
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO) //表示id自动产生，采用自加的方式
    @Column(name="id")
    private int id;
    @Column(name="PoName")
    private String poname;
    @Column(name="protocal")
    private int protocal;
    @Column(name="flowid")
    private int flowid;
    @Column(name="week")
    private int week;
    @Column(name="hour")
    private int hour;
    @Column(name="pps")
    private String pps;
    @Column(name="bps")
    private String bps;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoname() {
        return poname;
    }

    public void setPoname(String poname) {
        this.poname = poname;
    }

    public int getProtocal() {
        return protocal;
    }

    public void setProtocal(int protocal) {
        this.protocal = protocal;
    }

    public int getFlowid() {
        return flowid;
    }

    public void setFlowid(int flowid) {
        this.flowid = flowid;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getPps() {
        return pps;
    }

    public void setPps(String pps) {
        this.pps = pps;
    }

    public String getBps() {
        return bps;
    }

    public void setBps(String bps) {
        this.bps = bps;
    }
}
