package com.cetc.security.ddos.persistence;

import javax.persistence.*;

import com.cetc.security.ddos.common.type.ControllerType;

/**
 * Created by zhangtao on 2015/7/23.
 */
@Entity
@Table(name="controller")
public class ControllerEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="ip")
    private String ip;
    @Column(name="port")
    private int port;
    @Column(name="user")
    private String user;
    @Column(name="password")
    private String password;
    @Column(name="type")
    private ControllerType type;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ControllerType getType() {
        return type;
    }

    public void setType(ControllerType type) {
        this.type = type;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getFlag() {
        return flag;
    }

    public void setFlag(short flag) {
        this.flag = flag;
    }

}
