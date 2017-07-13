package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by hanqsheng on 2016/4/27.
 */

@Entity
@Table(name="cleandev")
public class CleanDevEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="direct")
    private CleanDirection direct;
    @Column(name="check_interval")
    private int check_interval;
    @Column(name="tcp")
    private int tcp;
    @Column(name="tcp_first")
    private boolean tcpFirst;
    @Column(name="tcp_abnormal")
    private int tcp_abnormal;
    @Column(name="udp")
    private int udp;
    @Column(name="icmp")
    private int icmp;
    @Column(name="http")
    private int http;
    @Column(name="http_port")
    private int http_port;
    @Column(name="http_header")
    private int http_header;
    @Column(name="http_post")
    private int http_post;
    @Column(name="https")
    private int https;
    @Column(name="https_port")
    private int https_port;
    @Column(name="https_thc")
    private int https_thc;
    @Column(name="dns_request")
    private int dns_request;
    @Column(name="dns_reply")
    private int dns_reply;
    @Column(name="dns_abnormal")
    private int dns_abnormal;
    @Column(name="dns_port")
    private int dns_port;
    @Column(name="snmp")
    private int snmp;
    @Column(name="snmp_port")
    private int snmpPort;
    @Column(name="ntp")
    private int ntp;
    @Column(name="ntp_port")
    private int ntpPort;

    @Column(name="ip")
    private String ip;
    @Column(name="user")
    private String user;
    @Column(name="password")
    private String password;
    @Column(name="flow_timeout")
    private int flow_timeout = 60;
    @Column(name="flag")
    private short flag;


    public int getFlow_timeout() {
        return flow_timeout;
    }

    public void setFlow_timeout(int flow_timeout) {
        this.flow_timeout = flow_timeout;
    }

    public int getHttp() {
        return http;
    }

    public void setHttp(int http) {
        this.http = http;
    }

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

    public CleanDirection getDirect() {
        return direct;
    }

    public void setDirect(CleanDirection direct) {
        this.direct = direct;
    }

    public int getCheck_interval() {
        return check_interval;
    }

    public void setCheck_interval(int check_interval) {
        this.check_interval = check_interval;
    }

    public int getTcp() {
        return tcp;
    }


    public void setTcp(int tcp) {
        this.tcp = tcp;
    }

    public int getTcp_abnormal() {
        return tcp_abnormal;
    }

    public void setTcp_abnormal(int tcp_abnormal) {
        this.tcp_abnormal = tcp_abnormal;
    }

    public int getUdp() {
        return udp;
    }

    public void setUdp(int udp) {
        this.udp = udp;
    }

    public int getIcmp() {
        return icmp;
    }

    public void setIcmp(int icmp) {
        this.icmp = icmp;
    }

    public int getHttp_port() {
        return http_port;
    }

    public void setHttp_port(int http_port) {
        this.http_port = http_port;
    }

    public int getHttp_header() {
        return http_header;
    }

    public void setHttp_header(int http_header) {
        this.http_header = http_header;
    }

    public int getHttp_post() {
        return http_post;
    }

    public void setHttp_post(int http_post) {
        this.http_post = http_post;
    }

    public int getHttps() {
        return https;
    }

    public void setHttps(int https) {
        this.https = https;
    }

    public int getHttps_port() {
        return https_port;
    }

    public void setHttps_port(int https_port) {
        this.https_port = https_port;
    }

    public int getHttps_thc() {
        return https_thc;
    }

    public void setHttps_thc(int https_thc) {
        this.https_thc = https_thc;
    }

    public int getDns_request() {
        return dns_request;
    }

    public void setDns_request(int dns_request) {
        this.dns_request = dns_request;
    }

    public int getDns_reply() {
        return dns_reply;
    }

    public void setDns_reply(int dns_reply) {
        this.dns_reply = dns_reply;
    }

    public int getDns_abnormal() {
        return dns_abnormal;
    }

    public void setDns_abnormal(int dns_abnormal) {
        this.dns_abnormal = dns_abnormal;
    }

    public int getDns_port() {
        return dns_port;
    }

    public void setDns_port(int dns_port) {
        this.dns_port = dns_port;
    }

    public short getFlag() {
        return flag;
    }

    public void setFlag(short flag) {
        this.flag = flag;
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

    public int getSnmp() {
        return snmp;
    }

    public void setSnmp(int snmp) {
        this.snmp = snmp;
    }

    public int getSnmpPort() {
        return snmpPort;
    }

    public void setSnmpPort(int snmpPort) {
        this.snmpPort = snmpPort;
    }

    public int getNtp() {
        return ntp;
    }

    public void setNtp(int ntp) {
        this.ntp = ntp;
    }

    public int getNtpPort() {
        return ntpPort;
    }

    public void setNtpPort(int ntpPort) {
        this.ntpPort = ntpPort;
    }

	public boolean isTcpFirst() {
		return tcpFirst;
	}

	public void setTcpFirst(boolean tcpFirst) {
		this.tcpFirst = tcpFirst;
	}
    
    
}
