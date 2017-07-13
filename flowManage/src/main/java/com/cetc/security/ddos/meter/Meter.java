package com.cetc.security.ddos.meter;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * {
    "meter": [
        {
            "flags": "meter-kbps",
            "meter-id": "0",
            "meter-band-headers": {
                "meter-band-header": [
                    {
                        "band-id": "0",
                        "drop-rate": "30000"
                    }
                ]
            }
        }
    ]
}
  
 * */


public class Meter {
	
	@JsonProperty("flags")
	String flags;
	
	@JsonProperty("meter-id")
	String    meterId;
	
	@JsonProperty("meter-band-headers")
	MeterBandHeaders meterBanderHeaders;
	
	
	public Meter(String flags, int meterId, MeterBandHeaders meterBanderHeaders)
	{		
		this.flags = flags;
		this.meterId = String.valueOf(meterId);
		this.meterBanderHeaders = meterBanderHeaders;
	}	

}
