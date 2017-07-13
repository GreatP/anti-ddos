package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.InputTrafficInfoEntity;
import com.cetc.security.ddos.persistence.TrafficInfoEntity;
import com.cetc.security.ddos.persistence.dao.TrafficInfoDao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.Query;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service("trafficDataService")
public class TrafficDataService {

	@Autowired
	private TrafficInfoDao trafficDao;
	
	
	public List<TrafficInfoEntity> getAllTraffic() {
        return trafficDao.findAll();
    }
	
	public List<TrafficInfoEntity> getTrafficByTime(int po_id, int type,long start_time, long end_time) {
        return trafficDao.findTrafficByTime(po_id,type,start_time,end_time);
    }
	
	public List<TrafficInfoEntity> getTrafficByBpsOrder(int po_id, int type,long start_time, long end_time, int count) {
		
		List<TrafficInfoEntity> list=trafficDao.findTrafficByBpsOrder(po_id,type,start_time,end_time);
		List<TrafficInfoEntity> listNew = new ArrayList<TrafficInfoEntity>();
		int realCount=0;
		
		if (list==null) 
		{
			return null;
		}
		
		if (list.size()<count)
		{
			realCount = list.size();
		}
		else
		{
			realCount = count;
		}
		for(int i=0;i<realCount;i++)
		{
			listNew.add(list.get(i));
		}
		return listNew;
    }

    public List<DetailTrafficContrastInfo> getTrafficInfoRangeTime(long start_time, long end_time) {
        long lastTime = 0;
        int k=1;
        List<TrafficInfoEntity> f= trafficDao.findTrafficInfoByTimeRange(start_time, end_time);
        if (f == null) {
            return  null;
        }
        List<DetailTrafficContrastInfo> list = new ArrayList<DetailTrafficContrastInfo>();

        //for (TrafficInfoEntity f: trafficInfoEntities) {
          for (int i=0;i< f.size();) {
              DetailTrafficContrastInfo d = new DetailTrafficContrastInfo();
              d.setInput_traffic_pps(f.get(i).getPps_all());
              d.setInput_traffic_bps(f.get(i).getBps_all());
              d.setOutput_traffic_bps(f.get(i).getOutput_bps());
              d.setOutput_traffic_pps(f.get(i).getOutput_pps());
              d.setAttack_traffic_pps(f.get(i).getAttack_pps());
              d.setAttack_traffic_bps(f.get(i).getAttack_bps());
              d.setTimestamp(f.get(i).getTime());
              for (int j=k; j< f.size();j++,k++) {
                  if ((j < f.size()) && (f.get(j -1).getTime() == f.get(j).getTime())) {
                      d.setInput_traffic_pps(d.getInput_traffic_pps() + f.get(j).getPps_all());
                      d.setInput_traffic_bps(d.getInput_traffic_bps() + f.get(j).getBps_all());
                      d.setOutput_traffic_bps(d.getOutput_traffic_bps() + f.get(j).getOutput_bps());
                      d.setOutput_traffic_pps(d.getOutput_traffic_pps() + f.get(j).getOutput_pps());
                      d.setAttack_traffic_pps(d.getAttack_traffic_pps() + f.get(j).getAttack_pps());
                      d.setAttack_traffic_bps(d.getAttack_traffic_bps() + f.get(j).getAttack_bps());
                  } else {
                      break;
                  }
              }
              i = k;
              k++;
              list.add(d);
          }
            /*
            if (f.get(i).getTime() == lastTime) {
                detailTrafficContrastInfo.setInput_traffic_pps(f.get(i).getPps_all());
                detailTrafficContrastInfo.setInput_traffic_bps(f.get(i).getBps_all());
                detailTrafficContrastInfo.setOutput_traffic_bps(f.get(i).getOutput_bps());
                detailTrafficContrastInfo.setOutput_traffic_pps(f.get(i).getOutput_pps());
                detailTrafficContrastInfo.setAttack_traffic_pps(f.get(i).getAttack_pps());
                detailTrafficContrastInfo.setAttack_traffic_bps(f.get(i).getAttack_bps());
            } else {
                DetailTrafficContrastInfo detailTrafficContrastInfo = new DetailTrafficContrastInfo();
                detailTrafficContrastInfo.setInput_traffic_pps(detailTrafficContrastInfo.getInput_traffic_pps() + f.get(i).getPps_all());
                detailTrafficContrastInfo.setInput_traffic_bps(detailTrafficContrastInfo.getInput_traffic_bps() + f.get(i).getBps_all());
                detailTrafficContrastInfo.setOutput_traffic_bps(detailTrafficContrastInfo.getOutput_traffic_bps() + f.get(i).getOutput_bps());
                detailTrafficContrastInfo.setOutput_traffic_pps(detailTrafficContrastInfo.getOutput_traffic_pps() + f.get(i).getOutput_pps());
                detailTrafficContrastInfo.setAttack_traffic_pps(detailTrafficContrastInfo.getAttack_traffic_pps() + f.get(i).getAttack_pps());
                detailTrafficContrastInfo.setAttack_traffic_bps(detailTrafficContrastInfo.getAttack_traffic_bps() + f.get(i).getAttack_bps());
                detailTrafficContrastInfo.setTimestamp(f.get(i).getTime());
            }
            DetailTrafficContrastInfo detailTrafficContrastInfo = new DetailTrafficContrastInfo();
            lastTime = f.get(i).getTime();
            list.add(detailTrafficContrastInfo);*/

        return  list;
    }


