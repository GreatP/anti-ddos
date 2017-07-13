package com.cetc.security.ddos.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import com.cetc.security.ddos.persistence.FlowEntity;
import com.cetc.security.ddos.persistence.NetNodeEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;


import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.web.client.RestClientException;

public class ProtectObjectFlow {
	private static Logger logger = AntiLogger.getLogger(ProtectObjectFlow.class);
    private ProtectObjectEntity po;
    private List<FlowEntity> flows;
    private List<FlowConfig> AllFlows = new ArrayList<FlowConfig>();
    private Controller control;
    private NetNodeEntity netNodeEntity;
    private Map<Integer, FlowConfig> flowInfoes;

	public ProtectObjectFlow(Controller control, NetNodeEntity netNodeEntity, ProtectObjectEntity po, List<FlowEntity> flows)
	{
        this.control = control;
        this.netNodeEntity = netNodeEntity;
        this.po = po;
        this.flows = flows;
        this.flowInfoes = new HashMap<Integer, FlowConfig>();
	}

    public Controller getControl() {
        return control;
    }

    public void setControl(Controller control) {
        this.control = control;
    }

    public NetNodeEntity getNetNodeEntity() {
        return netNodeEntity;
    }

    public void setNetNodeEntity(NetNodeEntity netNodeEntity) {
        this.netNodeEntity = netNodeEntity;
    }

    public Map<Integer, FlowConfig> getFlowInfoes() {
        return flowInfoes;
    }

    public void setFlowInfoes(Map<Integer, FlowConfig> flowInfoes) {
        this.flowInfoes = flowInfoes;
    }
    

    public void setFlows(List<FlowEntity> flows) {
		this.flows = flows;
	}

	public List<FlowConfig> getAllFlows() {
		return AllFlows;
	}

	public void setAllFlows(List<FlowConfig> allFlows) {
		AllFlows = allFlows;
	}

	public ProtectObjectEntity getPo() {
        return po;
    }

    public void setPo(ProtectObjectEntity po) {
        this.po = po;
    }

    public List<FlowEntity> getFlows() {
        return flows;
    }


    private FlowConfig setFlowConfig(ProtectObjectEntity po, FlowEntity f) {
        String flowName = "anti-ddos-";
        int id = f.getId();

        FlowConfig oneFlow = new FlowConfig();
		/*base info*/
        oneFlow.setFlowId(id);  //check here
        oneFlow.setNodeSwId(netNodeEntity.getSwId());
        oneFlow.setPriority(f.getPriority());
        oneFlow.setIdleTimeout(GlobalId.idleTimeout);
        oneFlow.setHardTimeout(GlobalId.hardTimeout);
        oneFlow.setTableId(GlobalId.tableId);
        oneFlow.setFlowName(flowName+Integer.toString(id));

		/*match info*/
        oneFlow.setIpv4Destination(po.getNetWork());
        if (!(po.getInPort() == null || po.getInPort().equals("")))
        {
        	oneFlow.setInputNode(po.getInPort());
        }
        oneFlow.setEthernetType(f.getEthType());
        oneFlow.setIpProtocol(f.getProtocol());
        if (f.getL4() != 0)
        {
        	oneFlow.setPort(f.getL4());
        }

		/*instruction info*/
        oneFlow.setActionOrder(1);
        oneFlow.setInstrOrder(1);
        oneFlow.setOutputNode(po.getOutPort());
        oneFlow.setMaxLength(GlobalId.maxLength);

		/*statistic info*/
        oneFlow.setPacketCount(0);
        oneFlow.setByteCount(0);

        return oneFlow;
    }
	
