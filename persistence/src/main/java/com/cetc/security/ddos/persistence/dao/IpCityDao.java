package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.IpCityEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lb on 2016/5/12.
 */
//@Repository 用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
@Repository("ipCityDao")
public class IpCityDao extends AbstractBaseDao<IpCityEntity> {
    public List<IpCityEntity> findOrder() {

        //设置排序条件,根据count从大到小
        List<OrderParam> list = new ArrayList<OrderParam>();
        list.add(new OrderParam("count", "desc"));

        return super.findOrderedByListProperties(null,list);
    }
    public IpCityEntity findCity(String city) {

        //设置查询条件
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("city",city);

        List<IpCityEntity> result = super.findByProperties(params);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }
}