    public DetailInputTrafficInfo getInputTrafficStat(long start, long end) {
        List<DetailInputTrafficInfo> list = new ArrayList<DetailInputTrafficInfo>();
        List<TrafficInfoEntity> trafficInfoEntities= trafficDao.findTrafficInfoByTimeRange(start, end);
        if (trafficInfoEntities == null) {
            return  null;
        }

        DetailInputTrafficInfo dfInfo = new DetailInputTrafficInfo();
        for (TrafficInfoEntity f: trafficInfoEntities) {
            dfInfo.setIcmp_bps(f.getBps_icmp() + dfInfo.getIcmp_bps());
            dfInfo.setIcmp_pps(f.getPps_icmp() + dfInfo.getIcmp_pps());
            dfInfo.setTcp_bps(f.getBps_tcp() + dfInfo.getTcp_bps());
            dfInfo.setTcp_pps(f.getPps_tcp() + dfInfo.getTcp_pps());
            dfInfo.setUdp_bps(f.getBps_udp() + dfInfo.getUdp_bps());
            dfInfo.setUdp_pps(f.getPps_udp() + dfInfo.getUdp_pps());
            dfInfo.setOther_bps(f.getBps_other() + dfInfo.getOther_bps());
            dfInfo.setOther_pps(f.getPps_other() + dfInfo.getOther_pps());

            list.add(dfInfo);
        }

        return dfInfo;
    }

    public TrafficInfoEntity getTrafficById(int id) {
        return trafficDao.findById(id);
    }
    
    public TrafficInfoEntity getTrafficByPoId(int po_id) {
        return trafficDao.findByPoId(po_id);
    }
    
    public void delTrafficByType(int type) {
        trafficDao.delByType(type);
    }
    
    public void addTrafficNode(TrafficInfoEntity trafficEntity) {
    	trafficDao.insert(trafficEntity);
    }
    
    public void addTrafficNodes(List<TrafficInfoEntity> trafficEntities) {
    	for(TrafficInfoEntity tf:trafficEntities)
    	{
    		trafficDao.insert(tf);
    	}
    }

    public class DetailTrafficContrastInfo {
        private long input_traffic_pps;
        private long input_traffic_bps;
        private long output_traffic_pps;
        private long output_traffic_bps;
        private long attack_traffic_pps;
        private long attack_traffic_bps;
        private long timestamp;

        public long getInput_traffic_pps() {
            return input_traffic_pps;
        }

        public void setInput_traffic_pps(long input_traffic_pps) {
            this.input_traffic_pps = input_traffic_pps;
        }

        public long getInput_traffic_bps() {
            return input_traffic_bps;
        }

        public void setInput_traffic_bps(long input_traffic_bps) {
            this.input_traffic_bps = input_traffic_bps;
        }

        public long getOutput_traffic_pps() {
            return output_traffic_pps;
        }

        public void setOutput_traffic_pps(long output_traffic_pps) {
            this.output_traffic_pps = output_traffic_pps;
        }

        public long getOutput_traffic_bps() {
            return output_traffic_bps;
        }

        public void setOutput_traffic_bps(long output_traffic_bps) {
            this.output_traffic_bps = output_traffic_bps;
        }

        public long getAttack_traffic_pps() {
            return attack_traffic_pps;
        }

        public void setAttack_traffic_pps(long attack_traffic_pps) {
            this.attack_traffic_pps = attack_traffic_pps;
        }

        public long getAttack_traffic_bps() {
            return attack_traffic_bps;
        }

        public void setAttack_traffic_bps(long attack_traffic_bps) {
            this.attack_traffic_bps = attack_traffic_bps;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public class DetailInputTrafficInfo {
        private long icmp_pps;
        private long icmp_bps;
        private long udp_pps;
        private long udp_bps;
        private long tcp_pps;
        private long tcp_bps;
        private long other_pps;
        private long other_bps;

        public long getIcmp_pps() {
            return icmp_pps;
        }

        public void setIcmp_pps(long icmp_pps) {
            this.icmp_pps = icmp_pps;
        }

        public long getIcmp_bps() {
            return icmp_bps;
        }

        public void setIcmp_bps(long icmp_bps) {
            this.icmp_bps = icmp_bps;
        }

        public long getUdp_pps() {
            return udp_pps;
        }

        public void setUdp_pps(long udp_pps) {
            this.udp_pps = udp_pps;
        }

        public long getUdp_bps() {
            return udp_bps;
        }

        public void setUdp_bps(long udp_bps) {
            this.udp_bps = udp_bps;
        }

        public long getTcp_pps() {
            return tcp_pps;
        }

        public void setTcp_pps(long tcp_pps) {
            this.tcp_pps = tcp_pps;
        }

        public long getTcp_bps() {
            return tcp_bps;
        }

        public void setTcp_bps(long tcp_bps) {
            this.tcp_bps = tcp_bps;
        }

        public long getOther_pps() {
            return other_pps;
        }

        public void setOther_pps(long other_pps) {
            this.other_pps = other_pps;
        }

        public long getOther_bps() {
            return other_bps;
        }

        public void setOther_bps(long other_bps) {
            this.other_bps = other_bps;
        }
    }
}
