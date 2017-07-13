package com.cetc.security.ddos.persistence;



import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowDataEntity {
	
	/*
	String POName;
	
	int flowIndex;
	*/
	
	@JsonProperty("timestamp")
	long timeStamp;
	
	@JsonProperty("pkt")
	double pktRate;
	
	@JsonProperty("byte")
	double byteRate;
	//String type;
	
	
	public FlowDataEntity(long timeStamp, double pktRate, double byteRate)
	{		
		this.timeStamp = timeStamp;
		this.pktRate = pktRate;
		this.byteRate = byteRate; 
	}
	
	
}


