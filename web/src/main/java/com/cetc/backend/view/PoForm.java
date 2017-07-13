package com.cetc.backend.view;



/**
 * Created by zhangtao on 2015/8/6.
 */
public class PoForm {
    private int id;
    private String name;
    private String ipType; //need transform 0->IPV4 1->IPV6
    private String network;
    private String cleanInport;
    private String cleanOutport;
    private String inport;
    private String outport;
    private String reinjectionPort;//reinjection port
    private String defenseType; //need transform LIMIT_RATE, GUIDE_FLOW 
    private String guidePort;
    private String learn_status; //need transform int-->string 0->init 1->learning 2->active
    private int netnode_id;
    private  int controllerId;
    private int cleanDevId;
    private String cleandevIp;
    //private String netnode_swid;
    //private String netnode_name;
    //private String protocol;
    private int tcp;
    private int udp;
    private int icmp;
    private int any;
    private int tcp_syn;
    private int tcp_synack;
    private int udp_threshold;
    private int icmp_threshold;
    private boolean icmpRedirect;
    private boolean httpSrcAuth;
    private int http_request;
    private int http_port;
    private int https_request;
    private int https_port;
    private int https_thc;
    private int dns_request;
    private int dns_reply;
    private int dns_port;
    private int check_interval;

    private int ntp;
    private int ntp_port;
    private int snmp;
    private int snmp_port;

    private int ipOption;


    private int autoOrfix;

    /* 由于JS精度问题，这里用String */
    private String pps;
    private String kbps;
    private String username;//tenant name
    private int tenantId;//tenant id


    public boolean isIcmpRedirect() {
        return icmpRedirect;
    }

    public void setIcmpRedirect(boolean icmpRedirect) {
        this.icmpRedirect = icmpRedirect;
    }


    public boolean isHttpSrcAuth() {
        return httpSrcAuth;
    }

    public void setHttpSrcAuth(boolean httpSrcAuth) {
        this.httpSrcAuth = httpSrcAuth;
    }

    public int getIpOption() {
        return ipOption;
    }

    public void setIpOption(int ipOption) {
        this.ipOption = ipOption;
    }

    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
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

    public String getReinjectionPort() {
        return reinjectionPort;
    }

    public void setReinjectionPort(String reinjectionPort) {
        this.reinjectionPort = reinjectionPort;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDns_port() {
        return dns_port;
    }

    public void setDns_port(int dns_port) {
        this.dns_port = dns_port;
    }

    public int getCheck_interval() {
        return check_interval;
    }

    public void setCheck_interval(int check_interval) {
        this.check_interval = check_interval;
    }

    public int getTcp_syn() {
        return tcp_syn;
    }

    public void setTcp_syn(int tcp_syn) {
        this.tcp_syn = tcp_syn;
    }

    public int getTcp_synack() {
        return tcp_synack;
    }

    public void setTcp_synack(int tcp_synack) {
        this.tcp_synack = tcp_synack;
    }

    public int getUdp_threshold() {
        return udp_threshold;
    }

    public void setUdp_threshold(int udp_threshold) {
        this.udp_threshold = udp_threshold;
    }

    public int getIcmp_threshold() {
        return icmp_threshold;
    }

    public void setIcmp_threshold(int icmp_threshold) {
        this.icmp_threshold = icmp_threshold;
    }

    public int getHttp_request() {
        return http_request;
    }

    public void setHttp_request(int http_request) {
        this.http_request = http_request;
    }

    public int getHttp_port() {
        return http_port;
    }

    public void setHttp_port(int http_port) {
        this.http_port = http_port;
    }

    public int getHttps_request() {
        return https_request;
    }

    public void setHttps_request(int https_request) {
        this.https_request = https_request;
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

    public String getCleandevIp() {
        return cleandevIp;
    }

    public void setCleandevIp(String cleandevIp) {
        this.cleandevIp = cleandevIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /*public String getNetnode_name() {
        return netnode_name;
    }

    public void setNetnode_name(String netnode_name) {
        this.netnode_name = netnode_name;
    }
    */

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
/*
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
*/
    public String getInport() {
        return inport;
    }

    public void setInport(String inport) {
        this.inport = inport;
    }

    public String getOutport() {
        return outport;
    }

    public void setOutport(String outport) {
        this.outport = outport;
    }

    public int getNetnode_id() {
        return netnode_id;
    }

    public void setNetnode_id(int netnode_id) {
        this.netnode_id = netnode_id;
    }

    /*
    public String getNetnode_swid() {
        return netnode_swid;
    }

    public void setNetnode_swid(String netnode_swid) {
        this.netnode_swid = netnode_swid;
    }

*/
    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    public String getDefenseType() {
        return defenseType;
    }

    public void setDefenseType(String defenseType) {
        this.defenseType = defenseType;
    }

    public String getLearn_status() {
        return learn_status;
    }

    public void setLearn_status(String learn_status) {
        this.learn_status = learn_status;
    }

    public String getGuidePort() {
        return guidePort;
    }

    public void setGuidePort(String guidePort) {
        this.guidePort = guidePort;
    }

    public int getTcp() {
        return tcp;
    }

    public void setTcp(int tcp) {
        this.tcp = tcp;
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

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        this.any = any;
    }

    public int getAutoOrfix() {
        return autoOrfix;
    }

    public void setAutoOrfix(int autoOrfix) {
        this.autoOrfix = autoOrfix;
    }


    public String getPps() {
        return pps;
    }

    public void setPps(String pps) {
        this.pps = pps;
    }

    public String getKbps() {
        return kbps;
    }

    public void setKbps(String kbps) {
        this.kbps = kbps;
    }

    public String getCleanInport() {
        return cleanInport;
    }

    public void setCleanInport(String cleanInport) {
        this.cleanInport = cleanInport;
    }

    public String getCleanOutport() {
        return cleanOutport;
    }

    public void setCleanOutport(String cleanOutport) {
        this.cleanOutport = cleanOutport;
    }

    public int getCleanDevId() {
        return cleanDevId;
    }

    public void setCleanDevId(int cleanDevId) {
        this.cleanDevId = cleanDevId;
    }


}


