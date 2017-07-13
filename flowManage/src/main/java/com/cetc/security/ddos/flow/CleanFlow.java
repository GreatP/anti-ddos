package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.common.utils.AntiLogger;
import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import com.cetc.security.ddos.persistence.FlowEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2016/6/2.
 */
public class CleanFlow extends AbstractFlow {
    //private static Logger logger = AntiLogger.getLogger(CleanFlow.class);
    //private Controller controller;
    //private Map<Integer, FlowConfig> flowConfigMap;

    public CleanFlow(Controller controller) {
        super(controller);
    }


    protected void createPOFlows(ProtectObjectEntity po, List<FlowEntity> flowEntities) throws Exception
    {
        if (flowEntities == null)
        {
            logger.warn("the flow policy is null, please set the policy first!");
            return;
        }

        /*先查询，再下发，如发现flow id不一致的相同流，则先将其删除，再下发*/
        // getAllFlowsBeforeCreate();

        try {
            for (FlowEntity f : flowEntities) {
                createOneFlow(f.getId(), po, f, null, 1, null, po.getNetWork(), po.getCleanInport(), null, "2");
            }
        } catch (Exception e) {
            logger.error("Create protect object:" + po.getName() + " flow fail:" + e.getMessage());
            delPOFlows();
            throw e;
        }
    }


}
