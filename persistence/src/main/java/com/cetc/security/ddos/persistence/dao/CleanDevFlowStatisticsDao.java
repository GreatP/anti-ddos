package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.FlowEntity;
import org.springframework.stereotype.Repository;
import com.cetc.security.ddos.persistence.CleanDevFlowStatisticsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zhangtao on 2015/10/8.
 */
@Repository
public class CleanDevFlowStatisticsDao extends AbstractBaseDao<CleanDevFlowStatisticsEntity> {
    public CleanDevFlowStatisticsEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<CleanDevFlowStatisticsEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public List<CleanDevFlowStatisticsEntity> getAllCleanDevStatInfo() {
        return super.findAll();
    }
}
