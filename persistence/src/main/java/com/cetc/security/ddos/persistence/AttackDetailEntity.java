package com.cetc.security.ddos.persistence;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hanqsheng on 2016/5/23.
 */
@Entity
@Table(name="attack_detail")
public class AttackDetailEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="attackip")
    private String attackip;
    @Column(name="attack_type")
    private int attackType;
    @Column(name="start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name="duration")
    private long duration;
    @Column(name="status")
    private int status;
    @Column(name="total_pkts")
    private long totalPkts;
    @Column(name="total_bytes")
    private long totalBytes;
    @Column(name="peak")
    private long peak;
    @Column(name="po_id")
    private int poId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttackip() {
        return attackip;
    }

    public void setAttackip(String attackip) {
        this.attackip = attackip;
    }

    public int getAttackType() {
        return attackType;
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTotalPkts() {
        return totalPkts;
    }

    public void setTotalPkts(long totalPkts) {
        this.totalPkts = totalPkts;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getPeak() {
        return peak;
    }

    public void setPeak(long peak) {
        this.peak = peak;
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }
}
