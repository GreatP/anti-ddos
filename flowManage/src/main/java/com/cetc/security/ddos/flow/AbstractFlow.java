package com.cetc.security.ddos.flow;

import com.cetc.security.ddos.common.utils.AntiLogger;
import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import com.cetc.security.ddos.persistence.FlowEntity;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2016/8/9.
 */
public abstract class AbstractFlow {
    static Logger logger = AntiLogger.getLogger(AbstractFlow.class);
    private Controller controller;
    private Map<Integer, FlowConfig> flowConfigMap;

    public AbstractFlow(Controller controller) {
        this.controller = controller;
        flowConfigMap = new HashMap<Integer, FlowConfig>();
    }




    private FlowConfig setFlowConfig(ProtectObjectEntity po, FlowEntity f, String path, int tableId, String srcIp, String dstIp, String inport, String outport, String gotoTable) {
        String flowName = "anti-ddos-";
        int id = f.getId();

        FlowConfig oneFlow = new FlowConfig();
		/*base info*/
        oneFlow.setPath(path);
        oneFlow.setFlowId(id);  //check here
        oneFlow.setNodeSwId("br0");
        oneFlow.setPriority(f.getPriority());
        oneFlow.setIdleTimeout(GlobalId.idleTimeout);
        oneFlow.setHardTimeout(GlobalId.hardTimeout);
        oneFlow.setTableId(tableId);
        oneFlow.setFlowName(flowName+Integer.toString(id));

		/*match info*/
        oneFlow.setIpv4Source(srcIp);
        oneFlow.setIpv4Destination(dstIp);
        if (!(inport == null || inport.equals("")))
        {
            oneFlow.setInputNode(inport);
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
        oneFlow.setOutputNode(outport);
        oneFlow.setGotoTable(gotoTable);
        oneFlow.setMaxLength(GlobalId.maxLength);

		/*statistic info*/
        oneFlow.setPacketCount(0);
        oneFlow.setByteCount(0);

        return oneFlow;
    }

    protected void createOneFlow(int flowId, ProtectObjectEntity po, FlowEntity f, String path, int tableId, String srcIp, String dstIp, String inport, String outport, String gotoTable) throws Exception
    {
        FlowConfig oneFlow =setFlowConfig(po,  f, path, tableId, srcIp, dstIp, inport, outport, gotoTable);

        controller.putFlowInfo(oneFlow);
        flowConfigMap.put(flowId, oneFlow);

        logger.debug("Clean device:add flow " + oneFlow.getFlowName() + " success" + ":" +"ip(" + oneFlow.getIpv4Destination()+")," + "protocol(" +oneFlow.getIpProtocol() + ")");
    }


    abstract protected void createPOFlows(ProtectObjectEntity po, List<FlowEntity> flowEntities) throws Exception;

    protected void delPOFlows()
    {
        if (flowConfigMap == null)
        {
            logger.warn("delPOFlows: flow array is not init!");
            return;
        }

        //Iterator<Map.Entry<Integer, FlowConfig>> it = flowConfigMap.entrySet().iterator();

        for (Map.Entry<Integer, FlowConfig> entry : flowConfigMap.entrySet()) {
            // while (it.hasNext()) {
            //Map.Entry<Integer, FlowConfig> entry = it.next();
            try {
                controller.delFlowInfo(entry.getValue());
            } catch (Exception e) {
                logger.error("Delete flow:" + entry.getValue().getFlowId() + " fail");
            }
        }

        flowConfigMap.clear();
    }
}
