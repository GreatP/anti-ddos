package com.cetc.security.ddos.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.AutoLearnBaseValueEntity;


import com.cetc.security.ddos.persistence.IpTrafficInfoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hanqsheng on 2016/05/12
 */
@Repository
public class IpTrafficInfoDao extends AbstractBaseDao<IpTrafficInfoEntity> {
    public IpTrafficInfoEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<IpTrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public IpTrafficInfoEntity findByIp(String ip) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        List<IpTrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public List<IpTrafficInfoEntity> getIpFlowInfoTop10(String unit) {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam(unit, "desc"));

        List<IpTrafficInfoEntity> result = super.findOrderedByListProperties(null, list);
        return result;
    }

    public List<IpTrafficInfoEntity> findOrderedDescByFlowRate(int start, int limit, String unit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put(unit, "desc");
        return findOrderedByProperties(null, orders, start, limit);
    }

    @Transactional
    public void delByIp(String ip) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip);
        super.deleteByProperties(params);
    }

}
