package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.AttackIpEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lb on 2016/5/17.
 */
//@Repository 用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
@Repository("attackIpDao")
public class AttackIpDao extends AbstractBaseDao<AttackIpEntity> {
    public List<AttackIpEntity> findNotHandle() {
        //设置查询条件
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("handled",0);

        return super.findByProperties(params);
    }

    public AttackIpEntity findByid(int id) {
        //设置查询条件
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);

        List<AttackIpEntity> result = super.findByProperties(params);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

}
