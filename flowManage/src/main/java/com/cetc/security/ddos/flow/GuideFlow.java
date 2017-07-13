package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import org.springframework.web.client.RestClientException;

public class GuideFlow {

	ProtectObjectFlow poFlow;
	FlowConfig flow;
	Controller controller;
	String nodeID;
    int flowID;
    String OldOutPort;

	public GuideFlow(ProtectObjectFlow poFlow, int FlowId)
	{
		this.poFlow = poFlow;
		this.flowID = FlowId;
		this.flow = poFlow.getFlowByFlowId(FlowId);
		this.controller = poFlow.getControl();
		this.nodeID = poFlow.getNetNodeEntity().getSwId();
		this.OldOutPort = flow.getOutputNode();
	}

	public void startGuideFlow(String newOutPort) throws Exception
	{		
		controller.startGuideFlow(flow, OldOutPort, newOutPort);
	}


	public void endGuideFlow() throws Exception
	{
		controller.endGuideFlow(flow);
	}
}
