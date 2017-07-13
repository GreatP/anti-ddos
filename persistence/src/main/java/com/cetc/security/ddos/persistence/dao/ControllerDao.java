package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.ControllerEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2015/7/23.
 */
@Repository
public class ControllerDao extends AbstractBaseDao<ControllerEntity> {
    public List<ControllerEntity> find(short flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", flag);
        return findByProperties(params);
    }

    public ControllerEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<ControllerEntity> result = findByProperties(params);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }

    protected List<Map<String, Object>> setFlag(short flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", flag);
        List<Map<String, Object>> p = new ArrayList<Map<String, Object>>();
        p.add(params);
        return p;
    }


    protected List<String> setNotEqual() {
        List<String> conditions = new ArrayList<String>();
        conditions.add("<>");
        return conditions;
    }


    public List<ControllerEntity> findOrderedDescByNotEqualFlag(short flag, int start, int limit) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        return findOrderedDescById(p, conditions, start, limit);
    }

    public long countByNotEqualFlag(short flag) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        return countByCondition(p, conditions);
    }

    public List<ControllerEntity> findNotEqualFlag(short flag) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");
        return findOrderedByProperties(p, conditions, orders);
    }

    public ControllerEntity find(String ip, int port) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("port", port);
        List<ControllerEntity> ControllerEntities = findByProperties(params);
        if (ControllerEntities == null) {
            return null;
        }
        return ControllerEntities.get(0);
    }


}
