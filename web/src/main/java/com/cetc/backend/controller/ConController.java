package com.cetc.backend.controller;

import com.cetc.backend.view.ControllerForm;
import com.cetc.security.ddos.common.type.ControllerType;
import com.cetc.security.ddos.persistence.ControllerEntity;
import com.cetc.security.ddos.persistence.ControllerIfaceEntity;
import com.cetc.security.ddos.persistence.DDoSParamEntity;
import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.ControllerService;
import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangtao on 2015/8/6.
 */
@Controller
@RequestMapping("/policy")
public class ConController extends BaseController {
    private static Logger logger = AntiLogger.getLogger(ConController.class);
    @Autowired
    private ControllerService controllerService;


    protected void setControllerProperties(ControllerForm controllerForm,
                                            ControllerEntity controllerEntity,
                                            DDoSParamEntity dDoSParamEntity) {
        controllerEntity.setIp(controllerForm.getIp());
        controllerEntity.setPort(controllerForm.getPort());
        controllerEntity.setUser(controllerForm.getUser());
        controllerEntity.setPassword(controllerForm.getPassword());
        controllerEntity.setType(ControllerType.getControllerType(controllerForm.getType()));

        dDoSParamEntity.setDetectionInterval(controllerForm.getDetectionInterval());
        dDoSParamEntity.setRecoverNormalThreshold(controllerForm.getRecoverNormalThreshold());
        dDoSParamEntity.setAttackSuspicionsThreshold(controllerForm.getAttackSuspicionsThreshold());
        dDoSParamEntity.setDetectionDeviationPercentage(controllerForm.getDetectionDeviationPercentage());
    }

    protected void setControllerForm(ControllerEntity ce,
                                     DDoSParamEntity dDoSParamEntity,
                                     ControllerForm cf,
                                     ControllerIfaceEntity ci) {
        cf.setId(ce.getId());
        cf.setIp(ce.getIp());
        cf.setPort(ce.getPort());
        cf.setType(ce.getType().getValue());
        cf.setUser(ce.getUser());
        cf.setPassword(ce.getPassword());
        cf.setDetectionInterval(dDoSParamEntity.getDetectionInterval());
        cf.setDetectionDeviationPercentage(dDoSParamEntity.getDetectionDeviationPercentage());
        cf.setAttackSuspicionsThreshold(dDoSParamEntity.getAttackSuspicionsThreshold());
        cf.setRecoverNormalThreshold(dDoSParamEntity.getRecoverNormalThreshold());

        cf.setInport(ci.getInPort());
        cf.setOutport(ci.getOutPort());
    }

    protected List<ControllerForm> getControllerFormList(List<ControllerEntity> list) {
        List<ControllerForm> resultList = new ArrayList<ControllerForm>();
        for (ControllerEntity c : list) {
            ControllerForm cf = new ControllerForm();
            DDoSParamEntity dDoSParamEntity = controllerService.getDDoSParam(c.getId());
            ControllerIfaceEntity controllerIfaceEntity = controllerService.getControllerIface(c.getId());
            if (dDoSParamEntity == null) {
                continue;
            }

            if (controllerIfaceEntity == null) {
                continue;
            }

            setControllerForm(c, dDoSParamEntity, cf, controllerIfaceEntity);
            resultList.add(cf);
        }
        return resultList;
    }

    @ResponseBody
    @RequestMapping(value = "controller", method = RequestMethod.GET)
    public String list(@RequestParam(value="page", required = false) String page) {
        int start = getPageStart(page);

        logger.debug("start:" + start + ", limit:" + DEFAULT_PAGE_LIMIT);

        List<ControllerEntity> list = controllerService.getController(start, DEFAULT_PAGE_LIMIT);
        List<ControllerForm> resultList = getControllerFormList(list);

        ControllerListForm clf = new ControllerListForm();
        clf.setTotal(controllerService.countController());
        clf.setControllers(resultList);
        return toJson(clf);
        //return (callback + "("+json+")");
        //HttpResponseStatus s = new HttpResponseStatus(500, "");
        //response.setStatus(500);
        //json = returnError("ggrhrh");
        //return (callback + "("+json+")");
        //return returnError("ggrhrh");

        //request.setAttribute("list", list);
        //return new ModelAndView("policy/controllerList");
    }

    @ResponseBody
    @RequestMapping(value = "getAllController", method = RequestMethod.GET)
    public String list() {
        List<ControllerEntity> list = controllerService.getController();
        List<ControllerForm> resultList = getControllerFormList(list);
        return toJson(resultList);
    }

