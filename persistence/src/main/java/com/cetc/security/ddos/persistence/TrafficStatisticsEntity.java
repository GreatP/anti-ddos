package com.cetc.security.ddos.persistence;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by hanqsheng on 2016/5/11.
 */
@Entity
@Table(name="traffic_statistics")
public class TrafficStatisticsEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="input_traffic_pps")
    private long input_traffic_pps;
    @Column(name="input_traffic_bps")
    private long input_traffic_bps;
    @Column(name="output_traffic_pps")
    private long output_traffic_pps;
    @Column(name="output_traffic_bps")
    private long output_traffic_bps;
    @Column(name="attack_traffic_pps")
    private long attack_traffic_pps;
    @Column(name="attack_traffic_bps")
    private long attack_traffic_bps;
    @Column(name="icmp_pps")
    private long icmp_pps;
    @Column(name="icmp_bps")
    private long icmp_bps;
    @Column(name="udp_pps")
    private long udp_pps;
    @Column(name="udp_bps")
    private long udp_bps;
    @Column(name="tcp_pps")
    private long tcp_pps;
    @Column(name="tcp_bps")
    private long tcp_bps;
    @Column(name="other_pps")
    private long other_pps;
    @Column(name="other_bps")
    private long other_bps;
    @Column(name="save_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date save_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getInput_traffic_pps() {
        return input_traffic_pps;
    }

    public void setInput_traffic_pps(long input_traffic_pps) {
        this.input_traffic_pps = input_traffic_pps;
    }

    public long getInput_traffic_bps() {
        return input_traffic_bps;
    }

    public void setInput_traffic_bps(long input_traffic_bps) {
        this.input_traffic_bps = input_traffic_bps;
    }

    public long getOutput_traffic_pps() {
        return output_traffic_pps;
    }

    public void setOutput_traffic_pps(long output_traffic_pps) {
        this.output_traffic_pps = output_traffic_pps;
    }

    public long getOutput_traffic_bps() {
        return output_traffic_bps;
    }

    public void setOutput_traffic_bps(long output_traffic_bps) {
        this.output_traffic_bps = output_traffic_bps;
    }

    public long getAttack_traffic_pps() {
        return attack_traffic_pps;
    }

    public void setAttack_traffic_pps(long attack_traffic_pps) {
        this.attack_traffic_pps = attack_traffic_pps;
    }

    public long getAttack_traffic_bps() {
        return attack_traffic_bps;
    }

    public void setAttack_traffic_bps(long attack_traffic_bps) {
        this.attack_traffic_bps = attack_traffic_bps;
    }

    public long getIcmp_pps() {
        return icmp_pps;
    }

    public void setIcmp_pps(long icmp_pps) {
        this.icmp_pps = icmp_pps;
    }

    public long getIcmp_bps() {
        return icmp_bps;
    }

    public void setIcmp_bps(long icmp_bps) {
        this.icmp_bps = icmp_bps;
    }

    public long getUdp_pps() {
        return udp_pps;
    }

    public void setUdp_pps(long udp_pps) {
        this.udp_pps = udp_pps;
    }

    public long getUdp_bps() {
        return udp_bps;
    }

    public void setUdp_bps(long udp_bps) {
        this.udp_bps = udp_bps;
    }

    public long getTcp_pps() {
        return tcp_pps;
    }

    public void setTcp_pps(long tcp_pps) {
        this.tcp_pps = tcp_pps;
    }

    public long getTcp_bps() {
        return tcp_bps;
    }

    public void setTcp_bps(long tcp_bps) {
        this.tcp_bps = tcp_bps;
    }

    public long getOther_pps() {
        return other_pps;
    }

    public void setOther_pps(long other_pps) {
        this.other_pps = other_pps;
    }

    public long getOther_bps() {
        return other_bps;
    }

    public void setOther_bps(long other_bps) {
        this.other_bps = other_bps;
    }

    public Date getSave_time() {
        return save_time;
    }

    public void setSave_time(Date save_time) {
        this.save_time = save_time;
    }
}
