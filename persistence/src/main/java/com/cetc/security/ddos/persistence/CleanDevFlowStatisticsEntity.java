package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by hanqsheng  on 2016/5/23.
 */
@Entity
@Table(name="cleandev_flowstatistics")
public class CleanDevFlowStatisticsEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="attack_count")
    private long attackCount;
    @Column(name="defense_count")
    private long defenseCount;
    @Column(name="clean_traffic")
    private long cleanTraffic;
    @Column(name="attack_src_num")
    private long attackSrcNum;
    @Column(name="syn_flood")
    private long synFloodCount;
    @Column(name="ack_flood")
    private long ackFloodCount;
    @Column(name="syn_ack_flood")
    private long synAckFloodCount;
    @Column(name="fin_rst_flood")
    private long finRstFloodCount;
    @Column(name="udp_flood")
    private long udpFloodCount;
    @Column(name="icmp_flood")
    private long icmpFloodCount;

    @Column(name="dns_flood")
    private long dnsFloodCount;
    @Column(name="cc_flood")
    private long ccFloodCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getCleanTraffic() {
        return cleanTraffic;
    }

    public void setCleanTraffic(long cleanTraffic) {
        this.cleanTraffic = cleanTraffic;
    }

    public long getAttackSrcNum() {
        return attackSrcNum;
    }

    public void setAttackSrcNum(long attackSrcNum) {
        this.attackSrcNum = attackSrcNum;
    }

    public long getSynFloodCount() {
        return synFloodCount;
    }

    public void setSynFloodCount(long synFloodCount) {
        this.synFloodCount = synFloodCount;
    }

    public long getAckFloodCount() {
        return ackFloodCount;
    }

    public void setAckFloodCount(long ackFloodCount) {
        this.ackFloodCount = ackFloodCount;
    }

    public long getSynAckFloodCount() {
        return synAckFloodCount;
    }

    public void setSynAckFloodCount(long synAckFloodCount) {
        this.synAckFloodCount = synAckFloodCount;
    }

    public long getFinRstFloodCount() {
        return finRstFloodCount;
    }

    public void setFinRstFloodCount(long finRstFloodCount) {
        this.finRstFloodCount = finRstFloodCount;
    }

    public long getUdpFloodCount() {
        return udpFloodCount;
    }

    public void setUdpFloodCount(long udpFloodCount) {
        this.udpFloodCount = udpFloodCount;
    }

    public long getIcmpFloodCount() {
        return icmpFloodCount;
    }

    public void setIcmpFloodCount(long icmpFloodCount) {
        this.icmpFloodCount = icmpFloodCount;
    }

    public long getDnsFloodCount() {
        return dnsFloodCount;
    }

    public void setDnsFloodCount(long dnsFloodCount) {
        this.dnsFloodCount = dnsFloodCount;
    }

    public long getCcFloodCount() {
        return ccFloodCount;
    }

    public void setCcFloodCount(long ccFloodCount) {
        this.ccFloodCount = ccFloodCount;
    }
}
