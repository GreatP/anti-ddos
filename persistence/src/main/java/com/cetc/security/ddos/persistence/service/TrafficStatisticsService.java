package com.cetc.security.ddos.persistence.service;


import com.cetc.security.ddos.persistence.TrafficStatisticsEntity;
import com.cetc.security.ddos.persistence.dao.TrafficStatisticsDao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//import java.util.ArrayList;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("trafficStatisticsService")
public class TrafficStatisticsService {

	@Autowired
	private TrafficStatisticsDao trafficStatisticsDao;


	public List<TrafficStatisticsEntity> getAllTraffic() {
        return trafficStatisticsDao.findAll();
    }

	public List<TrafficStatisticsEntity> getTrafficStatisticsByTime(Timestamp start_time, Timestamp end_time) {
        return trafficStatisticsDao.findTrafficStatisticsByTime(start_time, end_time);
    }

    public TrafficStatisticsEntity getTrafficStatisticsById(int id) {
        return trafficStatisticsDao.findById(id);
    }

    public void addTrafficStatistic(TrafficStatisticsEntity trafficStatisticsEntity) {
        trafficStatisticsDao.insert(trafficStatisticsEntity);
    }

    public  void delAll(){
        trafficStatisticsDao.deleteAll();
    }

    public List<DetailTrafficContrastInfo> getTrafficContrastInfoRangeTime(Date start_time, Date end_time) {

        List<DetailTrafficContrastInfo> list = new ArrayList<DetailTrafficContrastInfo>();
        List<TrafficStatisticsEntity> trafficContrastEntities =  trafficStatisticsDao.findTrafficStatisticsByTime(start_time, end_time);
        if (trafficContrastEntities == null) {
            return  null;
        }

        for (TrafficStatisticsEntity f: trafficContrastEntities) {
            DetailTrafficContrastInfo detailTrafficContrastInfo = new DetailTrafficContrastInfo();
            detailTrafficContrastInfo.setInput_traffic_pps(f.getInput_traffic_pps());
            detailTrafficContrastInfo.setInput_traffic_bps(f.getInput_traffic_bps());
            detailTrafficContrastInfo.setOutput_traffic_bps(f.getOutput_traffic_bps());
            detailTrafficContrastInfo.setOutput_traffic_pps(f.getOutput_traffic_pps());
            detailTrafficContrastInfo.setAttack_traffic_pps(f.getAttack_traffic_pps());
            detailTrafficContrastInfo.setAttack_traffic_bps(f.getAttack_traffic_bps());
            detailTrafficContrastInfo.setTimestamp(f.getSave_time());
            list.add(detailTrafficContrastInfo);
        }

        return  list;
    }

    public DetailInputTrafficInfo getLatestInputTrafficInfo () {
        TrafficStatisticsEntity trafficStatisticsEntity = trafficStatisticsDao.getLatestInputTrafficInfo();
        if (trafficStatisticsEntity == null) {
            return null;
        }

        DetailInputTrafficInfo detailInputTrafficInfo = new DetailInputTrafficInfo();
        detailInputTrafficInfo.setIcmp_bps(trafficStatisticsEntity.getIcmp_bps());
        detailInputTrafficInfo.setIcmp_pps(trafficStatisticsEntity.getIcmp_pps());
        detailInputTrafficInfo.setUdp_bps(trafficStatisticsEntity.getUdp_bps());
        detailInputTrafficInfo.setUdp_pps(trafficStatisticsEntity.getUdp_pps());
        detailInputTrafficInfo.setTcp_bps(trafficStatisticsEntity.getTcp_bps());
        detailInputTrafficInfo.setTcp_pps(trafficStatisticsEntity.getTcp_pps());
        detailInputTrafficInfo.setOther_bps(trafficStatisticsEntity.getOther_bps());
        detailInputTrafficInfo.setOther_pps(trafficStatisticsEntity.getOther_pps());

        return detailInputTrafficInfo;
    }

    public class DetailTrafficContrastInfo {
        private long input_traffic_pps;
        private long input_traffic_bps;
        private long output_traffic_pps;
        private long output_traffic_bps;
        private long attack_traffic_pps;
        private long attack_traffic_bps;
        private Date timestamp;

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

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
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
