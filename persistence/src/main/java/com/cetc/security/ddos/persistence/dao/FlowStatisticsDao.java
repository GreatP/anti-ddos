package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.FlowEntity;
import org.springframework.stereotype.Repository;
import com.cetc.security.ddos.persistence.FlowStatisticsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zhangtao on 2015/10/8.
 */
@Repository
public class FlowStatisticsDao extends AbstractBaseDao<FlowStatisticsEntity> {
    public FlowStatisticsEntity findByFlowId(int flowId) {
        Map<String, Object> params = new HashMap<String, Object>();
        FlowEntity fe = new FlowEntity();
        fe.setId(flowId);
        params.put("flowEntity", fe);
        List<FlowStatisticsEntity> result = findByProperties(params);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }

    public List<FlowStatisticsEntity> findOrderedDescByFlowId(int start, int limit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("flowEntity", "desc");

//        Map<String, Object> params = new HashMap<String, Object>();
//        FlowEntity fe = new FlowEntity();
//        fe.setFlag((short)3);
//        params.put("flowEntity.flag", 3);

        return findOrderedByProperties(null, orders, start, limit);
    }

    public void delNoTransByFlowId(int flowId) {
        FlowStatisticsEntity flowStatisticsEntity = findByFlowId(flowId);
        this.deleteNoTrans(flowStatisticsEntity);
    }
}
