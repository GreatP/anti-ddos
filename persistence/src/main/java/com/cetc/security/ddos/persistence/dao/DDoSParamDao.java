package com.cetc.security.ddos.persistence.dao;

import com.cetc.security.ddos.persistence.DDoSParamEntity;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2015/7/27.
 */
@Repository
public class DDoSParamDao extends AbstractBaseDao<DDoSParamEntity> {
    public DDoSParamEntity findByControllerId(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("controllerId", id);
        List<DDoSParamEntity> result = findByProperties(params);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }

    public void updateByControllerId(DDoSParamEntity dDoSParamEntity, int id) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("detectionInterval", dDoSParamEntity.getDetectionInterval());
        params.put("detectionDeviationPercentage", dDoSParamEntity.getDetectionDeviationPercentage());
        params.put("attackSuspicionsThreshold", dDoSParamEntity.getAttackSuspicionsThreshold());
        params.put("recoverNormalThreshold", dDoSParamEntity.getRecoverNormalThreshold());
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("controllerId", id);
        updateByPropertiesNoTrans(params, where);
    }
}
