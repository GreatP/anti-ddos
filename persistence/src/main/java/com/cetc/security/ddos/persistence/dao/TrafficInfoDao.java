package com.cetc.security.ddos.persistence.dao;

import java.util.*;

import com.cetc.security.ddos.persistence.TrafficInfoEntity;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

/**
 * Created by zhangtao on 2015/7/22.
 */
@Repository
public class TrafficInfoDao extends AbstractBaseDao<TrafficInfoEntity> {
	public TrafficInfoEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<TrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }
	
	public TrafficInfoEntity findByPoId(int po_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("po_id", po_id);
        List<TrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }
	
	@Transactional
	public void delByType(int type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        super.deleteByProperties(params);
    }
	
	/*find traffic by time range and po, and order by time*/
	public List<TrafficInfoEntity> findTrafficByTime(int po_id, int type,long start_time, long end_time) {
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put("po_id", po_id);
        params.put("type", type);
        List<TrafficInfoEntity> result =  findByTimeRange(params,"time",start_time,end_time);
        if (result == null) {
            return null;
        }

        return result;
    }

    public List<TrafficInfoEntity> findTrafficInfoByDateTime(Date start_time, Date end_time) {
        List<TrafficInfoEntity> result =  findByDateTimeRange(null, "time", start_time, end_time);
        if (result == null) {
            return null;
        }

        return result;

    }

    public List<TrafficInfoEntity> findTrafficInfoByTimeRange(long start_time, long end_time) {
        List<TrafficInfoEntity> result =  findByTimeRange(null, "time", start_time, end_time);
        if (result == null) {
            return null;
        }

        return result;

    }
	
	/*find traffic by time range and po, and order by time*/
	public List<TrafficInfoEntity> findTrafficByBpsOrder(int po_id, int type,long start_time, long end_time) {
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put("po_id", po_id);
        params.put("type", type);
        
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("bps_all", "desc"));
        
        //List<TrafficInfoEntity> result =  findByTimeRangeBpsOrder(params,"time",start_time,end_time);
        List<TrafficInfoEntity> result = findOrderedByTimeListProperties(params, list, "time", start_time,end_time);
        if (result == null) {
            return null;
        }

        return result;
    }
}
