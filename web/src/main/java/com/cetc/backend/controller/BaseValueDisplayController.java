package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import com.cetc.security.ddos.persistence.dao.AutoLearnBaseValueDao;
import com.cetc.security.ddos.persistence.service.AutoLearnBaseValueService;
import com.cetc.security.ddos.persistence.service.POService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lb on 2015/8/14.
 */
@Controller
@RequestMapping("/baseValueDisplay")
public class BaseValueDisplayController extends BaseController {
    @Autowired
    private AutoLearnBaseValueService autoLearnBaseValueService;
    @Autowired
    private POService poService;

    @RequestMapping()
    //此处不能用@ResponseBody，否则页面中不能调用flowDisplay.vm，而仅显示字符串policy/flowDisplay
    public String flowDisplay(HttpServletRequest request) {
        List<ProtectObjectEntity> list = poService.getAllPO();
        request.setAttribute("polist", list);//把数据库中的值设置到polist，前端可以直接从polist获取值
        return "policy/baseValueDisplay";
    }

    @RequestMapping("getdata")
    @ResponseBody
    public String getFlowDisplay(HttpServletRequest request,
                                 @RequestParam(value = "poID",required = false) int poID,
                                 @RequestParam(value = "protocol",required = false) short protocol,
                                 @RequestParam(value = "week",required = false) int week) {

        System.out.println("poID is "+request.getParameter("poID"));
        System.out.println("protocol is "+request.getParameter("protocol"));
        System.out.println("week is "+week);

        int flowid = poService.getFlowId(poID,protocol);

        String json;
        if (week == 10) {//all
            json = toJson(autoLearnBaseValueService.getBaseValue(flowid));
        } else {
            json = toJson(autoLearnBaseValueService.getBaseValue(flowid, week));
        }

        //获取参数callback的值，等同于@RequestParam(value = "callback",required = true) String callback
        String callback = request.getParameter("callback");

        return (callback+"("+json+")");
    }

}
