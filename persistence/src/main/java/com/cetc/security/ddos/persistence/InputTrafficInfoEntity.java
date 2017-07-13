package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by cw on 2016/5/11.
 */
@Entity
@Table(name="input_trafficinfo")
public class InputTrafficInfoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="protocol")
    private int protocol;
    @Column(name="rate_pps")
    private long rate_pps;
    @Column(name="rate_bps")
    private long rate_bps;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public long getRate_pps() {
        return rate_pps;
    }

    public void setRate_pps(long rate_pps) {
        this.rate_pps = rate_pps;
    }

    public long getRate_bps() {
        return rate_bps;
    }

    public void setRate_bps(long rate_bps) {
        this.rate_bps = rate_bps;
    }
}
