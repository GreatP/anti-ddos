package com.cetc.backend.controller;

import com.cetc.backend.view.IdNetworkForm;
import com.cetc.backend.view.PONameForm;
import com.cetc.backend.view.PoForm;
import com.cetc.security.ddos.persistence.*;
import com.cetc.security.ddos.persistence.service.CleanDevService;
import com.cetc.security.ddos.persistence.service.NetNodeService;
import com.cetc.security.ddos.persistence.service.POService;

import com.cetc.security.ddos.persistence.service.Role;
import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangtao on 2015/8/5.
 */
@Controller
@RequestMapping("/policy")
public class POController extends BaseController {
	private static Logger logger = AntiLogger.getLogger(POController.class);
    @Autowired
    private POService poService;
    @Autowired
    private NetNodeService netnodeService;
    @Autowired
    private CleanDevService cleanDevService;

    
    protected void setPoProperties(PoForm poForm,
            ProtectObjectEntity poEntity) 
    {	
    	poEntity.setName(poForm.getName());
    	
    	if (poForm.getIpType() == null)
    	{
    		poEntity.setIpType((short)0);
    	}
    	else
    	{
    		if (poForm.getIpType().equals("IPV6"))
        	{
        		poEntity.setIpType((short)1);
        	}
        	else
        	{
        		poEntity.setIpType((short)0);
        	}
    	}
    	
    	poEntity.setLearnStatus((short)0);
    	
    	poEntity.setNetWork(poForm.getNetwork());

        CleanDevEntity c = new CleanDevEntity();
        c.setId(poForm.getCleanDevId());
        poEntity.setCleanDevEntity(c);
        poEntity.setCleanInport(poForm.getCleanInport());
        poEntity.setCleanOutport(poForm.getCleanOutport());
        /**********************set the threshold*********************************/
        poEntity.setReinjectionPort(poForm.getReinjectionPort());
        poEntity.setCheckInterval(poForm.getCheck_interval());
        poEntity.setIcmp(poForm.getIcmp_threshold());
        poEntity.setIcmpRedirect(poForm.isIcmpRedirect());
        poEntity.setHttpSrcAuth(poForm.isHttpSrcAuth());
        poEntity.setUdp(poForm.getUdp_threshold());
        poEntity.setTcpSyn(poForm.getTcp_syn());
        poEntity.setTcpSynAck(poForm.getTcp_synack());
        poEntity.setHttp(poForm.getHttp_request());
        poEntity.setHttp_port(poForm.getHttp_port());
        poEntity.setHttps(poForm.getHttps_request());
        poEntity.setHttps_port(poForm.getHttps_port());
        poEntity.setDns_request(poForm.getDns_request());
        poEntity.setDns_reply(poForm.getDns_reply());
        poEntity.setDns_port(poForm.getDns_port());

        poEntity.setNtp(poForm.getNtp());
        poEntity.setNtpPort(poForm.getNtp_port());
        poEntity.setSnmp(poForm.getSnmp());
        poEntity.setSnmpPort(poForm.getSnmp_port());
        poEntity.setIpOption(poForm.getIpOption());

        /*************************************************************************/

    	poEntity.setInPort(poForm.getInport());
    	
    	poEntity.setOutPort(poForm.getOutport());
    	
    	if (poForm.getDefenseType().equals("LIMIT_RATE"))
    	{
    		poEntity.setDefenseType(DefenseType.LIMIT_RATE);
    	}
    	else
    	{
    		poEntity.setDefenseType(DefenseType.GUIDE_FLOW);
    	}
    	
    	poEntity.setGuidePort(poForm.getGuidePort());
    	
    	//poEntity.setNetNodeId(netnodeService.getNetNodeByName(poForm.getNetnode_name()).getId());
    	poEntity.setControllerId(poForm.getControllerId());
		
	}
    
    protected void setPoFlowProperties(PoForm poForm,FlowEntity flowEntity, short protocol)
    {
    	if (protocol == 0)
    	{
    		flowEntity.setPriority((short) 499);
    	}
    	else
    	{
    		flowEntity.setPriority((short) 500);
    	}
    	
    	flowEntity.setEthType((short) 2048);
    	flowEntity.setProtocol(protocol);
    	if (poForm.getAutoOrfix() == 0)
    	{
    		flowEntity.setThresholdType(ThresholdType.AUTO_LEARNING);
    	}
    	else
    	{
    		flowEntity.setThresholdType(ThresholdType.FIXED_THRESHOLD);
    	}

        if ((poForm.getPps() != null) && (poForm.getPps().length() > 0)) {
            flowEntity.setThresholdPps(Long.valueOf(poForm.getPps()));
        }
        if ((poForm.getKbps() != null)  && (poForm.getKbps().length() > 0)) {
            flowEntity.setThresholdKBps(Long.valueOf(poForm.getKbps()));
        }
	}
    
