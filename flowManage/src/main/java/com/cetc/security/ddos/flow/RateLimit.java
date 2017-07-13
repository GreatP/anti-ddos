package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import org.springframework.web.client.RestClientException;

//import com.cetc.security.ddos.controller.adapter.MeterConfig;


public class RateLimit {

	ProtectObjectFlow poFlow;
	FlowConfig flow;
	Controller controller;
	String nodeID;
    int flowID;
	int kbps;
	int pbps;
	int meterID;

	public RateLimit(ProtectObjectFlow poFlow, int FlowId)
	{
		this.poFlow = poFlow;
		this.flowID = FlowId;
		//this.kbps = kbps;
		//this.pbps = pbps;
		this.flow = poFlow.getFlowByFlowId(FlowId);
		this.controller = poFlow.getControl();
		this.nodeID = poFlow.getNetNodeEntity().getSwId();

		//MeterConfig meterConfig = new MeterConfig();
	}

	public int startRateLimit(double kbps, double pbps) throws Exception
	{
		
		meterID = controller.AddMeter(flowID, kbps, pbps, nodeID);
		
		if (meterID == -1)
		{
			return -1;			
		}
		
		controller.BindMeter(meterID, flow);
		
		return 1;
	}


	public void endRateLimit() throws Exception
	{
		controller.UnBindMeter(meterID, flow);
		
	}


}
