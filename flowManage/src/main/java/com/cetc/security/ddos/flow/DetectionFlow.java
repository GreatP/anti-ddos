package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.persistence.FlowEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * Created by zhangtao on 2016/8/9.
 */
public class DetectionFlow extends AbstractFlow {
    private static int detectionId = 0;
    public DetectionFlow(Controller controller) {
        super(controller);
    }

    protected void createPOFlows(ProtectObjectEntity po, List<FlowEntity> flowEntities) throws Exception
    {
        try {
            FlowEntity f = new FlowEntity();
            f.setPriority((short)500);
            f.setEthType((short)0x800);
            f.setProtocol((short) 0);

            String path = "/ovs/bin/";
            //创建引流
            createOneFlow(detectionId, po, f, path, 0, null, po.getNetWork(), po.getInPort(), po.getGuidePort(), null);            
           

            detectionId++;
            if ((po.getReinjectionPort() != null) && !po.getReinjectionPort().equals("")) {
                //创建回注流
                createOneFlow(detectionId, po, f, path, 0, null, po.getNetWork(), po.getReinjectionPort(), po.getOutPort(), null);
                detectionId++;
            }
            
            //创建回包流
            createOneFlow(detectionId, po, f, path, 0, po.getNetWork(), null, po.getOutPort(), po.getInPort(), null);
            detectionId++;
            
            //创建源认证回包流
            createOneFlow(detectionId, po, f, path, 0, po.getNetWork(), null, po.getGuidePort(), po.getInPort(), null);
            detectionId++;
        } catch (Exception e) {
            logger.error("Create protect object:" + po.getName() + " detection flow fail:" + e.getMessage());
            delPOFlows();
            throw e;
        }
    }
}
