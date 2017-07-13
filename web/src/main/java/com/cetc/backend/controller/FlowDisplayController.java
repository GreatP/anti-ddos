package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import com.cetc.security.ddos.persistence.service.FlowDataService;
import com.cetc.security.ddos.persistence.service.POService;
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
 * Created by lb on 2015/8/11.
 */
@Controller
@RequestMapping("/flowDisplay")
public class FlowDisplayController extends BaseController {
    private static Logger logger = AntiLogger.getLogger(FlowDisplayController.class);
    @Autowired
    private POService poService;
    @Autowired
    private FlowDataService flowDataService;

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
        String callback = request.getParameter("callback");
        System.out.println(request.getRequestURL());
        String json = toJson(poService.getAllPO());
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
                                 @RequestParam(value = "poID",required = false) int poID,
                                 @RequestParam(value = "protocol",required = false) short protocol) {
        int flowid = poService.getFlowId(poID,protocol);

        System.out.println("poID is "+request.getParameter("poID"));
        long now = System.currentTimeMillis();
        String S_type;
        long start=0;
        switch (type) {
            case 1:
                S_type="hour";
                start = now-3600*1000;
                break;
            case 2:
                S_type="day";
                start = now-24*3600*1000;
                break;
            case 3:
                S_type="week";
                start = now-7*24*3600*1000L;
                break;
            case 4:
                S_type="month";
                start = now-30*24*3600*1000L;//需要用long型否则（30*24*3600*1000）会发生溢出
                break;
            case 5:
                S_type="year";
                start = now-365*24*3600*1000L;
                break;
            default:
                logger.error("error type " + type);
                return null;
        }

        //获取参数callback的值，等同于@RequestParam(value = "callback",required = true) String callback
        String callback = request.getParameter("callback");
        System.out.println(request.getRequestURL());
        System.out.println(request.getParameter("protocal"));



        System.out.println("start:"+start);
        System.out.println("end:"+now);
        //String json = toJson(flowData.getDataByTime("po1",protocal,S_type,start,now));
        String json = flowDataService.getDataByTime("anti", flowid, S_type, start, now);
        //logger.debug("Flow display get data!");
        //System.out.println(json);
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


}
