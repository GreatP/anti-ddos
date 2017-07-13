package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by zhangtao on 2015/7/22.
 */
@Entity
@Table(name="flow")
public class FlowEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="priority")
    private short priority;
    @Column(name="ethernetType")
    private short ethType;
    @Column(name="protocol")
    private short protocol;
    @Column(name="l4")
    private int l4;     /* TCP,UDP表示目的端口 */
    @Column(name="threshold_type")
    private ThresholdType thresholdType;
    @Column(name="threshold_pps")
    private long thresholdPps;
    @Column(name="threshold_kbps")
    private long thresholdKBps;
    @Column(name="po_id")
    private int poId;
    @Column(name="flag")
    private short flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public short getProtocol() {
        return protocol;
    }

    public void setProtocol(short protocol) {
        this.protocol = protocol;
    }

    public short getEthType() {
        return ethType;
    }

    public void setEthType(short ethType) {
        this.ethType = ethType;
    }

    public int getL4() {
        return l4;
    }

    public void setL4(int l4) {
        this.l4 = l4;
    }

    public ThresholdType getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(ThresholdType thresholdType) {
        this.thresholdType = thresholdType;
    }

    public long getThresholdKBps() {
        return thresholdKBps;
    }

    public void setThresholdKBps(long thresholdKBps) {
        this.thresholdKBps = thresholdKBps;
    }

    public long getThresholdPps() {
        return thresholdPps;
    }

    public void setThresholdPps(long thresholdPps) {
        this.thresholdPps = thresholdPps;
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public short getFlag() {
        return flag;
    }

    public void setFlag(short flag) {
        this.flag = flag;
    }
}
