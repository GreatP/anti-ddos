package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by cw on 2016/5/11.
 */
@Entity
@Table(name="po_trafficinfo")
public class PoTrafficInfoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="po_id")
    private int po_id;
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

    public int getPo_id() {
        return po_id;
    }

    public void setPo_id(int po_id) {
        this.po_id = po_id;
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
