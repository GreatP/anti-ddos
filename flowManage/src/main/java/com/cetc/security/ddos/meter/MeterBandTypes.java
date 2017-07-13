package com.cetc.security.ddos.meter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeterBandTypes {

    @JsonProperty("flags")
    String flags;

    public MeterBandTypes()
    {
        this.flags = "ofpmbt-drop";
    }

}