    protected void setPoForm(ProtectObjectEntity p, PoForm pf, List<FlowEntity> flowList)
    {
    	pf.setId(p.getId());
    	pf.setName(p.getName());
    	if (p.getIpType() == 0)
    	{
    		pf.setIpType("IPV4");
    	}
    	else
    	{
    		pf.setIpType("IPV6");
    	}
    	
    	pf.setNetwork(p.getNetWork());

        pf.setCleanDevId(p.getCleanDevEntity().getId());
        pf.setCleandevIp(cleanDevService.getCleanDev(pf.getCleanDevId()).getIp());
        pf.setCleanOutport(p.getCleanOutport());
        pf.setCleanInport(p.getCleanInport());
        pf.setInport(p.getInPort());
    	pf.setOutport(p.getOutPort());
        pf.setReinjectionPort(p.getReinjectionPort());
        pf.setIcmpRedirect(p.isIcmpRedirect());
        pf.setHttpSrcAuth(p.isHttpSrcAuth());
    	
    	if (p.getDefenseType() == DefenseType.LIMIT_RATE)
    	{
    		pf.setDefenseType("LIMIT_RATE");
    	}
    	else
    	{
    		pf.setDefenseType("GUIDE_FLOW");
    	}
    	pf.setGuidePort(p.getGuidePort());
    	
    	//set learnstatus
    	pf.setLearn_status(b2fLeanStatus(p.getLearnStatus()));

    	//pf.setNetnode_swid(netnodeService.getNetNode(p.getNetNodeId()).getSwId());
        /*
        NetNodeEntity netNodeEntity =  netnodeService.getNetNode(p.getNetNodeId());
        if (netNodeEntity != null) {
            pf.setNetnode_name(netNodeEntity.getName());
        }
        */

        pf.setControllerId(p.getControllerId());
        pf.setCleanDevId(p.getCleanDevEntity().getId());
    	
    	String proto = " ";
    	long kbps = 0;
    	long pps = 0;
    	ThresholdType autoOrfix = ThresholdType.AUTO_LEARNING;
    	if(flowList != null)
    	{
    		for (FlowEntity f:flowList)
        	{ 
        		if (f.getProtocol() == 0)
        		{
        			proto += "OTHER ";
        			pf.setAny(1);
        		}
        		else if (f.getProtocol() == 1)
        		{
        			proto += "ICMP ";
        			pf.setIcmp(1);
        		}
        		else if (f.getProtocol() == 6)
        		{
        			proto += "TCP ";
        			pf.setTcp(1);
        		}
        		else if (f.getProtocol() == 17)
        		{
        			proto += "UDP ";
        			pf.setUdp(1);
        		}
        		
        		pps = f.getThresholdPps();
        		kbps = f.getThresholdKBps();
        		autoOrfix = f.getThresholdType();
        	}
    	}	
    	//pf.setProtocol(proto);
    	
    	pf.setPps(String.valueOf(pps));
    	pf.setKbps(String.valueOf(kbps));
    	
    	if (autoOrfix == ThresholdType.AUTO_LEARNING)
    	{
    		pf.setAutoOrfix(0);
    	}
    	else
    	{
    		pf.setAutoOrfix(1);
    	}

        /**********************set the threshold*********************************/
        pf.setIcmp_threshold(p.getIcmp());
        pf.setTcp_syn(p.getTcpSyn());
        pf.setTcp_synack(p.getTcpSynAck());
        pf.setUdp_threshold(p.getUdp());
        pf.setCheck_interval(p.getCheckInterval());
        pf.setHttp_request(p.getHttp());
        pf.setHttp_port(p.getHttp_port());
        pf.setHttps_request(p.getHttps());
        pf.setHttps_port(p.getHttps_port());
        pf.setDns_request(p.getDns_request());
        pf.setDns_reply(p.getDns_reply());
        pf.setDns_port(p.getDns_port());

        pf.setNtp(p.getNtp());
        pf.setNtp_port(p.getNtpPort());
        pf.setSnmp(p.getSnmp());
        pf.setSnmp_port(p.getSnmpPort());

        pf.setIpOption(p.getIpOption());
        /************************************************************************/
    	
    }
    
