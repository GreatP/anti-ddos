package com.cetc.security.ddos.persistence.service;



import com.cetc.security.ddos.persistence.IpTrafficInfoEntity;
import com.cetc.security.ddos.persistence.TrafficInfoEntity;

import com.cetc.security.ddos.persistence.dao.IpTrafficInfoDao;
import com.cetc.security.ddos.persistence.dao.TrafficInfoDao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

@Service("ipTrafficService")
public class IpTrafficInfoService {

	@Autowired
    private IpTrafficInfoDao ipFlowDao;


    public List<IpTrafficInfoEntity> getAllIpFlowInfo() {
        return ipFlowDao.findAll();
    }

    public IpTrafficInfoEntity getIpTrafficById(int id) {
        return ipFlowDao.findById(id);
    }

    public IpTrafficInfoEntity findIpTrafficByIp(String ip) {
        return ipFlowDao.findByIp(ip);
    }


    public List<IpTrafficInfoEntity> getIpTrafficInfoRange(int start, int limit) {
        String order = "flowrate_bps";// flowrate_bps or flowrate_pps
        return ipFlowDao.findOrderedDescByFlowRate(start, limit, order);
    }

    public void addIpFlow(IpTrafficInfoEntity ipFlowEntity) {
        ipFlowDao.insert(ipFlowEntity);
    }

    public void updateIpFlow(IpTrafficInfoEntity ipFlowEntity) {
        ipFlowDao.update(ipFlowEntity);
    }

    public void delAll(){
        ipFlowDao.deleteAll();
    }

    public List<DetailIpTrafficInfo> getIpTrafficStat(int start, int limit) {
        String order = "flowrate_bps";// flowrate_bps or flowrate_pps
        List<DetailIpTrafficInfo> list = new ArrayList<DetailIpTrafficInfo>();
        List<IpTrafficInfoEntity> poStatisticsEntitys = ipFlowDao.findOrderedDescByFlowRate(start, limit, order);

        for (IpTrafficInfoEntity f: poStatisticsEntitys) {
            DetailIpTrafficInfo dfInfo = new DetailIpTrafficInfo();
            dfInfo.setIp(f.getIp());
            dfInfo.setRate_pps(f.getFlowrate_pps());
            dfInfo.setRate_bps(f.getFlowrate_bps());

            list.add(dfInfo);
        }

        return list;
    }


    public class DetailIpTrafficInfo {
        private String ip;
        private long rate_pps;
        private long rate_bps;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
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
}
