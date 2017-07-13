package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.common.type.ControllerType;
import com.cetc.security.ddos.common.type.DeviceType;
import com.cetc.security.ddos.common.utils.AntiLogger;
import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.persistence.CleanDevEntity;
import com.cetc.security.ddos.persistence.FlowEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2016/6/2.
 */
public class FlowTable {
    private static Logger logger = AntiLogger.getLogger(FlowTable.class);
    private Map<Integer, CleanDev> cleanDevMap;

    public FlowTable() {
        cleanDevMap = new HashMap<Integer, CleanDev>();
    }

    public Map<Integer, CleanDev> getCleanDevMap() {
        return cleanDevMap;
    }

    public void setCleanDevMap(Map<Integer, CleanDev> cleanPOMap) {
        this.cleanDevMap = cleanDevMap;
    }

    public synchronized void addCleanDev(DeviceType devType, int id, ControllerType type, String ip, int port, String user, String passw) {
        CleanDev cleanDev = new CleanDev(devType, id, type, ip, port, user, passw);
        cleanDevMap.put(id, cleanDev);
    }

    public synchronized Controller getCleanDevController(int cleanDevId) {
        CleanDev cleanDev = cleanDevMap.get(cleanDevId);
        if (cleanDev == null) {
            return null;
        }

        return cleanDev.getController();
    }

    public synchronized void delCleanDev(int cleanDevId) {
        CleanDev cLeanDev = cleanDevMap.remove(cleanDevId);
        if (cLeanDev == null) {
            return;
        }
        cLeanDev.clearPO();
    }

    public synchronized void delCleanDev(CleanDevEntity cleanDevEntity) {
        delCleanDev(cleanDevEntity.getId());
    }

    public synchronized void clearFlowTable() {
        for (Map.Entry<Integer, CleanDev> entry : cleanDevMap.entrySet()) {
            if (entry.getValue() == null) {
                return;
            }

            entry.getValue().clearPO();
        }
    }

    public synchronized void addCleanFlows(int cleanDevId, ProtectObjectEntity po, List<FlowEntity> flowEntities) throws Exception {
        CleanDev cleanDev = cleanDevMap.get(cleanDevId);
        cleanDev.addPO(po, flowEntities);
    }

    public synchronized void delCleanFlows(int cleanDevId, ProtectObjectEntity po) {
        CleanDev cleanDev = cleanDevMap.get(cleanDevId);
        if (cleanDev == null) {
            return;
        }
        cleanDev.delPO(po);
    }
}