    protected void setPoFlows(PoForm poForm, List<FlowEntity> poFlows)
    {
    	if (poForm.getTcp() == 1)
        {
        	FlowEntity flowEntity = new FlowEntity();
        	setPoFlowProperties(poForm, flowEntity, (short)6);
        	poFlows.add(flowEntity);	
        }
        
        if (poForm.getUdp() == 1)
        {
        	FlowEntity flowEntity = new FlowEntity();
        	setPoFlowProperties(poForm, flowEntity, (short)17);
        	poFlows.add(flowEntity);	
        }
        
        if (poForm.getIcmp() == 1)
        {
        	FlowEntity flowEntity = new FlowEntity();
        	setPoFlowProperties(poForm, flowEntity, (short)1);
        	poFlows.add(flowEntity);	
        }
        
        if (poForm.getAny() == 1)
        {
        	FlowEntity flowEntity = new FlowEntity();
        	setPoFlowProperties(poForm, flowEntity, (short)0);
        	poFlows.add(flowEntity);	
        }
    	
    }
    
    protected short f2bLeanStatus(String learn)
    {
    	short a = 0;
    	if (learn.equals("INIT"))
    	{
    		a = 0;
    	}
    	else if (learn.equals("LEARNING"))
    	{
    		a =1;
    	}
    	else if(learn.equals("ACTIVE"))
    	{
    		a=2;
    	}
    	
    	return a;
    	
    }
    
    protected String b2fLeanStatus(short a)
    {
    	String learn = "INIT";
    	if (a == 0)
    	{
    		learn = "INIT";
    	}
    	else if (a == 1)
    	{
    		learn = "LEARNING";
    	}
    	else if (a == 2)
    	{
    		learn = "ACTIVE";
    	}
    	
    	return learn;
    	
    }
    
    private class PoListForm {
        private long total;
        List<PoForm> pos;

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<PoForm> getPos() {
            return pos;
        }

        public void setPos(List<PoForm> pos) {
            this.pos = pos;
        }
    }
    
    @RequestMapping("getPO")
    @ResponseBody
    public String getPO() {
        return toJson(poService.getAllPO());
    }
    
    @ResponseBody
    @RequestMapping(value = "po", method = RequestMethod.GET)
    public String list(/*HttpServletRequest request, */ @RequestParam(value = "page", required = false) String page) {
        int start = getPageStart(page);
        int userId = this.getCurrentUser().getId();
        List<ProtectObjectEntity> list = null;

        if (this.getCurrentUser().getRole().equals(Role.TENANT)) {
            list = poService.getPoByUserId(userId, start, DEFAULT_PAGE_LIMIT);
        } else {
            list = poService.getProtectObject(start, DEFAULT_PAGE_LIMIT);
        }

        List<PoForm> resultList = new ArrayList<PoForm>();
        List<FlowEntity> flowList;
        for (ProtectObjectEntity p : list) {
        	PoForm pf = new PoForm();
            UserEntity userEntity = poService.getUserById(p.getId());
            if (userEntity != null) {
                pf.setUsername(userEntity.getUsername());
            } else {
                pf.setUsername("--");
            }
        	flowList = poService.getFlowByPoId(p.getId());
            setPoForm(p, pf, flowList);
            resultList.add(pf);
        }
        
        PoListForm poListForm = new PoListForm();
        poListForm.setPos(resultList);
        poListForm.setTotal(poService.countAllPo());

        return toJson(poListForm);
    }

    @ResponseBody
    @RequestMapping(value = "getAllPo", method = RequestMethod.GET)
    public String getAllPo() {
        int userId = this.getCurrentUser().getId();
        List<ProtectObjectEntity> list = null;
        if (this.getCurrentUser().getRole().equals(Role.TENANT)) {
            list = poService.getPoByUserId(userId);
        } else {
            list = poService.getProtectObject();
        }

        if (list == null) {
            return toJson(list);
        }

        List<PoForm> resultList = new ArrayList<PoForm>();
        List<FlowEntity> flowList;
        for (ProtectObjectEntity p : list) {
            PoForm pf = new PoForm();
            flowList = poService.getFlowByPoId(p.getId());
            setPoForm(p, pf, flowList);
            resultList.add(pf);
        }

        return toJson(resultList);
    }
    
