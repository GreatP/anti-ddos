package com.cetc.security.ddos.persistence;

/**
 * Created by zhangtao on 2015/10/12.
 */
public enum EventType {
    EVENT_ATTACKED((short)0),
    EVENT_START_DEFENSE((short)1),
    EVENT_STOP_DEFENSE((short)2);

    private short value;

    private EventType(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
