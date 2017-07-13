package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.AutoLearnBaseValueEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lb on 2015/8/13.
 */
//@Repository 用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
@Repository("autoLearnBaseValueDao")
public class AutoLearnBaseValueDao extends AbstractBaseDao<AutoLearnBaseValueEntity>{

    public AutoLearnBaseValueEntity find(int flowid,int week,int hour) {
        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("poname", poname);
        //params.put("protocal", protocal);
        params.put("flowid", flowid);
        params.put("week", week);
        params.put("hour", hour);
        List<AutoLearnBaseValueEntity> result = super.findByProperties(params);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    //查询一周七天的学习基线值
    /*public List<AutoLearnBaseValueEntity> findOrder(String name,int protocal) {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poname", name);
        params.put("protocal", protocal);

        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("week", "asc"));
        list.add(new OrderParam("hour", "asc"));


        List<AutoLearnBaseValueEntity> result = super.findOrderedByListProperties(params, list);
        return result;
    }*/
    public List<AutoLearnBaseValueEntity> findOrder(int flowid) {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flowid", flowid);

        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("week", "asc"));
        list.add(new OrderParam("hour", "asc"));


        List<AutoLearnBaseValueEntity> result = super.findOrderedByListProperties(params, list);
        return result;
    }

    //查询一周中某天的学习基线值
    /*public List<AutoLearnBaseValueEntity> findOrderOneday(String name,int protocal,int week) {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poname", name);
        params.put("protocal", protocal);
        params.put("week", week);

        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        //list.add(new OrderParam("week", "asc"));
        list.add(new OrderParam("hour", "asc"));


        List<AutoLearnBaseValueEntity> result = super.findOrderedByListProperties(params, list);
        return result;
    }*/

    public List<AutoLearnBaseValueEntity> findOrderOneday(int flowid,int week) {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flowid", flowid);
        params.put("week", week);

        //设置排序条件
        List<OrderParam> list = new ArrayList<OrderParam>();
        //list.add(new OrderParam("week", "asc"));
        list.add(new OrderParam("hour", "asc"));


        List<AutoLearnBaseValueEntity> result = super.findOrderedByListProperties(params, list);
        return result;
    }

    //删除一个基线值,注意：在DAO层，删除添加更新操作必须有事物注解@Transactional，用于保证数据库操作的原子性
    @Transactional
    public void delOneBaseValue(int flowid,int week,int hour) {
        //设置查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flowid", flowid);
        params.put("week", week);
        params.put("hour", hour);

        super.deleteByProperties(params);

    }
}