    @ResponseBody
    @RequestMapping(value="po/{id:\\d+}", method = RequestMethod.GET)
    public String getPo(HttpServletResponse response, @PathVariable("id") int id) {
    	ProtectObjectEntity po = poService.getPo(id);
    	if (po == null) {
            logger.warn("Can't get protect object(" + id + ") entity.");
            return returnHttp500Error(response, "Can't get protect object(" + id + ") entity.");
        }

    	List<FlowEntity> flowList = poService.getFlowByPoId(po.getId());
    	PoForm pf = new PoForm();
        UserEntity userEntity = poService.getUserById(po.getId());
        if (userEntity != null) {
            pf.setUsername(userEntity.getUsername());
            pf.setTenantId(userEntity.getId());
        } else {
            pf.setUsername("--");
        }
    	setPoForm(po, pf, flowList);
        return toJson(pf);
    }
    
    @ResponseBody
    @RequestMapping(value = "po", method = RequestMethod.POST)
    public String add(@RequestBody PoForm poForm) {

        //int userId = this.getCurrentUser().getId();
    	ProtectObjectEntity poEntity = new ProtectObjectEntity();
    	List<FlowEntity> poFlows = new ArrayList<FlowEntity>();
    	
        setPoProperties(poForm, poEntity);
        
        setPoFlows(poForm, poFlows);

        if (poForm.getTenantId() != -1) {
            poService.addPoByUserId(poForm.getTenantId(), poEntity, poFlows);
        } else {
            poService.addPo(poEntity, poFlows);
        }

        logger.info(this.getCurrentUser().getUsername() + " add po: " + poForm.getName());

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="po", method = RequestMethod.PUT)
    public String edit(@RequestBody PoForm poForm) {
        ProtectObjectEntity poEntity = new ProtectObjectEntity();
        List<FlowEntity> poFlows = new ArrayList<FlowEntity>();
        
        setPoProperties(poForm, poEntity);
        poEntity.setId(poForm.getId());
        //poEntity.setLearnStatus(f2bLeanStatus(poForm.getLearn_status()));
            
        setPoFlows(poForm, poFlows);

        poService.updatePo(poEntity, poFlows);

        logger.info(this.getCurrentUser().getUsername() + " edit po: " + poForm.getName());

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="po/{id:\\d+}", method = RequestMethod.DELETE)
    public String del(@PathVariable("id") int id) {
        logger.info(this.getCurrentUser().getUsername() + " delete po: " + poService.getPo(id).getName());

        poService.setPoDelFlag(Integer.valueOf(id));

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="isExistPO", method = RequestMethod.POST)
    public String isExistNetNode(@RequestBody PONameForm poNameForm) {
        int i = JSON_RETURN_EXISTED;

        if (poNameForm == null) {
            i= JSON_RETURN_NOT_EXIST;
            return toJson(i);
        }

        ProtectObjectEntity poEntity = poService.getProtectObject(poNameForm.getName());
        if (poEntity == null) {
            i = JSON_RETURN_NOT_EXIST;
            return toJson(i);
        }

        if (poNameForm.getId() != 0) {
            if(poEntity.getId() == poNameForm.getId()) {
                i = JSON_RETURN_NOT_EXIST;
            }
        }

        return toJson(i);
    }

    @ResponseBody
    @RequestMapping(value="isExistPONetwork", method = RequestMethod.POST)
    public String isExistNetwork(@RequestBody IdNetworkForm idNetworkForm) {

        int i = JSON_RETURN_EXISTED;

        if (idNetworkForm == null) {
            i= JSON_RETURN_NOT_EXIST;
            return toJson(i);
        }

        ProtectObjectEntity poEntity = poService.getPOByNetWork(idNetworkForm.getNetwork());
        if (poEntity == null) {
            i = JSON_RETURN_NOT_EXIST;
        }

        return toJson(i);
    }

    @ResponseBody
    @RequestMapping(value="batchDelPo", method = RequestMethod.POST)
    public String del1(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            logger.info(this.getCurrentUser().getUsername() + " batch delete po: " + poService.getPo(id).getName());
        }

    	poService.setPoDelFlag(ids);

        return returnSuccess();
    }
}
