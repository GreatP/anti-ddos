package com.cetc.backend.view;

import javax.persistence.Column;

/**
 * Created by hasen on 2015/8/11.
 */
public class NetNodeForm {
    private int id;
    private String name;
    private String swId;
    private short swType;
    private int controllerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwId() {
        return swId;
    }

    public void setSwId(String swId) {
        this.swId = swId;
    }

    public short getSwType() {
        return swType;
    }

    public void setSwType(short swType) {
        this.swType = swType;
    }

    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }
}