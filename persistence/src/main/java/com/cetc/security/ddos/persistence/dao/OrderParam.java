package com.cetc.security.ddos.persistence.dao;

/**
 * Created by lb on 2015/8/14.
 */
public class OrderParam {
    private String key;
    private String value;

    public OrderParam(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
