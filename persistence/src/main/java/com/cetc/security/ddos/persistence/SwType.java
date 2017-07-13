package com.cetc.security.ddos.persistence;

/**
 * Created by hasen on 2015/9/1.
 */
public enum SwType {

    SW_ODL((short)0),
    SW_OTHERS((short)1);

    private short value;

    private SwType(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
