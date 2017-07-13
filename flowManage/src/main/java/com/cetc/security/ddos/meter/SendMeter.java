package com.cetc.security.ddos.meter;

import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import com.cetc.security.ddos.flow.NetNode;

public class SendMeter {
	
	Controller MeterController;
	NetNode MeterNetNode;
	//int MeterId;
	int FlowId;
	int kbps;
	int pbps;
	
	
	
	public Controller getMeterController() {
		return MeterController;
	}

	public void setMeterController(Controller meterController) {
		MeterController = meterController;
	}

	public NetNode getMeterNetNode() {
		return MeterNetNode;
	}

	public void setMeterNetNode(NetNode meterNetNode) {
		MeterNetNode = meterNetNode;
	}

	public int getFlowId() {
		return FlowId;
	}

	public void setFlowId(int flowId) {
		FlowId = flowId;
	}

	public int getKbps() {
		return kbps;
	}

	public void setKbps(int kbps) {
		this.kbps = kbps;
	}

	public int getPbps() {
		return pbps;
	}

	public void setPbps(int pbps) {
		this.pbps = pbps;
	}

	public SendMeter(Controller controller, NetNode netnode, int Flowid){
		this.MeterController = controller;
		this.MeterNetNode = netnode;
		this.FlowId = Flowid;		
	}
	
	public void PutMeterkbps(int droprate){
		String url;
		String meterJson;
		
		FlowConfig flowConfig = new FlowConfig();
		
		BuildMeters buildMeters = new BuildMeters("meter-kbps", FlowId, droprate);
		
		meterJson = buildMeters.BuildMetersJson();
		
		System.out.println("meter json is " + meterJson);	
		
		//url ="http://" + MeterController.getControllerIp() + ":" + Integer.toString(MeterController.getControllerPort()) + "/restconf/config/opendaylight-inventory:nodes/node/"+MeterNetNode.swId+"/flow-node-inventory:meter/" + FlowId;
		
		//System.out.println("meter url is " + url);	
		
		//flowConfig.SetFlowInterface(url, meterJson, MeterController);		
	}
	
	/*
	public void PutMeterpktps(int droprate){
		String url;
		String meterJson;
		
		FlowConfig flowConfig = new FlowConfig();
		
		BuildMeters buildMeters = new BuildMeters("meter-pktps", FlowId * 2 + 1, droprate);
		
		meterJson = buildMeters.BuildMetersJson();
		
		System.out.println("meter json is " + meterJson);	
		
		url ="http://" + MeterController.getControllerIp() + ":" + Integer.toString(MeterController.getControllerPort()) + "/restconf/config/opendaylight-inventory:nodes/node/"+MeterNetNode.swId+"/flow-node-inventory:meter/" + (FlowId * 2 + 1);
		
		System.out.println("meter url is " + url);	
		
		flowConfig.SetFlowInterface(url, meterJson, MeterController);		
	}
	*/
	
	public void PutMeter(double kbps, double pktps)
	{		
		//PutMeterkbps(kbps);
		//PutMeterpktps(pktps);
	
		//MeterNetNode.AddFlowMeter(FlowId, MeterController);
	}
	
	
	public void UnBindMeter()
	{			
		//MeterNetNode.DelFlowMeter(FlowId, MeterController);
	}
		

	public static void main(String[] args) {
		
		String meterJson;
		
		int meterId = 7;
		
		String restPrefix;
		
		//Controller contrl = new Controller("192.168.100.151", 8181, "admin", "admin");
		FlowConfig flowConfig = new FlowConfig();
		
		BuildMeters buildMeters = new BuildMeters("meter-kbps",meterId, 3000);
		
		meterJson = buildMeters.BuildMetersJson();
	
		System.out.println("11 json is " + meterJson);	
		
		//restPrefix = "http://" + contrl.getControllerIp() + ":" + Integer.toString(contrl.getControllerPort()) + "/restconf/config/opendaylight-inventory:nodes/node/openflow:361114189758663/flow-node-inventory:meter/" + meterId;
		//url = restPrefix + urlPrefix;
		
		//System.out.println("url is " + restPrefix);				
		
		//flowConfig.SetFlowInterface(restPrefix, meterJson, contrl);
	}
	
}
