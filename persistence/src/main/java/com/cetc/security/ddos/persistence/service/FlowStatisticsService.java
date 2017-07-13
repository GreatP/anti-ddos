package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.common.type.ProtocolType;
import com.cetc.security.ddos.persistence.NetNodeEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
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
public class FlowStatisticsService {
    @Autowired
    private FlowStatisticsDao flowStatisticsDao;
    @Autowired
    private ProtectObjectDao protectObjectDao;

    private void statCount(FlowStatisticsEntity f, FlowStat flowStat) {
        flowStat.setNormalSpeedCount(f.getNormalSpeedCount() + flowStat.getNormalSpeedCount());
        flowStat.setExceptionSpeedCount(f.getExceptionSpeedCount() + flowStat.getExceptionSpeedCount());
        flowStat.setAttackCount(f.getAttackCount() + flowStat.getAttackCount());
        flowStat.setDefenseCount(f.getDefenseCount() + flowStat.getDefenseCount());
        flowStat.setRecoverCount(f.getRecoverCount() + flowStat.getRecoverCount());
    }

    public FlowStat getTotalStat() {
        FlowStat flowStat = new FlowStat();

        List<FlowStatisticsEntity> flowStatisticsEntities = flowStatisticsDao.findAll();
        for (FlowStatisticsEntity f: flowStatisticsEntities) {
            statCount(f, flowStat);
        }
        return flowStat;
    }

    public List<FlowStat> getProtocolStat() {
        FlowStat tcpFlowStat = new FlowStat();
        FlowStat icmpFlowStat = new FlowStat();
        FlowStat udpFlowStat = new FlowStat();
        FlowStat ipFlowStat = new FlowStat();

        List<FlowStatisticsEntity> flowStatisticsEntities = flowStatisticsDao.findAll();
        for (FlowStatisticsEntity f: flowStatisticsEntities) {
            switch (f.getFlowEntity().getProtocol()) {
                case ProtocolType.IPPROTO_ICMP:
                    statCount(f, icmpFlowStat);
                    break;
                case ProtocolType.IPPROTO_TCP:
                    statCount(f, tcpFlowStat);
                    break;
                case ProtocolType.IPPROTO_UDP:
                    statCount(f, udpFlowStat);
                    break;
                default:
                    statCount(f, ipFlowStat);
                    break;
            }
        }

        List<FlowStat> l = new ArrayList<FlowStat>();
        icmpFlowStat.setProtocol(ProtocolType.IPPROTO_ICMP);
        l.add(icmpFlowStat);
        tcpFlowStat.setProtocol(ProtocolType.IPPROTO_TCP);
        l.add(tcpFlowStat);
        udpFlowStat.setProtocol(ProtocolType.IPPROTO_UDP);
        l.add(udpFlowStat);
        ipFlowStat.setProtocol(ProtocolType.IPPROTO_IP);
        l.add(ipFlowStat);

        return l;
    }

    @Transactional
    public void updateStat(int flowId, long normalSpeedCount, long exceptionSpeedCount,
                           long attackCount, long defenseCount, long recoverCount) {
        FlowStatisticsEntity fs = flowStatisticsDao.findByFlowId(flowId);

        fs.setNormalSpeedCount(fs.getNormalSpeedCount() + normalSpeedCount);
        fs.setExceptionSpeedCount(fs.getExceptionSpeedCount() + exceptionSpeedCount);
        fs.setAttackCount(fs.getAttackCount() + attackCount);
        fs.setDefenseCount(fs.getDefenseCount() + defenseCount);
        fs.setRecoverCount(fs.getRecoverCount() + recoverCount);

        flowStatisticsDao.updateNoTrans(fs);
        flowStatisticsDao.flush();
    }

    public List<DetailFlowStat> getStat(int start, int limit) {
        Map<Integer, ProtectObjectEntity> map = new HashMap<Integer, ProtectObjectEntity>();
        List<DetailFlowStat> list = new ArrayList<DetailFlowStat>();
        List<FlowStatisticsEntity> flowStatisticsEntitys = flowStatisticsDao.findOrderedDescByFlowId(start, limit);

        for (FlowStatisticsEntity f : flowStatisticsEntitys) {
            ProtectObjectEntity po = protectObjectDao.findById(f.getFlowEntity().getPoId(), map);
            if (po == null) {
                continue;
            }

            DetailFlowStat detailFlowStat = new DetailFlowStat();
            detailFlowStat.setPoName(po.getName());

            FlowStat flowStat = new FlowStat();
            flowStat.setProtocol(f.getFlowEntity().getProtocol());
            statCount(f, flowStat);
            detailFlowStat.setFlowStat(flowStat);
            list.add(detailFlowStat);
        }

        return list;
    }

    public long countFlowStat() {
        return flowStatisticsDao.countAll();
    }

    public class FlowStat {
        private short protocol;
        private long normalSpeedCount;
        private long exceptionSpeedCount;
        private long attackCount;
        private long defenseCount;
        private long recoverCount;

        public short getProtocol() {
            return protocol;
        }

        public void setProtocol(short protocol) {
            this.protocol = protocol;
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
    }

    public class DetailFlowStat {
        private String poName;
        private FlowStat flowStat;

        public String getPoName() {
            return poName;
        }

        public void setPoName(String poName) {
            this.poName = poName;
        }

        public FlowStat getFlowStat() {
            return flowStat;
        }

        public void setFlowStat(FlowStat flowStat) {
            this.flowStat = flowStat;
        }
    }
}
