package com.cetc.security.ddos.flow;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cetc.security.ddos.common.utils.AntiLogger;
import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import com.cetc.security.ddos.persistence.ControllerIfaceEntity;

public class ForwardFlow {
	static Logger logger = AntiLogger.getLogger(ForwardFlow.class);
	private static int forwardFlowId = 0;
    private Controller controller;
    private Map<Integer, FlowConfig> flowConfigMap;
    
    public ForwardFlow(Controller controller) {
        this.controller = controller;
        flowConfigMap = new HashMap<Integer, FlowConfig>();
    }
    
    
    public Controller getController() {
		return controller;
	}


	private void putFlow(String inPort, String outPort) throws Exception {
		FlowConfig flowConfig = new FlowConfig();  
		
    	flowConfig.setPriority(1);

    	flowConfig.setNodeSwId("br0");
    	flowConfig.setPath("/ovs/bin/");
    	flowConfig.setInputNode(inPort);    
    	flowConfig.setOutputNode(outPort);
    	controller.putFlowInfo(flowConfig);
        flowConfigMap.put(forwardFlowId, flowConfig);
        
        forwardFlowId++;
    }
    
    public void createFlow(ControllerIfaceEntity iface) throws Exception {    	 
    	try {
    		putFlow(iface.getInPort(), iface.getOutPort());
    		putFlow(iface.getOutPort(), iface.getInPort());
    	} catch (Exception e) {
    		logger.error("Create connection:" + controller.getControllerIp() + " flow fail:" + e.toString());
    		throw e;
    	}
    }
    
    public void delFlows()
    {
        if (flowConfigMap == null)
        {
            logger.warn("delConnectionFlows: flow array is not init!");
            return;
        }

        //Iterator<Map.Entry<Integer, FlowConfig>> it = flowConfigMap.entrySet().iterator();

        for (Map.Entry<Integer, FlowConfig> entry : flowConfigMap.entrySet()) {
            // while (it.hasNext()) {
            //Map.Entry<Integer, FlowConfig> entry = it.next();
            try {
                controller.delFlowInfo(entry.getValue());
            } catch (Exception e) {
                logger.error("Delete connection flow:" + entry.getValue().getFlowId() + " fail");
            }
        }

        flowConfigMap.clear();
    }
}
