package com.cetc.security.ddos.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.AttackDefenseEntity;

import org.springframework.stereotype.Repository;

/**
 * Created by hanqsheng on 2016/05/12
 */
@Repository
public class AttackDefenseDao extends AbstractBaseDao<AttackDefenseEntity> {
    public List<AttackDefenseEntity> getAllAttackDefenseInfo() {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("save_time", "asc"));

        List<AttackDefenseEntity> result = super.findOrderedByListProperties(null, list);
        return result;
    }

    public List<AttackDefenseEntity> findOrderedDescBySaveTime(int start, int limit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("save_time", "asc");
        return findOrderedByProperties(null, orders, start, limit);
    }

    public AttackDefenseEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<AttackDefenseEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }
}
