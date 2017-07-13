package com.cetc.security.ddos.persistence;

/**
 * Created by hanqsheng on 2016/5/3.
 */
public enum CleanDirection {
    UNI_DIRECTION((short)0),
    BI_DIRECTION((short)1);

    private short value;

    public short getValue() {
        return value;
    }

    private CleanDirection(short value) {
        this.value = value;
    }
}
