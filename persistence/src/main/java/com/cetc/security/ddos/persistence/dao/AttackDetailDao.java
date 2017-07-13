package com.cetc.security.ddos.persistence.dao;

import java.util.*;
import javax.persistence.Query;

import com.cetc.security.ddos.persistence.AttackDetailEntity;

import com.cetc.security.ddos.persistence.AttackTypeCount;
import org.springframework.stereotype.Repository;

/**
 * Created by hanqsheng on 2016/05/24
 */
@Repository
public class AttackDetailDao extends AbstractBaseDao<AttackDetailEntity> {
    public List<AttackDetailEntity> getAllAttackDetailInfoOderedAscById() {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("id", "asc"));

        List<AttackDetailEntity> result = super.findOrderedByListProperties(null, list);
        return result;
    }

    public List<AttackDetailEntity> getAllAttackDetailInfo() {
        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("startTime", "desc"));

        List<AttackDetailEntity> result = super.findOrderedByListProperties(null, list);
        return result;
    }


    public List<AttackDetailEntity> findOrderedAscByStartTime(int start, int limit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("startTime", "desc");
        return findOrderedByProperties(null, orders, start, limit);
    }

    /*find traffic by time range and po, and order by time*/
    public List<AttackDetailEntity> findAttackDetailByTime(Date start_time, Date end_time) {
        List<AttackDetailEntity> result =  findByDateTimeRange(null, "startTime", start_time, end_time);
        if (result == null) {
            return null;
        }

        return result;
    }

    public AttackDetailEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<AttackDetailEntity> result =  super.findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public List<AttackDetailEntity> findByAttackIpId(int attackip_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("attackip_id", attackip_id);
        List<AttackDetailEntity> result =  super.findByProperties(params);

        return result;
    }

    public List<AttackDetailEntity> findByStatus(int status) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        List<AttackDetailEntity> result =  super.findByProperties(params);

        return result;
    }
    public List<AttackDetailEntity> findByThree(String attackip,int status,int po_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("attackip", attackip);
        params.put("status", status);
        params.put("poId", po_id);
        List<AttackDetailEntity> result =  super.findByProperties(params);

        return result;
    }

    /*group by和其他查询函数有差别*/
    public List<AttackTypeCount> groupByType() {
        List<AttackTypeCount> attacks = new ArrayList<AttackTypeCount>();
        String jpql = " select o.attackType, count(o) as cnt from AttackDetailEntity o group by o.attackType order by cnt desc";
        Query query = super.entityManager.createQuery(jpql);
        List<Object[]> list = query.getResultList();
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] objects = (Object[])iterator.next();
                AttackTypeCount attackTypeCount = new AttackTypeCount();
                attackTypeCount.setAttack_type((Integer)objects[0]);
                attackTypeCount.setCount((Long)objects[1]);
                attacks.add(attackTypeCount);
            }
        }

        return attacks;
    }

    /*select attackip,sum(peak) as newpeak from attack_detail group by attackip order by newpeak desc*/
    public List<Object[]> findByIpPeakTop10() {
        String jpql = " select o.attackip, sum(o.peak) as newpeak from AttackDetailEntity o group by o.attackip order by newpeak desc";
        Query query = super.entityManager.createQuery(jpql);
        List<Object[]> list = query.getResultList();

        return list;
    }

    public List<AttackDetailEntity> findByPoId(int poId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poId", poId);
        return findByProperties(params);
    }
}
