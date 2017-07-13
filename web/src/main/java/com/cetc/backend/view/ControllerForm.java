package com.cetc.backend.view;

/**
 * Created by zhangtao on 2015/8/6.
 */
public class ControllerForm {
    private int id;
    private String ip;
    private int port;
    private String inport;
    private String outport;
    private String user;
    private String password;
    private short type;
    private int detectionInterval;
    private int detectionDeviationPercentage;
    private int attackSuspicionsThreshold;
    private int recoverNormalThreshold;

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

    public int getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public int getDetectionDeviationPercentage() {
        return detectionDeviationPercentage;
    }

    public void setDetectionDeviationPercentage(int detectionDeviationPercentage) {
        this.detectionDeviationPercentage = detectionDeviationPercentage;
    }

    public int getAttackSuspicionsThreshold() {
        return attackSuspicionsThreshold;
    }

    public void setAttackSuspicionsThreshold(int attackSuspicionsThreshold) {
        this.attackSuspicionsThreshold = attackSuspicionsThreshold;
    }

    public int getRecoverNormalThreshold() {
        return recoverNormalThreshold;
    }

    public void setRecoverNormalThreshold(int recoverNormalThreshold) {
        this.recoverNormalThreshold = recoverNormalThreshold;
    }

    public int getDetectionInterval() {
        return detectionInterval;
    }

    public void setDetectionInterval(int detectionInterval) {
        this.detectionInterval = detectionInterval;
    }
}
