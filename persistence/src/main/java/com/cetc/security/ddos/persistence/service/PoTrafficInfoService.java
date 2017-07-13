package com.cetc.security.ddos.persistence.service;


import com.cetc.security.ddos.persistence.PoTrafficInfoEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import com.cetc.security.ddos.persistence.TrafficInfoEntity;
import com.cetc.security.ddos.persistence.dao.PoTrafficInfoDao;
import com.cetc.security.ddos.persistence.dao.ProtectObjectDao;
import com.cetc.security.ddos.persistence.dao.TrafficInfoDao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("poTrafficService")
public class PoTrafficInfoService {

	@Autowired
	private PoTrafficInfoDao poTrafficInfoDao;

    @Autowired
    private ProtectObjectDao protectObjectDao;
	
	public List<PoTrafficInfoEntity> getAllPoTrafficInfo() {
        return poTrafficInfoDao.findAll();
    }

    public List<PoTrafficInfoEntity> getPoTrafficInfoRange(int start, int limit, String unit) {
        return poTrafficInfoDao.getOrderedDescByFlowRate(start, limit, unit);
    }

    public List<DetailPoTrafficInfo> getPoTrafficStat(int start, int limit) {
        String order = "flowrate_bps";// flowrate_bps or flowrate_pps
        List<DetailPoTrafficInfo> list = new ArrayList<DetailPoTrafficInfo>();
        List<PoTrafficInfoEntity> poStatisticsEntitys = poTrafficInfoDao.getOrderedDescByFlowRate(start, limit, order);

        for (PoTrafficInfoEntity f: poStatisticsEntitys) {
            DetailPoTrafficInfo dfInfo = new DetailPoTrafficInfo();

            PoTrafficInfo poTrafficInfo = new PoTrafficInfo();
            dfInfo.setPoName(getPoNameById(f.getPo_id()));
            poTrafficInfo.setRate_bps(f.getFlowrate_bps());
            poTrafficInfo.setRate_pps(f.getFlowrate_pps());

            dfInfo.setPoTrafficInfo(poTrafficInfo);

            list.add(dfInfo);
        }


        return list;
    }

    public PoTrafficInfoEntity findPoTrafficById(int id) {
        return poTrafficInfoDao.findById(id);
    }

    public PoTrafficInfoEntity findPoTrafficByPoId(int poId) {
        return poTrafficInfoDao.findByPoId(poId);
    }
    
    public void addPoTrafficInfo(PoTrafficInfoEntity poTrafficInfoEntity) {
        poTrafficInfoDao.insert(poTrafficInfoEntity);
    }

    public void updatePoTrafficInfo(PoTrafficInfoEntity poTrafficInfoEntity) {
        poTrafficInfoDao.update(poTrafficInfoEntity);
    }

    public void delAll() {
        poTrafficInfoDao.deleteAll();
    }

    public String getPoNameById(int po_id) {
        ProtectObjectEntity poEntity = protectObjectDao.findById(po_id);
        return  poEntity.getName();
    }

    public class PoTrafficInfo {
        private long rate_pps;
        private long rate_bps;

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

    public class DetailPoTrafficInfo {
        private String poName;
        private PoTrafficInfo poTrafficInfo;

        public String getPoName() {
            return poName;
        }

        public void setPoName(String poName) {
            this.poName = poName;
        }

        public PoTrafficInfo getPoTrafficInfo() {
            return poTrafficInfo;
        }

        public void setPoTrafficInfo(PoTrafficInfo poTrafficInfo) {
            this.poTrafficInfo = poTrafficInfo;
        }
    }

}
