package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by zhangtao on 2015/7/27.
 */
@Entity
@Table(name="ddosparam")
public class DDoSParamEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="detection_interval")
    private int detectionInterval;
    @Column(name="deviation_percentage")
    private int detectionDeviationPercentage;
    @Column(name="suspicions_threshold")
    private int attackSuspicionsThreshold;
    @Column(name="recover_threshold")
    private int recoverNormalThreshold;
    @Column(name="controller_id")
    private int controllerId;

    public int getDetectionDeviationPercentage() {
        return detectionDeviationPercentage;
    }

    public void setDetectionDeviationPercentage(int detectionDeviationPercentage) {
        this.detectionDeviationPercentage = detectionDeviationPercentage;
    }

    public int getAttackSuspicionsThreshold() {
        return attackSuspicionsThreshold;
    }

    public void setAttackSuspicionsThreshold(int attackSuspicionsThreshold) {
        this.attackSuspicionsThreshold = attackSuspicionsThreshold;
    }

    public int getRecoverNormalThreshold() {
        return recoverNormalThreshold;
    }

    public void setRecoverNormalThreshold(int recoverNormalThreshold) {
        this.recoverNormalThreshold = recoverNormalThreshold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

    public int getDetectionInterval() {
        return detectionInterval;
    }

    public void setDetectionInterval(int detectionInterval) {
        this.detectionInterval = detectionInterval;
    }
}
