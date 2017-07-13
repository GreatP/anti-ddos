package com.cetc.backend.controller;

import com.cetc.backend.view.CleanDevForm;
import com.cetc.security.ddos.common.type.ControllerType;
import com.cetc.security.ddos.persistence.*;
import com.cetc.security.ddos.persistence.service.CleanDevService;

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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanqsheng on 2016/4/27.
 */
@Controller
@RequestMapping("/policy")
public class CleanDevController extends BaseController {
	private static Logger logger = AntiLogger.getLogger(CleanDevController.class);
    @Autowired
    private CleanDevService cleandevService;


    @ResponseBody
    @RequestMapping(value = "cleanDev", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) String page) {
        int start = getPageStart(page);

        List<CleanDevEntity> list = cleandevService.getCleanDev(start, DEFAULT_PAGE_LIMIT);
        List<CleanDevForm> resultList = new ArrayList<CleanDevForm>();
        for (CleanDevEntity a : list) {
            CleanDevForm nnf = new CleanDevForm();
            setCleanDevForm(a, nnf);
            resultList.add(nnf);
        }

        CleanDevListForm nnlf  = new CleanDevListForm();

        nnlf.setTotal(cleandevService.countCleanDev());
        nnlf.setCleandevs(resultList);

        return toJson(nnlf);
    }


    @ResponseBody
    @RequestMapping(value = "getAllCleanDev", method = RequestMethod.GET)
    public String getAllCleanDev() {
        List<CleanDevEntity> list = cleandevService.getCleanDev();
        List<CleanDevForm> resultList = new ArrayList<CleanDevForm>();
        for (CleanDevEntity a : list) {
            CleanDevForm nnf = new CleanDevForm();
            setCleanDevForm(a, nnf);
            resultList.add(nnf);
        }

        return toJson(resultList);
    }

    private void setCleanDevForm(CleanDevEntity cdEntity, CleanDevForm cdForm) {
        cdForm.setId(cdEntity.getId());
        cdForm.setIp(cdEntity.getIp());
        if (cdEntity.getDirect() == CleanDirection.UNI_DIRECTION) {
            cdForm.setDirect("单向清洗");
        } else {
            cdForm.setDirect("双向清洗");
        }

        cdForm.setTcp_first(cdEntity.isTcpFirst());
        cdForm.setCheck_interval(cdEntity.getCheck_interval());
        cdForm.setTcp(cdEntity.getTcp());
        cdForm.setTcp_abnormal(cdEntity.getTcp_abnormal());
        cdForm.setUdp(cdEntity.getUdp());
        cdForm.setIcmp(cdEntity.getIcmp());
        //cdForm.setIcmp_abnormal(cdEntity.getIcmp_abnormal());
        cdForm.setHttp(cdEntity.getHttp());
        cdForm.setHttp_header(cdEntity.getHttp_header());
        cdForm.setHttp_post(cdEntity.getHttp_post());
        cdForm.setHttp_port(cdEntity.getHttp_port());
        cdForm.setHttps(cdEntity.getHttps());
        cdForm.setHttps_thc(cdEntity.getHttps_thc());
        cdForm.setHttps_port(cdEntity.getHttps_port());
        cdForm.setDns_request(cdEntity.getDns_request());
        cdForm.setDns_reply(cdEntity.getDns_reply());
        cdForm.setDns_abnormal(cdEntity.getDns_abnormal());
        cdForm.setDns_port(cdEntity.getDns_port());


        cdForm.setNtp(cdEntity.getNtp());
        cdForm.setNtp_port(cdEntity.getNtpPort());
        cdForm.setSnmp(cdEntity.getSnmp());
        cdForm.setSnmp_port(cdEntity.getSnmpPort());
    }

    private  void setCleanDevEntity(CleanDevForm cdForm, CleanDevEntity cdEntity){
        cdEntity.setId(cdForm.getId());
        cdEntity.setIp(cdForm.getIp());
        cdEntity.setUser("root");
        cdEntity.setPassword("123456");




        if (cdForm.getDirect().equals("uni-direction")) {
            cdEntity.setDirect(CleanDirection.UNI_DIRECTION);
        } else {
            cdEntity.setDirect(CleanDirection.BI_DIRECTION);
        }

        cdEntity.setTcpFirst(cdForm.isTcp_first());
        cdEntity.setCheck_interval(cdForm.getCheck_interval());
        cdEntity.setTcp(cdForm.getTcp());
        cdEntity.setTcp_abnormal(cdForm.getTcp_abnormal());
        cdEntity.setUdp(cdForm.getUdp());
        cdEntity.setIcmp(cdForm.getIcmp());
        //cdEntity.setIcmp_abnormal(cdForm.getIcmp_abnormal());
        cdEntity.setHttp(cdForm.getHttp());
        cdEntity.setHttp_header(cdForm.getHttp_header());
        cdEntity.setHttp_post(cdForm.getHttp_post());
        cdEntity.setHttp_port(cdForm.getHttp_port());
        cdEntity.setHttps(cdForm.getHttps());
        cdEntity.setHttps_thc(cdForm.getHttps_thc());
        cdEntity.setHttps_port(cdForm.getHttps_port());
        cdEntity.setDns_request(cdForm.getDns_request());
        cdEntity.setDns_reply(cdForm.getDns_reply());
        cdEntity.setDns_port(cdForm.getDns_port());
        cdEntity.setDns_abnormal(cdForm.getDns_abnormal());

        cdEntity.setNtp(cdForm.getNtp());
        cdEntity.setNtpPort(cdForm.getNtp_port());
        cdEntity.setSnmp(cdForm.getSnmp());
        cdEntity.setSnmpPort(cdForm.getSnmp_port());
    }

    @RequestMapping("getCleanDev")
    @ResponseBody
    public String getCleanDev() {
        return toJson(cleandevService.getAllCleanDev());
    }

    @ResponseBody
    @RequestMapping(value="cleanDev/{id:\\d+}", method = RequestMethod.GET)
    public String getCleanDev(@PathVariable("id") int id) {
        CleanDevEntity cleanDev = cleandevService.getCleanDev(id);

        CleanDevForm nnf = new CleanDevForm();
        setCleanDevForm(cleanDev, nnf);
        return toJson(nnf);
    }

    @ResponseBody
    @RequestMapping(value="isExistCleanDev/{id:\\d+}/{ip:[0-9\\.]+}", method = RequestMethod.GET)
    public String isExistCleanDev(@PathVariable("id") int id, @PathVariable("ip") String ip) {
        int i = JSON_RETURN_EXISTED;

        CleanDevEntity cleanDev = cleandevService.getCleanDevByIp(ip);
        if (cleanDev != null) {
            if (cleanDev.getId() == id) {
                i = JSON_RETURN_NOT_EXIST;
            }
        } else {
            i = JSON_RETURN_NOT_EXIST;
        }
        return toJson(i);
    }

    @ResponseBody
    @RequestMapping(value = "cleanDev", method = RequestMethod.POST)
    public String add(HttpServletResponse response, @RequestBody CleanDevForm cleanDev) {
        CleanDevEntity cdEntity = new CleanDevEntity();
        try {
            setCleanDevEntity(cleanDev, cdEntity);
        } catch (Exception e) {
            logger.error(this.getCurrentUser().getUsername() + " add cleandev:set cleandev properties fail:"
                    + e.getMessage());
            return returnHttp500Error(response, e.getMessage());
        }

        cleandevService.addCleanDev(cdEntity);

        logger.info(this.getCurrentUser().getUsername() + " add cleandev: " + cleanDev.getIp());

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "cleanDev", method = RequestMethod.PUT)
    public String edit(HttpServletResponse response, @RequestBody CleanDevForm cleanDev) {

        CleanDevEntity cdEntity = new CleanDevEntity();
        try {
            setCleanDevEntity(cleanDev, cdEntity);
        } catch (Exception e) {
            logger.error(this.getCurrentUser().getUsername() + " edit cleanDev:set cleandev properties fail:"
                    + e.getMessage());
            return returnHttp500Error(response, e.getMessage());
        }

        cleandevService.updateCleanDev(cdEntity);

        logger.info(this.getCurrentUser().getUsername() + " edit cleandev: " + cleanDev.getIp());

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="cleanDev/{id:\\d+}", method = RequestMethod.DELETE)
    public String del(@PathVariable("id") int id) {
        logger.info(this.getCurrentUser().getUsername() + " delete cleandev: " + cleandevService.getCleanDev(id).getIp());

        cleandevService.setCleanDevDelFlag(Integer.valueOf(id));

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value="batchDelCleanDev", method = RequestMethod.POST)
    public String del1(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            logger.info(this.getCurrentUser().getUsername() + " batch delete cleandev: "
                    + cleandevService.getCleanDev(id).getIp());
        }

        cleandevService.setCleanDevDelFlag(ids);

        return returnSuccess();
    }

    private class CleanDevListForm {
        private long total;
        private List<CleanDevForm> cleandevs;

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<CleanDevForm> getCleanDevs() {
            return cleandevs;
        }

        public void setCleandevs(List<CleanDevForm> cleandevs) {
            this.cleandevs = cleandevs;
        }
    }
}