	private void createOneFlow(int flowId, ProtectObjectEntity po, FlowEntity f) throws Exception
	{
		FlowConfig oneFlow =setFlowConfig(po,  f);
		/*
		FlowConfig flowTmp = null;
		int flag = 0;
		
		if (!AllFlows.isEmpty())
		{
			for (FlowConfig fc:AllFlows)
			{
				if (fc.getIpProtocol() == oneFlow.getIpProtocol() && 
						(fc.getIpv4Destination().equals(oneFlow.getIpv4Destination())))
				{
					flag = 1;
					flowTmp = fc;
					break;
				}
			}
			if(flag == 1)
			{
				if (flowTmp.getFlowId() != oneFlow.getFlowId())
				{
					flowTmp.setNodeSwId(oneFlow.getNodeSwId());
					flowTmp.setTableId(oneFlow.getTableId());	
					control.delFlowInfo(flowTmp);
				}
			}
		}*/
		
		control.putFlowInfo(oneFlow);
        flowInfoes.put(flowId, oneFlow);
        
        logger.info("add flow " + oneFlow.getFlowName() + " success" + ":" +"ip(" + oneFlow.getIpv4Destination()+")," + "protocol(" +oneFlow.getIpProtocol() + ")");
	}

	/**** need not to delete one flow by the user
    private void delOneFlow(Flow f) {
        FlowConfig oneFlow; 
        
        oneFlow = flowInfoes.get(f.getId());
        
        if (oneFlow != null)
        {
        	control.delFlowInfo(oneFlow);
        	flowInfoes.remove(f.getId());
        }
    
    }
    */

    public void updatePOFlows(FlowConfig flowConfig) throws Exception {
        control.putFlowInfo(flowConfig);
    }
    
    public void getAllFlowsBeforeCreate() throws Exception
    {
    	control.getAllFlowInfo(AllFlows, netNodeEntity.getSwId(), 0);
    }

    public void createPOFlows() throws Exception
    {
        if (flows == null)
        {
        	logger.warn("the flow policy is null, please set the policy first!");
            return;
        }
        
        /*先查询，再下发，如发现flow id不一致的相同流，则先将其删除，再下发*/
       // getAllFlowsBeforeCreate();

        List<FlowEntity> flowEntities = flows;
        try {
            for (FlowEntity f : flowEntities) {
                createOneFlow(f.getId(), po, f);
            }
        } catch (RestClientException e) {
            logger.error("Create protect object:" + po.getName() + " flow fail:" + e.getMessage());
            delPOFlows();
            throw e;
        }
    }
	
	public void delPOFlows()
	{
		if (flowInfoes == null)
		{
			logger.warn("delPOFlows: flow array is not init!");
			return;
		}

        Iterator<Map.Entry<Integer, FlowConfig>> it = flowInfoes.entrySet().iterator();

        for (Map.Entry<Integer, FlowConfig> entry : flowInfoes.entrySet()) {
       // while (it.hasNext()) {
            //Map.Entry<Integer, FlowConfig> entry = it.next();
            try {
                control.delFlowInfo(entry.getValue());
            } catch (Exception e) {
                logger.error("Delete flow:" + entry.getValue().getFlowId() + " fail");
            }
        }

        flowInfoes.clear();
	}

    public FlowConfig getFlowByFlowId(int flowId) {
        return flowInfoes.get(flowId);
    }
    
    public void printPOinfo()
    {
    	logger.debug("======================");
    	logger.debug("ProtectObject info: "+ "id:"+po.getId()+"name:" +po.getName() +"network:"+po.getNetWork());
    	logger.debug("flow info:");
    	//Iterator<Map.Entry<Integer, FlowConfig>> it = flowInfoes.entrySet().iterator();

        for (Map.Entry<Integer, FlowConfig> entry : flowInfoes.entrySet()) {
        //while (it.hasNext()) {
            //Map.Entry<Integer, FlowConfig> entry = it.next();
            logger.debug("------------------------------");
            logger.debug("flow id:"+entry.getValue().getFlowId());
            logger.debug("flow name:"+entry.getValue().getFlowName());
            logger.debug("flow network:"+entry.getValue().getIpv4Destination());
            logger.debug("flow protocal:"+entry.getValue().getIpProtocol());
            logger.debug("flow port:"+entry.getValue().getPort());
        }
    	
    }
}
