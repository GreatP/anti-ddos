package com.cetc.security.ddos.meter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeterBandHeaders {

	@JsonProperty("meter-band-header")
	List<MeterBandHeader> lMeterBandHeader; 
}
