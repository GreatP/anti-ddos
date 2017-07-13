package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.persistence.NetNodeEntity;

import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

public class NetNode {
	private static Logger logger = AntiLogger.getLogger(NetNode.class);
    private NetNodeEntity netNodeEntity;
    private List<ProtectObjectFlow> protectObjects;

    public NetNode(NetNodeEntity netNodeEntity) {
        this.netNodeEntity = netNodeEntity;
        this.protectObjects = new ArrayList<ProtectObjectFlow>();
    }

    public List<ProtectObjectFlow> getProtectObjects() {
        return protectObjects;
    }

    public void setProtectObjects(List<ProtectObjectFlow> protectObjects) {
        this.protectObjects = protectObjects;
    }

    public NetNodeEntity getNetNodeEntity() {
        return netNodeEntity;
    }

    public void setNetNodeEntity(NetNodeEntity netNodeEntity) {
        this.netNodeEntity = netNodeEntity;
    }

    public void addPOToNode(ProtectObjectFlow item) {
        protectObjects.add(item);
    }

    public void delPoFromNode(int id) throws Exception {
        for (ProtectObjectFlow p : protectObjects) {
            if (p.getPo().getId() == id) {
                delPOFromNode(p);
                break;
            }
        }
    }

    public void delPOFromNode(ProtectObjectFlow item) throws Exception
    {
        item.delPOFlows();
        protectObjects.remove(item);
    }

    public void delAllPO()
    {
        int i;
        for (i=0;i<protectObjects.size();i++)
        {
            try {
                delPOFromNode(protectObjects.get(i));
            } catch (Exception e) {
                logger.error("Delete protectObject:" + i + " fail");
            }
        }
    }
    
    public void printNetNodeinfo()
    {
    	logger.debug("======================");
    	logger.debug("netnode info: "+ "id:"+ netNodeEntity.getId()+"name:" + netNodeEntity.getName() +"swid:"+ netNodeEntity.getSwId());
    	logger.debug("po info:");
    	for (ProtectObjectFlow p : protectObjects) {
    		logger.debug("---------------------");
            logger.debug("id: "+p.getPo().getId());
            logger.debug("name: "+p.getPo().getName());
            logger.debug("network: "+p.getPo().getNetWork());  
        }
    }
}
