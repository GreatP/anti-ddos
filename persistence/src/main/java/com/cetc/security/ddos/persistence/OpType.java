package com.cetc.security.ddos.persistence;

/**
 * Created by zhangtao on 2015/8/27.
 */
public enum OpType {
    OP_ADD((short)0),
    OP_EDIT((short)1),
    OP_NORMAL((short)2),
    OP_DEL((short)3);

    private short value;

    private OpType(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
