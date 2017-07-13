package com.cetc.security.ddos.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cetc.security.ddos.persistence.*;

@Repository
public class ControllerIfaceDao extends AbstractBaseDao<ControllerIfaceEntity> {
	public ControllerIfaceEntity findByControllerId(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("controllerId", id);
        List<ControllerIfaceEntity> result = findByProperties(params);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }
	
	public void updateByControllerId(ControllerIfaceEntity controllerIfaceEntity, int id) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("inPort", controllerIfaceEntity.getInPort());
        params.put("outPort", controllerIfaceEntity.getInPort());
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("controllerId", id);
        updateByPropertiesNoTrans(params, where);
    }
}
