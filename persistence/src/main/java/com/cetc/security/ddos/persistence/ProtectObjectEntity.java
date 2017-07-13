package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by zhangtao on 2015/7/22.
 */

@Entity
@Table(name="protectobject")
public class ProtectObjectEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="iptype")
    private short ipType;
    @Column(name="network")
    private String netWork;
    @Column(name="clean_inport")
    private String cleanInport;
    @Column(name="clean_outport")
    private String cleanOutport;
    @Column(name="inport")
    private String inPort;
    @Column(name="outport")
    private String outPort;
    @Column(name="defensetype")
    private DefenseType defenseType;
    @Column(name="guideport")
    private String guidePort;
    @Column(name="reinjection_port")
    private String reinjectionPort;
    @Column(name="learn_status")
    private short learnStatus;
    //private List<Flow> flows;
    @Column(name="controller_id")
    private int controllerId = 0;
    @OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="cleandev_id")
    private CleanDevEntity cleanDevEntity;
    @Column(name="flag")
    private short flag;

    @Column(name="check_interval")
    private int checkInterval;
    @Column(name="tcp_syn")
    private int tcpSyn;
    @Column(name="tcp_synack")
    private int tcpSynAck;
    @Column(name="udp")
    private int udp;
    @Column(name="icmp")
    private int icmp;
    @Column(name="icmp_redirect")
    private boolean icmpRedirect;
    @Column(name="http")
    private int http;



    @Column(name="http_port")
    private int http_port;
    @Column(name="http_post")
    private int http_post;
    @Column(name="http_src_auth")
    private boolean httpSrcAuth;
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
    @Column(name="ip_option")
    private int ipOption;

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

    public short getIpType() {
        return ipType;
    }

    public void setIpType(short ipType) {
        this.ipType = ipType;
    }

    public String getNetWork() {
        return netWork;
    }

    public void setNetWork(String netWork) {
        this.netWork = netWork;
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

    public short getLearnStatus() {
        return learnStatus;
    }

    public void setLearnStatus(short learnStatus) {
        this.learnStatus = learnStatus;
    }

    /*public List<Flow> getFlows() {
        return flows;
    }

    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }*/


    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

    public DefenseType getDefenseType() {
        return defenseType;
    }

    public void setDefenseType(DefenseType defenseType) {
        this.defenseType = defenseType;
    }

    public String getGuidePort() {
        return guidePort;
    }

    public void setGuidePort(String guidePort) {
        this.guidePort = guidePort;
    }

    public String getReinjectionPort() {
        return reinjectionPort;
    }

    public void setReinjectionPort(String reinjectionPort) {
        this.reinjectionPort = reinjectionPort;
    }

    /*
    public Flow getFlowByFlowId(int flowId) {
        Flow ret = null;

        for (Flow f : flows) {
            if (f.getId() == flowId) {
                ret = f;
                break;
            }
        }

        return ret;
    }
    */

    public short getFlag() {
        return flag;
    }

    public void setFlag(short flag) {
        this.flag = flag;
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

    public CleanDevEntity getCleanDevEntity() {
        return cleanDevEntity;
    }

    public void setCleanDevEntity(CleanDevEntity cleanDevEntity) {
        this.cleanDevEntity = cleanDevEntity;
    }

    public int getTcpSyn() {
        return tcpSyn;
    }

    public void setTcpSyn(int tcpSyn) {
        this.tcpSyn = tcpSyn;
    }

    public int getTcpSynAck() {
        return tcpSynAck;
    }

    public void setTcpSynAck(int tcpSynAck) {
        this.tcpSynAck = tcpSynAck;
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

    public int getHttp_port() {
        return http_port;
    }

    public void setHttp_port(int http_port) {
        this.http_port = http_port;
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

    public int getDns_port() {
        return dns_port;
    }

    public void setDns_port(int dns_port) {
        this.dns_port = dns_port;
    }

    public int getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(int checkInterval) {
        this.checkInterval = checkInterval;
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

	public boolean isIcmpRedirect() {
		return icmpRedirect;
	}

	public void setIcmpRedirect(boolean icmpRedirect) {
		this.icmpRedirect = icmpRedirect;
	}

	public int getIpOption() {
		return ipOption;
	}

	public void setIpOption(int ipOption) {
		this.ipOption = ipOption;
	}

	public boolean isHttpSrcAuth() {
		return httpSrcAuth;
	}

	public void setHttpSrcAuth(boolean httpSrcAuth) {
		this.httpSrcAuth = httpSrcAuth;
	}
	
	
    
    
}
