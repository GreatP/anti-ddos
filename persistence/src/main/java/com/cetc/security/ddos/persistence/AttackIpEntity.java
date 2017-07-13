package com.cetc.security.ddos.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lb on 2016/5/17.
 */
@Entity
@Table(name="attack_ip")
public class AttackIpEntity {
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO) //表示id自动产生，采用自加的方式
    @Column(name="id")
    private int id;
    @Column(name="ip")
    private String ip;
    @Column(name="handled")
    private int handled;

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

    public int getHandled() {
        return handled;
    }

    public void setHandled(int handled) {
        this.handled = handled;
    }
}
