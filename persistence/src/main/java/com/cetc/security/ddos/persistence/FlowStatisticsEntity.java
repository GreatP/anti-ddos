package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by zhangtao on 2015/10/8.
 */
@Entity
@Table(name="flowstatistics")
public class FlowStatisticsEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="normal_speed_count")
    private long normalSpeedCount;
    @Column(name="exception_speed_count")
    private long exceptionSpeedCount;
    @Column(name="attack_count")
    private long attackCount;
    @Column(name="defense_count")
    private long defenseCount;
    @Column(name="recover_count")
    private long recoverCount;
    @OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="flow_id")
    private FlowEntity flowEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNormalSpeedCount() {
        return normalSpeedCount;
    }

    public void setNormalSpeedCount(long normalSpeedCount) {
        this.normalSpeedCount = normalSpeedCount;
    }

    public long getExceptionSpeedCount() {
        return exceptionSpeedCount;
    }

    public void setExceptionSpeedCount(long exceptionSpeedCount) {
        this.exceptionSpeedCount = exceptionSpeedCount;
    }

    public long getAttackCount() {
        return attackCount;
    }

    public void setAttackCount(long attackCount) {
        this.attackCount = attackCount;
    }

    public long getDefenseCount() {
        return defenseCount;
    }

    public void setDefenseCount(long defenseCount) {
        this.defenseCount = defenseCount;
    }

    public long getRecoverCount() {
        return recoverCount;
    }

    public void setRecoverCount(long recoverCount) {
        this.recoverCount = recoverCount;
    }

    public FlowEntity getFlowEntity() {
        return flowEntity;
    }

    public void setFlowEntity(FlowEntity flowEntity) {
        this.flowEntity = flowEntity;
    }
}
