package com.cetc.backend.controller;

import com.cetc.backend.view.NetNodeForm;
import com.cetc.security.ddos.persistence.ControllerEntity;
import com.cetc.security.ddos.persistence.NetNodeEntity;
import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.ControllerService;
import com.cetc.security.ddos.persistence.service.NetNodeService;
import com.cetc.security.ddos.persistence.SwType;
import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasen on 2015/8/11.
 */
@Controller
@RequestMapping("/policy")
public class NetNodeController extends BaseController {
    private static Logger logger = AntiLogger.getLogger(NetNodeController.class);
    @Autowired
    private NetNodeService netnodeService;
    //用于获取已经配置的controller ID值
    @Autowired
    private static ControllerService controllerService;

    @ResponseBody
    @RequestMapping(value = "netNode", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) String page) {
        int start = getPageStart(page);

        List<NetNodeEntity> list = netnodeService.getNetNode(start, DEFAULT_PAGE_LIMIT);
        List<NetNodeForm> resultList = new ArrayList<NetNodeForm>();
        for (NetNodeEntity a : list) {
            NetNodeForm nnf = new NetNodeForm();
            setNetNodeForm(a, nnf);;
            resultList.add(nnf);
        }

        NetNodeListForm nnlf  = new NetNodeListForm();
        nnlf.setTotal(netnodeService.countNetNode());
        nnlf.setNetNodes(resultList);

        return toJson(nnlf);
    }

    @ResponseBody
    @RequestMapping(value = "getController", method = RequestMethod.GET)
    public String getControllerId() {

        List<ControllerEntity> controllerList = controllerService.getController();

        List<NetNodeForm> resultList = new ArrayList<NetNodeForm>();

        for (ControllerEntity a : controllerList) {
            NetNodeForm nnf = new NetNodeForm();
            nnf.setControllerId(a.getId());
            resultList.add(nnf);
        }

        return toJson(resultList);
    }

    @ResponseBody
    @RequestMapping(value = "getAllNetNode", method = RequestMethod.GET)
    public String getAllNetNode() {
        List<NetNodeEntity> list = netnodeService.getNetNode();
        List<NetNodeForm> resultList = new ArrayList<NetNodeForm>();
        for (NetNodeEntity a : list) {
            NetNodeForm nnf = new NetNodeForm();
            setNetNodeForm(a, nnf);;
            resultList.add(nnf);
        }

        return toJson(resultList);
    }

    private void setNetNodeForm(NetNodeEntity netnodeEntity, NetNodeForm netnodeForm) {
        netnodeForm.setId(netnodeEntity.getId());
        netnodeForm.setName(netnodeEntity.getName());
        netnodeForm.setSwId(netnodeEntity.getSwId());
        netnodeForm.setSwType(netnodeEntity.getSwType());
        netnodeForm.setControllerId(netnodeEntity.getControllerId());

    }

    private  void setNetNodeEntity(NetNodeForm netnodeForm, NetNodeEntity netnodeEntity){
        netnodeEntity.setId(netnodeForm.getId());
        netnodeEntity.setName(netnodeForm.getName());
        netnodeEntity.setSwId(netnodeForm.getSwId());
        netnodeEntity.setSwType(netnodeForm.getSwType());
        netnodeEntity.setControllerId(netnodeForm.getControllerId());
    }

    @RequestMapping("getNetNode")
    @ResponseBody
    public String getNetNode() {
        return toJson(netnodeService.getAllNetNode());
    }

    @ResponseBody
    @RequestMapping(value="netNode/{id:\\d+}", method = RequestMethod.GET)
    public String getNetNode(@PathVariable("id") int id) {
        NetNodeEntity netNode = netnodeService.getNetNode(id);

        NetNodeForm nnf = new NetNodeForm();
        setNetNodeForm(netNode, nnf);
        return toJson(nnf);
    }

    @ResponseBody
    @RequestMapping(value="isExistNetNode/{id:\\d+}/{name}", method = RequestMethod.GET)
    public String isExistNetNode(@PathVariable("id") int id, @PathVariable("name") String name) {
        int i = JSON_RETURN_EXISTED;

        NetNodeEntity netNode = netnodeService.getNetNodeByName(name);
        if (netNode != null) {
            if (netNode.getId() == id) {
                i = JSON_RETURN_NOT_EXIST;
            }
        } else {
            i = JSON_RETURN_NOT_EXIST;
        }
        return toJson(i);
    }

    @ResponseBody
    @RequestMapping(value = "netNode", method = RequestMethod.POST)
    public String add(HttpServletResponse response, @RequestBody NetNodeForm netnodeForm) {
        NetNodeEntity netNodeEntity = new NetNodeEntity();
        netnodeForm.setSwType(SwType.SW_ODL.getValue());
        try {
            setNetNodeEntity(netnodeForm, netNodeEntity);
        } catch (Exception e) {
            logger.error(this.getCurrentUser().getUsername() + " add netNode:set netnode properties fail:"
                    + e.getMessage());
            return returnHttp500Error(response, e.getMessage());
        }

        netnodeService.addNetNode(netNodeEntity);

        logger.info(this.getCurrentUser().getUsername() + " add netnode: " + netnodeForm.getName());

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "netNode", method = RequestMethod.PUT)
    public String edit(HttpServletResponse response, @RequestBody NetNodeForm netnodeForm) {

        netnodeForm.setSwType(SwType.SW_ODL.getValue());
        NetNodeEntity netNodeEntity = new NetNodeEntity();
        try {
            setNetNodeEntity(netnodeForm, netNodeEntity);
        } catch (Exception e) {
            logger.error(this.getCurrentUser().getUsername() + " edit netNode:set netnode properties fail:"
                    + e.getMessage());
            return returnHttp500Error(response, e.getMessage());
        }

        netnodeService.updateNetNode(netNodeEntity);

        logger.info(this.getCurrentUser().getUsername() + " edit netnode: " + netnodeForm.getName());

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="netNode/{id:\\d+}", method = RequestMethod.DELETE)
    public String del(@PathVariable("id") int id) {
        logger.info(this.getCurrentUser().getUsername() + " delete netnode: " + netnodeService.getNetNode(id).getName());

        netnodeService.setNetNodeDelFlag(Integer.valueOf(id));

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="batchDelNetNode", method = RequestMethod.POST)
    public String del1(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            logger.info(this.getCurrentUser().getUsername() + " batch delete netnode: "
                    + netnodeService.getNetNode(id).getName());
        }

        netnodeService.setNetNodeDelFlag(ids);

        return returnSuccess();
    }

    private class NetNodeListForm {
        private long total;
        private List<NetNodeForm> netNodes;

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<NetNodeForm> getNetNodes() {
            return netNodes;
        }

        public void setNetNodes(List<NetNodeForm> netNodes) {
            this.netNodes = netNodes;
        }
    }
}
