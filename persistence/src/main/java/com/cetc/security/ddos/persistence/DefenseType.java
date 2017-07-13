package com.cetc.security.ddos.persistence;

/**
 * Created by zhangtao on 2015/7/30.
 */
public enum DefenseType {
    LIMIT_RATE((short)0),
    GUIDE_FLOW((short)1);

    private short value;

    private DefenseType(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
