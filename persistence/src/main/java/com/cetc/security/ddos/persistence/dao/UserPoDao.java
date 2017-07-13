package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.CleanDevEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.UserPoEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2016/6/20.
 */
@Repository
public class UserPoDao extends AbstractBaseDao<UserPoEntity>  {
    public List<UserPoEntity> findById(int id, int start, int limit) {
        List<Map<String, Object>> p = new ArrayList<Map<String, Object>>();
        List<String> conditions = new ArrayList<String>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEntity", userEntity);
        p.add(params);
        conditions.add("=");

        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");

        return findOrderedByProperties(p, conditions, orders, start, limit);
    }

    public List<UserPoEntity> findById(int id) {
        List<Map<String, Object>> p = new ArrayList<Map<String, Object>>();
        List<String> conditions = new ArrayList<String>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEntity", userEntity);
        p.add(params);
        conditions.add("=");

        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");

        return findOrderedByProperties(p, conditions, orders);
    }

    public Map<String, Object> getPoParam(int poId) {
        Map<String, Object> params = new HashMap<String, Object>();
        ProtectObjectEntity po = new ProtectObjectEntity();
        po.setId(poId);
        params.put("protectObjectEntity", po);

        return params;
    }

    public void delByPoId(int poId) {
        super.deleteByProperties(getPoParam(poId));
    }

    public UserPoEntity findByPoId(int poId) {
        Map<String, Object> params = getPoParam(poId);

        List<UserPoEntity> l = findByProperties(params);
        if (l == null) {
            return null;
        } else {
            return l.get(0);
        }
    }
}
