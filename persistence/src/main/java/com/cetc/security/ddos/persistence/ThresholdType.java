package com.cetc.security.ddos.persistence;

/**
 * Created by lb on 2015/7/27.
 */
public enum ThresholdType {
    AUTO_LEARNING(0),
    FIXED_THRESHOLD(1);

    private int code;

    private ThresholdType(int code) {
        this.code = code;
    }
}
