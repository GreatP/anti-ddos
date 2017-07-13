package com.cetc.security.ddos.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.FlowEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangtao on 2015/7/22.
 */
@Repository
public class FlowDao extends AbstractBaseDao<FlowEntity> {
      public List<FlowEntity> findByPoId(int poId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poId", poId);
        return findByProperties(params);
    }

      public void delFlowsByPoId(int poId) {
          Map<String, Object> params = new HashMap<String, Object>();
          params.put("poId", poId);
          deleteByProperties(params);
      }

    public FlowEntity findByPoIdAndProtocol(int poId, short protocol) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poId", poId);
        params.put("protocol", protocol);
        List<FlowEntity> result = findByProperties(params);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }
}