    @ResponseBody
    @RequestMapping(value = "isExistController/{id:\\d+}/{ip}/{port:\\d+}", method = RequestMethod.GET)
    public String isExistSameController(@PathVariable("id") int id, @PathVariable("ip") String ip,
                                        @PathVariable("port") int port) {
        int i = JSON_RETURN_EXISTED;
        ControllerEntity ce = controllerService.getController(ip, port);
        if (ce != null) {
            if (ce.getId() == id) {
                i = JSON_RETURN_NOT_EXIST;
            }
        } else {
            i = JSON_RETURN_NOT_EXIST;
        }
        return toJson(i);
    }

    @ResponseBody
    @RequestMapping(value="controller/{id:\\d+}", method = RequestMethod.GET)
    public String getController(HttpServletResponse response, @PathVariable("id") int id) {
        ControllerEntity ce = controllerService.getController(id);
        if (ce == null) {
            logger.warn("Can't get controller(id:" + id + ") entity.");
            return returnHttp500Error(response, "Can't get controller(id:" + id + ") entity.");
        }

        DDoSParamEntity dDoSParamEntity = controllerService.getDDoSParam(ce.getId());
        if (dDoSParamEntity == null) {
            logger.warn("Can't get DDoSParam(id:" + id + ") entity.");
            return returnHttp500Error(response, "Can't get controller(id:" + id + ") ddos paramter.");
        }

        ControllerIfaceEntity ci = controllerService.getControllerIface(ce.getId());
        if (ci == null) {
            logger.warn("Can't get ControllerIface(id:" + id + ") entity.");
            return returnHttp500Error(response, "Can't get controller(id:" + id + ") ddos paramter.");
        }

        ControllerForm cf = new ControllerForm();
        setControllerForm(ce, dDoSParamEntity, cf, ci);

        return toJson(cf);
    }

    @ResponseBody
    @RequestMapping(value = "controller", method = RequestMethod.POST)
    public String add( @RequestBody ControllerForm controllerForm) {
        ControllerEntity controllerEntity = new ControllerEntity();
        DDoSParamEntity dDoSParamEntity = new DDoSParamEntity();

        ControllerIfaceEntity controllerIfaceEntity = new ControllerIfaceEntity();
        controllerIfaceEntity.setInPort(controllerForm.getInport());
        controllerIfaceEntity.setOutPort(controllerForm.getOutport());

        setControllerProperties(controllerForm, controllerEntity, dDoSParamEntity);
        controllerService.addController(controllerEntity, dDoSParamEntity, controllerIfaceEntity);
        logger.info(this.getCurrentUser().getUsername() + " add controller: " + controllerEntity.getIp() + ":"
                    + controllerEntity.getPort());
        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="controller", method = RequestMethod.PUT)
    public String edit(@RequestBody ControllerForm controllerForm) {
        ControllerEntity controllerEntity = new ControllerEntity();
        DDoSParamEntity dDoSParamEntity = new DDoSParamEntity();
        ControllerIfaceEntity controllerIfaceEntity = new ControllerIfaceEntity();

        setControllerProperties(controllerForm, controllerEntity, dDoSParamEntity);
        controllerEntity.setId(controllerForm.getId());
        dDoSParamEntity.setControllerId(controllerForm.getId());

        controllerIfaceEntity.setInPort(controllerForm.getInport());
        controllerIfaceEntity.setOutPort(controllerForm.getOutport());

        controllerService.updateController(controllerEntity, dDoSParamEntity, controllerIfaceEntity);
        logger.info(this.getCurrentUser().getUsername() + " edit controller: " + controllerEntity.getIp() + ":"
                + controllerEntity.getPort());
        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="controller/{id:\\d+}", method = RequestMethod.DELETE)
    public String del(@PathVariable("id") int id) {
        logger.info(this.getCurrentUser().getUsername() + " delete controller: "
                + controllerService.getController(id).getIp() + ":" + controllerService.getController(id).getPort());
        controllerService.setControllerDelFlag(Integer.valueOf(id));
        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="batchDelController", method = RequestMethod.POST)
    public String del(@RequestBody List<Integer> ids) {
        for(Integer id : ids) {
            logger.info(this.getCurrentUser().getUsername() + " batch delete controller: "
                  + controllerService.getController(id).getIp() + ":" + controllerService.getController(id).getPort());
        }

        controllerService.setControllerDelFlag(ids);
        return returnSuccess();
    }

    private class ControllerListForm {
        private long total;
        private List<ControllerForm> controllers;

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<ControllerForm> getControllers() {
            return controllers;
        }

        public void setControllers(List<ControllerForm> controllers) {
            this.controllers = controllers;
        }
    }
}
