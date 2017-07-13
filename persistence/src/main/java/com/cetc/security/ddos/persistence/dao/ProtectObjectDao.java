package com.cetc.security.ddos.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.CleanDevEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;


import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.stereotype.Repository;


/**
 * Created by zhangtao on 2015/7/23.
 */
@Repository
public class ProtectObjectDao extends AbstractBaseDao<ProtectObjectEntity> {
    private static Logger logger = AntiLogger.getLogger(ProtectObjectDao.class);

    public List<ProtectObjectEntity> find(short flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", (short)flag);
        return findByProperties(params);
    }


    public List<ProtectObjectEntity> findByControllerIdAndNotEqualFlag(int id, short flag) {
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

    public List<ProtectObjectEntity> findByCleanDevIdAndNotEqualFlag(int id, short flag) {
        List<Map<String, Object>> p = new ArrayList<Map<String, Object>>();
        List<String> conditions = new ArrayList<String>();

        CleanDevEntity cleanDevEntity = new CleanDevEntity();
        cleanDevEntity.setId(id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cleanDevEntity", cleanDevEntity);
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
    
    public ProtectObjectEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<ProtectObjectEntity> result = findByProperties(params);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }

    public ProtectObjectEntity findByName(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        List<ProtectObjectEntity> result = findByProperties(params);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }
    
    public ProtectObjectEntity findByIp(String ip) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("netWork", ip);
        List<ProtectObjectEntity> result = findByProperties(params);
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


    public List<ProtectObjectEntity>  findOrderedDescByNotEqualFlag(short flag, int start, int limit) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        return findOrderedDescById(p, conditions, start, limit);
    }
    
    public List<ProtectObjectEntity> findNotEqualFlag(short flag) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");
        return findOrderedByProperties(p, conditions, orders);
    }


    public long countByNotEqualFlag(short flag) {
        List<Map<String, Object>> p = setFlag(flag);
        List<String> conditions = setNotEqual();
        return countByCondition(p, conditions);
    }

    public ProtectObjectEntity findById(int id, Map<Integer, ProtectObjectEntity> map) {
        /* 优化性能，防止下次遇到相同PO，又查询数据库 */
        ProtectObjectEntity po = map.get(id);
        if (po == null) {
            po = findById(id);
            if (po == null) {
                logger.debug("Don't find PO by poId:" + id);
                return null;
            }
            map.put(id, po);
        }

        return po;
    }
}
