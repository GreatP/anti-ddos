package com.cetc.security.ddos.persistence;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhangtao on 2015/10/10.
 */
@Entity
@Table(name="event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="type")
    private EventType type;
    @Column(name="attack_speed")
    private double attackSpeed;
    @Column(name="attack_pps")
    private long attackPps;
    @Column(name="time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="flow_id")
    private FlowEntity flowEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public long getAttackPps() {
        return attackPps;
    }

    public void setAttackPps(long attackPps) {
        this.attackPps = attackPps;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public FlowEntity getFlowEntity() {
        return flowEntity;
    }

    public void setFlowEntity(FlowEntity flowEntity) {
        this.flowEntity = flowEntity;
    }
}
