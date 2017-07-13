package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.EventEntity;
import com.cetc.security.ddos.persistence.FlowStatisticsEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.*;

/**
 * Created by zhangtao on 2015/10/10.
 */
@Repository
public class EventDao extends AbstractBaseDao<EventEntity> {
    public List<EventEntity> findOrderedDesc(int start, int limit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("time", "desc");
        orders.put("id", "desc");

        return findOrderedByProperties(null, orders, start, limit);
    }


    @Transactional
    public void deleteBeforeTime(long time) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("time", new Date(time));
        List<Map<String, Object>> p = new ArrayList<Map<String, Object>>();
        p.add(map);

        List<String> conditionList = new ArrayList<String>();
        conditionList.add("<");

        deleteByProperties(p, conditionList);
    }
}
