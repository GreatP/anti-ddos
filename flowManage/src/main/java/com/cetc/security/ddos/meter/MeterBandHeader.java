package com.cetc.security.ddos.meter;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * "meter-band-header": [
                    {
                        "band-id": "0",
                        "drop-rate": "30000"
                    }
 * */

public class MeterBandHeader {
	
	@JsonProperty("band-id")
	String BandId;
	
	@JsonProperty("meter-band-types")
	MeterBandTypes meterBandTypes;
	
	@JsonProperty("drop-rate")
	String DropRate;
	
	@JsonProperty("drop-burst-size")
	String DropBurstSize;
	
	public MeterBandHeader(int BandId, int DropRate)
	{		
		this.BandId = String.valueOf(BandId);
		this.DropRate = String.valueOf(DropRate);
		this.DropBurstSize = String.valueOf(DropRate);
	}	
}
