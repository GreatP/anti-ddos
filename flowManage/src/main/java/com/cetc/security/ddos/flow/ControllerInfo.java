package com.cetc.security.ddos.flow;

import java.util.Iterator;
import java.util.Map;

import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.persistence.DDoSParamEntity;

/**
 * Created by zhangtao on 2015/7/23.
 */
public class ControllerInfo {
    private int id;
    DDoSParamEntity dDoSParam;
    private Controller controller;
    private NetNodes netNodes;

    public ControllerInfo(int id, Controller controller, DDoSParamEntity dDoSParam) {
        this.id = id;
        this.controller = controller;
        this.dDoSParam = dDoSParam;
        netNodes = new NetNodes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public NetNodes getNetNodes() {
        return netNodes;
    }

    public void setNetNodes(NetNodes netNodes) {
        this.netNodes = netNodes;
    }

    public void addNetNode(NetNode netNode) {
        netNodes.addNode(netNode);
    }

    public NetNode getNetNode(int netNodeId) {
        return netNodes.getNetNodes().get(netNodeId);
    }

    public DDoSParamEntity getdDoSParam() {
        return dDoSParam;
    }

    public void setdDoSParam(DDoSParamEntity dDoSParam) {
        this.dDoSParam = dDoSParam;
    }

    public NetNode delNetNode(int netNodeId) {
       return netNodes.delNode(netNodeId);
    }

    public void delAllNetNode() {
        //Iterator<Map.Entry<Integer, NetNode>> it = netNodes.getNetNodes().entrySet().iterator();

        for (Map.Entry<Integer, NetNode> entry : netNodes.getNetNodes().entrySet()) {
        //while (it.hasNext()) {
            //Map.Entry<Integer, NetNode> entry = it.next();
            entry.getValue().delAllPO();
        }

        netNodes.getNetNodes().clear();
    }

}
