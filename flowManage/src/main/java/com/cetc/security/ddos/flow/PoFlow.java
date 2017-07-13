package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.common.type.DeviceType;
import com.cetc.security.ddos.common.utils.AntiLogger;
import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.persistence.FlowEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by zhangtao on 2016/6/7.
 */
public class PoFlow {
    private static Logger logger = AntiLogger.getLogger(PoFlow.class);
    private Controller controller;
    private ProtectObjectEntity po;
    //CleanFlow cleanFlow;
    private AbstractFlow flow;

    PoFlow(Controller controller, ProtectObjectEntity po, DeviceType devType) {
        this.controller = controller;
        this.po = po;
        if (devType == DeviceType.DEVICE_CLEAN) {
            this.flow = new CleanFlow(controller);
        } else {
            this.flow = new DetectionFlow(controller);
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    protected void addFlows(List<FlowEntity> flowEntities)  throws Exception {
        flow.createPOFlows(po, flowEntities);
    }

    protected void delFlows() {
        flow.delPOFlows();
    }
}
