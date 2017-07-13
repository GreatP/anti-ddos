package com.cetc.security.ddos.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.AutoLearnBaseValueEntity;

import com.cetc.security.ddos.persistence.PoTrafficInfoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hanqsheng on 2016/05/12
 */
@Repository
public class PoTrafficInfoDao extends AbstractBaseDao<PoTrafficInfoEntity> {
    public PoTrafficInfoEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<PoTrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public PoTrafficInfoEntity findByPoId(int poId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("po_id", poId);
        List<PoTrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public List<PoTrafficInfoEntity> getPoTrafficInfoList(String unit) {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam(unit, "desc"));

        List<PoTrafficInfoEntity> result = super.findOrderedByListProperties(null, list);
        return result;
    }

    public List<PoTrafficInfoEntity> getOrderedDescByFlowRate(int start, int limit, String unit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put(unit, "desc");
        return findOrderedByProperties(null, orders, start, limit);
    }

    @Transactional
    public void delByPoId(int po_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("po_id", po_id);
        super.deleteByProperties(params);
    }

}
