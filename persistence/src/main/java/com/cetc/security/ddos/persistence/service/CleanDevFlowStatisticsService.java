package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.common.type.ProtocolType;
import com.cetc.security.ddos.persistence.CleanDevFlowStatisticsEntity;
import com.cetc.security.ddos.persistence.NetNodeEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import com.cetc.security.ddos.persistence.dao.AttackIpDao;
import com.cetc.security.ddos.persistence.dao.CleanDevFlowStatisticsDao;
import com.cetc.security.ddos.persistence.dao.ProtectObjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cetc.security.ddos.persistence.FlowStatisticsEntity;
import com.cetc.security.ddos.persistence.dao.FlowStatisticsDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2015/10/9.
 */
@Service
public class CleanDevFlowStatisticsService {
    @Autowired
    private CleanDevFlowStatisticsDao cleanDevFlowStatisticsDao;
    @Autowired
    private ProtectObjectDao protectObjectDao;
    @Autowired
    private AttackIpDao attackIpDao;

    public List<CleanDevFlowStatisticsEntity> getAllCleanDevStatInfo() {
        return cleanDevFlowStatisticsDao.findAll();
    }

    public void addCleanDevStats(CleanDevFlowStatisticsEntity cleanDevFlowStatisticsEntity) {
        cleanDevFlowStatisticsDao.insert(cleanDevFlowStatisticsEntity);
    }

    public void setCleanDevAttackStat(CleanDevAttackStat cleanDevAttackStat, CleanDevFlowStatisticsEntity f) {
        cleanDevAttackStat.setDefenseCount(cleanDevAttackStat.getDefenseCount() + f.getDefenseCount());
        cleanDevAttackStat.setAttackCount(cleanDevAttackStat.getAttackCount() + f.getAttackCount());
        cleanDevAttackStat.setCleanTraffic(cleanDevAttackStat.getCleanTraffic() + f.getCleanTraffic());
        cleanDevAttackStat.setAttackSrcNum(f.getAttackSrcNum() + cleanDevAttackStat.getAttackSrcNum());
    }

    public void setAttackTypeStat(AttackTypeStat attackTypeStat, CleanDevFlowStatisticsEntity f) {
        attackTypeStat.setIcmpFloodCount(attackTypeStat.getIcmpFloodCount() + f.getIcmpFloodCount());
        attackTypeStat.setSynFloodCount(f.getSynFloodCount() + attackTypeStat.getSynFloodCount());
        attackTypeStat.setAckFloodCount(f.getAckFloodCount() + attackTypeStat.getAckFloodCount());
        attackTypeStat.setSynAckFloodCount(f.getSynAckFloodCount() + attackTypeStat.getSynAckFloodCount());
        attackTypeStat.setFinRstFloodCount(f.getFinRstFloodCount() + attackTypeStat.getFinRstFloodCount());
        attackTypeStat.setUdpFloodCount(f.getUdpFloodCount() + attackTypeStat.getUdpFloodCount());
        attackTypeStat.setDnsFloodCount(f.getDnsFloodCount() + attackTypeStat.getDnsFloodCount());
        attackTypeStat.setCcFloodCount(f.getCcFloodCount() + attackTypeStat.getCcFloodCount());
    }

    public List<DetailCleanDevStat> getAllAttackDefenseDetailInfo() {

        List<DetailCleanDevStat> list = new ArrayList<DetailCleanDevStat>();
        List<CleanDevFlowStatisticsEntity> cleanDevFlowStatisticsEntities = cleanDevFlowStatisticsDao.getAllCleanDevStatInfo();
        if (cleanDevFlowStatisticsEntities == null) {
            return null;
        }

        for (CleanDevFlowStatisticsEntity f : cleanDevFlowStatisticsEntities) {
            DetailCleanDevStat detailCleanDevStat = new DetailCleanDevStat();
            CleanDevAttackStat cleanDevAttackStat = new CleanDevAttackStat();
            AttackTypeStat attackTypeStat = new AttackTypeStat();

            setCleanDevAttackStat(cleanDevAttackStat, f);
            setAttackTypeStat(attackTypeStat, f);

            detailCleanDevStat.setCleanDevAttackStat(cleanDevAttackStat);
            detailCleanDevStat.setAttackTypeStat(attackTypeStat);

            list.add(detailCleanDevStat);
        }

        return list;
    }

    public CleanDevAttackStat getTotalCleanDevAttackStat() {
        CleanDevAttackStat cleanDevAttackStat = new CleanDevAttackStat();

        List<CleanDevFlowStatisticsEntity> cleanDevFlowStatisticsEntities = cleanDevFlowStatisticsDao.getAllCleanDevStatInfo();
        for (CleanDevFlowStatisticsEntity f: cleanDevFlowStatisticsEntities) {
            setCleanDevAttackStat(cleanDevAttackStat, f);
        }

        return cleanDevAttackStat;
    }

    public AttackTypeStat getTotalAttackTypeStat() {
        AttackTypeStat attackTypeStat = new AttackTypeStat();

        List<CleanDevFlowStatisticsEntity> cleanDevFlowStatisticsEntities = cleanDevFlowStatisticsDao.getAllCleanDevStatInfo();
        for (CleanDevFlowStatisticsEntity f: cleanDevFlowStatisticsEntities) {
            setAttackTypeStat(attackTypeStat, f);
        }

        return attackTypeStat;
    }

    public class DetailCleanDevStat {
        private CleanDevAttackStat cleanDevAttackStat;
        private AttackTypeStat attackTypeStat;

        public CleanDevAttackStat getCleanDevAttackStat() {
            return cleanDevAttackStat;
        }

        public void setCleanDevAttackStat(CleanDevAttackStat cleanDevAttackStat) {
            this.cleanDevAttackStat = cleanDevAttackStat;
        }

        public AttackTypeStat getAttackTypeStat() {
            return attackTypeStat;
        }

        public void setAttackTypeStat(AttackTypeStat attackTypeStat) {
            this.attackTypeStat = attackTypeStat;
        }
    }


    public class CleanDevAttackStat {
        private long attackCount;
        private long defenseCount;
        private long cleanTraffic;
        private long attackSrcNum;

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
    }

    public class AttackTypeStat {
        private long synFloodCount;
        private long ackFloodCount;
        private long synAckFloodCount;
        private long finRstFloodCount;
        private long udpFloodCount;
        private long icmpFloodCount;
        private long dnsFloodCount;
        private long ccFloodCount;

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
}
