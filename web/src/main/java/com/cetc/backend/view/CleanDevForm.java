package com.cetc.backend.view;

import javax.persistence.Column;

/**
 * Created by hasen on 2016/4/27.
 */
public class CleanDevForm {
    private int id;
    private String ip;
    private String direct;
    private boolean tcp_first;
    private int check_interval;
    private int tcp;
    private int tcp_abnormal;
    private int udp;
    private int icmp;
    private int http;
    private int http_port;
    private int http_header;
    private int http_post;
    private int https;
    private int https_port;
    private int https_thc;
    private int dns_request;
    private int dns_reply;
    private int dns_abnormal;
    private int dns_port;
    private int cleanDevId;

    private int ntp;
    private int ntp_port;
    private int snmp;
    private int snmp_port;

    public boolean isTcp_first() {
        return tcp_first;
    }

    public void setTcp_first(boolean tcp_first) {
        this.tcp_first = tcp_first;
    }

    public int getNtp() {
        return ntp;
    }

    public void setNtp(int ntp) {
        this.ntp = ntp;
    }

    public int getNtp_port() {
        return ntp_port;
    }

    public void setNtp_port(int ntp_port) {
        this.ntp_port = ntp_port;
    }

    public int getSnmp() {
        return snmp;
    }

    public void setSnmp(int snmp) {
        this.snmp = snmp;
    }

    public int getSnmp_port() {
        return snmp_port;
    }

    public void setSnmp_port(int snmp_port) {
        this.snmp_port = snmp_port;
    }

    public int getHttp_port() {
        return http_port;
    }

    public void setHttp_port(int http_port) {
        this.http_port = http_port;
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

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
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

    public int getHttp() {
        return http;
    }

    public void setHttp(int http) {
        this.http = http;
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

    public int getHttps_thc() {
        return https_thc;
    }

    public void setHttps_thc(int https_thc) {
        this.https_thc = https_thc;
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

    public int getCleanDevId() {
        return cleanDevId;
    }

    public void setCleanDevId(int cleanDevId) {
        this.cleanDevId = cleanDevId;
    }
}