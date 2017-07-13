package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.common.type.ControllerType;
import com.cetc.security.ddos.common.type.DeviceType;
import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.ControllerFactory;
import com.cetc.security.ddos.persistence.CleanDevEntity;
import com.cetc.security.ddos.persistence.FlowEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by zhangtao on 2016/6/7.
 */
public class CleanDev {
    private DeviceType devType;
    private Controller controller;
    private Map<Integer, PoFlow> cleanPOMap;

    CleanDev(DeviceType devType, int id, ControllerType type, String ip, int port, String user, String passw) {
        /*
        controller = ControllerFactory.getControllerInstance(cleanDevEntity.getId(), ControllerType.SSH_OVS,
                cleanDevEntity.getIp(), 22, cleanDevEntity.getUser(), cleanDevEntity.getPassword());
        */
        controller = ControllerFactory.getControllerInstance(id, type, ip, port, user, passw);
        cleanPOMap = new HashMap<Integer, PoFlow>();
        this.devType = devType;
    }

    public Controller getController() {
        return controller;
    }

    void addPO(ProtectObjectEntity po, List<FlowEntity> flowEntities) throws Exception {
        PoFlow cleanPO = new PoFlow(controller, po, devType);
        cleanPO.addFlows(flowEntities);
        cleanPOMap.put(po.getId(), cleanPO);
    }

    void delPO(ProtectObjectEntity po) {
        delPO(po.getId());
    }

    void delPO(int poId) {
        PoFlow cleanPO = cleanPOMap.remove(poId);
        if (cleanPO == null) {
            return;
        }
        cleanPO.delFlows();
    }

    void clearPO() {
        for (Map.Entry<Integer, PoFlow> entry : cleanPOMap.entrySet()) {
            if (entry.getValue() == null) {
                return;
            }
            entry.getValue().delFlows();
        }
    }
}
