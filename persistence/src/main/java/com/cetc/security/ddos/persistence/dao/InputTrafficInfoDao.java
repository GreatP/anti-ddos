package com.cetc.security.ddos.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.InputTrafficInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by hanqsheng on 2016/05/12
 */
@Repository
public class InputTrafficInfoDao extends AbstractBaseDao<InputTrafficInfoEntity> {
	public InputTrafficInfoEntity findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        List<InputTrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

    public InputTrafficInfoEntity findByProtocol(int protocol) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("protocol", protocol);
        List<InputTrafficInfoEntity> result =  findByProperties(params);
        if (result == null) {
            return null;
        }

        return result.get(0);
    }

}
