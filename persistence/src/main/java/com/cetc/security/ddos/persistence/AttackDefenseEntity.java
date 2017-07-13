package com.cetc.security.ddos.persistence;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hanqsheng on 2016/5/23.
 */
@Entity
@Table(name="attack_statistics")
public class AttackDefenseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="max_bps")
    private long maxBps;
    @Column(name="defense_count")
    private long defenseCount;
    @Column(name="save_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saveTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMaxBps() {
        return maxBps;
    }

    public void setMaxBps(long maxBps) {
        this.maxBps = maxBps;
    }

    public long getDefenseCount() {
        return defenseCount;
    }

    public void setDefenseCount(long defenseCount) {
        this.defenseCount = defenseCount;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }
}
