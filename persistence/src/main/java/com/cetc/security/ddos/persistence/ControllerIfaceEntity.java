package com.cetc.security.ddos.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="controller_iface")
public class ControllerIfaceEntity {
	@Id
    //@GeneratedValue(strategy= GenerationType.AUTO) //表示id自动产生，采用自加的方式
    @Column(name="id")
    private int id;
	@Column(name="inport")
    private String inPort;
	@Column(name="outport")
    private String outPort;
	@Column(name="controller_id")
    private int controllerId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInPort() {
		return inPort;
	}
	public void setInPort(String inPort) {
		this.inPort = inPort;
	}
	public String getOutPort() {
		return outPort;
	}
	public void setOutPort(String outPort) {
		this.outPort = outPort;
	}
	public int getControllerId() {
		return controllerId;
	}
	public void setControllerId(int controllerId) {
		this.controllerId = controllerId;
	}
	
	
}
