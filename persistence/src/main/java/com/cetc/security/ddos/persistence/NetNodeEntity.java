package com.cetc.security.ddos.persistence;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="netnode")
public class NetNodeEntity
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
	private String name;
    @Column(name="switch_id")
    private String swId;
    @Column(name="type")
    private short swType;
    @Column(name="controller_id")
    private int controllerId;
    @Column(name="status")
	private short status;
    @Column(name="flag")
    private short flag;

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

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

    public short getFlag() {
        return flag;
    }

    public void setFlag(short flag) {
        this.flag = flag;
    }
}
