package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import com.cetc.security.ddos.persistence.TrafficInfoEntity;
import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.service.POService;
import com.cetc.security.ddos.persistence.service.Role;
import com.cetc.security.ddos.persistence.service.TrafficDataService;

import org.apache.log4j.Logger;

import com.cetc.security.ddos.common.utils.AntiLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cw on 2016/5/12.
 */
@Controller
@RequestMapping("/trafficDisplay")
public class TrafficDisplayController extends BaseController {
    private static Logger logger = AntiLogger.getLogger(TrafficDisplayController.class);
    @Autowired
    private POService poService;
    @Autowired
    private TrafficDataService trafficDataService;

    class FlowInfo {
        long timestamp;
        double pkt;
        double byte1;

        public FlowInfo(long timestamp, double pkt, double byte1) {
            this.timestamp = timestamp;
            this.pkt = pkt;
            this.byte1 = byte1;
        }
    }
    class FlowInfoList {
        List<FlowInfo> list;

        public FlowInfoList () {
            list = new ArrayList<FlowInfo>();

            long Timestamp = System.currentTimeMillis();
            long x = 1000*60;
            Random ran=new Random(200);
            for(int i=60; i>0;i--) {
                FlowInfo info = new FlowInfo(Timestamp-x*i,ran.nextDouble(),ran.nextDouble());
                list.add(info);
            }
        }

        public List<FlowInfo> getList() {
            return list;
        }
    }

    @RequestMapping("getPO")
    @ResponseBody
    public String getPO(HttpServletRequest request) {
        int userId;
        String callback = request.getParameter("callback");
        System.out.println(request.getRequestURL());
        String json = null;
        userId = this.getCurrentUser().getId();
        if (this.getCurrentUser().getRole().equals(Role.TENANT)) {
            json = toJson(poService.getPoByUserId(userId));
        } else {
            json = toJson(poService.getAllPO());
        }

        return (callback+"("+json+")");
    }

    @RequestMapping("getPOTest")
    @ResponseBody
    public String getPOTest() {
        return (toJson(poService.getAllPO()));
    }

    @RequestMapping()
    //此处不能用@ResponseBody，否则页面中不能调用flowDisplay.vm，而仅显示字符串policy/flowDisplay
    public String flowDisplay(HttpServletRequest request) {
        List<ProtectObjectEntity> list = poService.getAllPO();
        request.setAttribute("polist", list);//把数据库中的值设置到polist，前端可以直接从polist获取值
        return "policy/flowDisplay";
    }

    /*@RequestMapping("getdata")
    @ResponseBody
    public String getFlowDisplay(HttpServletRequest request,
                                 @RequestParam(value = "displayType",required = true) int type,
                                 @RequestParam(value = "poName",required = false) String poname,
                                 @RequestParam(value = "protocal",required = true) int protocal) {
        FlowInfoList flist = new FlowInfoList();
        System.out.println(type+","+poname+","+protocal);

        List<FlowInfo> list = flist.getList();
        System.out.println("list:"+list);
        String json = toJson(list);
        //获取参数callback的值，等同于@RequestParam(value = "callback",required = true) String callback
        String callback = request.getParameter("callback");
        System.out.println(request.getRequestURL());
        System.out.println(request.getParameter("poName"));
        System.out.println(request.getParameter("protocal"));

        String return_string = callback+"("+json+")";
        System.out.println("tojson:"+json);
        return return_string;
    }*/
    @RequestMapping("getdata")
    @ResponseBody
    public String getFlowDisplay(HttpServletRequest request,
                                 @RequestParam(value = "displayType",required = false) int type,
                                 @RequestParam(value = "poID",required = false) int poID) {

        System.out.println("poID is "+request.getParameter("poID"));
        long now = System.currentTimeMillis();
        long start=0;
        TrafficListForm trafficForm = new TrafficListForm();
        switch (type) {
            case 1:
                start = now-3600*1000;
                break;
            case 2:
                start = now-24*3600*1000;
                break;
            case 3:
                start = now-7*24*3600*1000L;
                break;
            case 4:
                start = now-30*24*3600*1000L;//需要用long型否则（30*24*3600*1000）会发生溢出
                break;
            case 5:
                start = now-365*24*3600*1000L;
                break;
            default:
                logger.error("error type " + type);
                return null;
        }

        //获取参数callback的值，等同于@RequestParam(value = "callback",required = true) String callback
        String callback = request.getParameter("callback");
        System.out.println(request.getRequestURL());
        //System.out.println(request.getParameter("protocal"));

        System.out.println("start:"+start);
        System.out.println("end:"+now);
        
        List<TrafficInfoEntity> list = trafficDataService.getTrafficByTime(poID, type, start, now);
        List<TrafficInfoEntity> listTable = trafficDataService.getTrafficByBpsOrder(poID,type,start,now,5);

        String json;
        if(list == null || listTable == null)
        {
        	json = null;
        }
        else
        {
        	trafficForm.setTrafficAll(list);
            trafficForm.setTrafficList(listTable);
            
            json = toJson(trafficForm);	
        }
        
        return (callback+"("+json+")");
    }
    @RequestMapping("test")
    @ResponseBody
    public String getFlowTest(HttpServletRequest request) {
        FlowInfoList flist = new FlowInfoList();
        String json = toJson(flist.getList());
        //获取参数callback的值，等同于@RequestParam(value = "callback",required = true) String callback
        String callback = request.getParameter("callback");
        System.out.println(request.getRequestURL());
        return (callback+"("+json+")");
    }
    
    
    private class TrafficListForm {
        List<TrafficInfoEntity> trafficAll;
        List<TrafficInfoEntity> trafficList;
        
		public List<TrafficInfoEntity> getTrafficAll() {
			return trafficAll;
		}
		public void setTrafficAll(List<TrafficInfoEntity> trafficAll) {
			this.trafficAll = trafficAll;
		}
		public List<TrafficInfoEntity> getTrafficList() {
			return trafficList;
		}
		public void setTrafficList(List<TrafficInfoEntity> trafficList) {
			this.trafficList = trafficList;
		}

        
    }


}
