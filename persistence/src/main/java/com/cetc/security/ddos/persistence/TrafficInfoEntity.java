package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by cw on 2016/5/11.
 */
@Entity
@Table(name="traffic_info")
public class TrafficInfoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="type")
    private int type;
    @Column(name="time")
    private long time;
    @Column(name="pps_tcp")
    private long pps_tcp;
    @Column(name="pps_udp")
    private long pps_udp;
    @Column(name="pps_icmp")
    private long pps_icmp;  
    @Column(name="pps_other")
    private long pps_other;
    @Column(name="pps_all")
    private long pps_all;
    @Column(name="bps_tcp")
    private long bps_tcp;
    @Column(name="bps_udp")
    private long bps_udp;
    @Column(name="bps_icmp")
    private long bps_icmp;
    @Column(name="bps_other")
    private long bps_other;
    @Column(name="bps_all")
    private long bps_all;
    @Column(name="output_pps")
    private long output_pps;
    @Column(name="output_bps")
    private long output_bps;
    @Column(name="po_id")
    private int po_id;
    @Column(name="attack_bps")
    private long attack_bps;
    @Column(name="attack_pps")
    private long attack_pps;
    
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getPps_tcp() {
		return pps_tcp;
	}
	public void setPps_tcp(long pps_tcp) {
		this.pps_tcp = pps_tcp;
	}
	public long getPps_udp() {
		return pps_udp;
	}
	public void setPps_udp(long pps_udp) {
		this.pps_udp = pps_udp;
	}
	public long getPps_icmp() {
		return pps_icmp;
	}
	public void setPps_icmp(long pps_icmp) {
		this.pps_icmp = pps_icmp;
	}
	public long getPps_other() {
		return pps_other;
	}
	public void setPps_other(long pps_other) {
		this.pps_other = pps_other;
	}
	public long getPps_all() {
		return pps_all;
	}
	public void setPps_all(long pps_all) {
		this.pps_all = pps_all;
	}
	public long getBps_tcp() {
		return bps_tcp;
	}
	public void setBps_tcp(long bps_tcp) {
		this.bps_tcp = bps_tcp;
	}
	public long getBps_udp() {
		return bps_udp;
	}
	public void setBps_udp(long bps_udp) {
		this.bps_udp = bps_udp;
	}
	public long getBps_icmp() {
		return bps_icmp;
	}
	public void setBps_icmp(long bps_icmp) {
		this.bps_icmp = bps_icmp;
	}
	public long getBps_other() {
		return bps_other;
	}
	public void setBps_other(long bps_other) {
		this.bps_other = bps_other;
	}
	public long getBps_all() {
		return bps_all;
	}
	public void setBps_all(long bps_all) {
		this.bps_all = bps_all;
	}
	public long getOutput_pps() {
		return output_pps;
	}
	public void setOutput_pps(long output_pps) {
		this.output_pps = output_pps;
	}
	public long getOutput_bps() {
		return output_bps;
	}
	public void setOutput_bps(long output_bps) {
		this.output_bps = output_bps;
	}
	public int getPo_id() {
		return po_id;
	}
	public void setPo_id(int po_id) {
		this.po_id = po_id;
	}
	public long getAttack_bps() {
		return attack_bps;
	}
	public void setAttack_bps(long attack_bps) {
		this.attack_bps = attack_bps;
	}
	public long getAttack_pps() {
		return attack_pps;
	}
	public void setAttack_pps(long attack_pps) {
		this.attack_pps = attack_pps;
	}
   
}
