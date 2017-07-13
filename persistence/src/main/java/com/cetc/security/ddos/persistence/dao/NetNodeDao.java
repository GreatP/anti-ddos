package com.cetc.security.ddos.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.NetNodeEntity;
import org.springframework.stereotype.Repository;


/**
 * Created by zhangtao on 2015/7/23.
 */
@Repository
public class NetNodeDao extends AbstractBaseDao<NetNodeEntity> {
    public List<NetNodeEntity> find(short flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", flag);
        return findByProperties(params);
    }

    public NetNodeEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<NetNodeEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public List<NetNodeEntity> findByControllerIdAndNotEqualFlag(int id, short flag) {

        List<Map<String, Object>> p = new ArrayList<Map<String, Object>>();
        List<String> conditions = new ArrayList<String>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("controllerId", id);
        p.add(params);
        conditions.add("=");

        params = new HashMap<String, Object>();
        params.put("flag", flag);
        p.add(params);
        conditions.add("<>");

        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");

        return findOrderedByProperties(p, conditions, orders);
    }

    /*
    public NetNodeEntity findByswIdAndNotEqualFlag(String swid, short flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("swId", swid);
        params.put("flag", flag);
        List<NetNodeEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }
    */
	
	public NetNodeEntity findByName(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        List<NetNodeEntity> result =  findByProperties(params);
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

    public List<NetNodeEntity> findOrderedDescByNotEqualFlag(short flag, int start, int limit) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        return findOrderedDescById(p, conditions, start, limit);
    }

    public long countByNotEqualFlag(short flag) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        return countByCondition(p, conditions);
    }

    public List<NetNodeEntity> findNotEqualFlag(short flag) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");
        return findOrderedByProperties(p, conditions, orders);
    }

    public void updateFlagByControllerId(int id, short flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", flag);
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("controllerId", id);
        updateByPropertiesNoTrans(params, where);
    }
}
