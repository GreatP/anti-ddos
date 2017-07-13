package com.cetc.security.ddos.persistence.dao;

import java.sql.Timestamp;
import java.util.*;

import com.cetc.security.ddos.persistence.TrafficStatisticsEntity;

import org.springframework.stereotype.Repository;

/**
 * Created by hanqsheng on 2016/05/12
 */
@Repository
public class TrafficStatisticsDao extends AbstractBaseDao<TrafficStatisticsEntity> {
	public TrafficStatisticsEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<TrafficStatisticsEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public List<TrafficStatisticsEntity> findTrafficStatisticsByTime(Date start_time, Date end_time) {
        List<TrafficStatisticsEntity> result =  findByDateTimeRange(null, "save_time", start_time, end_time);
        if (result == null) {
            return null;
        }

        return result;

    }

    public TrafficStatisticsEntity getLatestInputTrafficInfo() {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("save_time", "desc"));

        List<TrafficStatisticsEntity> result = super.findOrderedByListProperties(null, list);
        return result.get(0);
    }
}
