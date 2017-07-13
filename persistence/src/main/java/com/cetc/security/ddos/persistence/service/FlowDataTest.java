package com.cetc.security.ddos.persistence.service;

public class FlowDataTest {
	
	//private static FlowDataService flowData = new FlowDataService();
	private static FlowDataService flowData;
	
	static {
		try{
			flowData = new FlowDataService();
		
		} catch (Exception ex){
			
		}
	}
	
	public static void main(String[] args) {

		String pJson = null;

		System.out.println("ooxx");
		// flowData.addFlowData("xxoo", 2, "hour", 1709, 10.2, 21.33);
		// flowData.addFlowData("xxoo", 2, "hour", 1707, 11.2, 22.33);
		pJson = flowData.getAllFlowData("po1", 6, "day");

		if (pJson != null) {
			System.out.println("json" + pJson);

		}
		System.out.println("xxoo");
	}

}
