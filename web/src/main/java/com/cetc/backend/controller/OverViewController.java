package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.FlowStatisticsEntity;
import com.cetc.security.ddos.persistence.service.EventService;
import com.cetc.security.ddos.persistence.service.FlowStatisticsService;
import com.cetc.security.ddos.persistence.service.FlowStatisticsService.FlowStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lb on 2015/10/10.
 */
@Controller
@RequestMapping("/overView")
public class OverViewController extends BaseController {
    @Autowired
    private FlowStatisticsService fs;

    @Autowired
    private EventService eventService;

    @RequestMapping()
    @ResponseBody
    public String getOverView(@RequestParam(value = "page", required = false) String page) {
        List list=new ArrayList();

        FlowStat flowStat = fs.getTotalStat();
        list.add(flowStat);

        List<FlowStat> protocalStat = fs.getProtocolStat();
        list.add(protocalStat);

        FlowStatisticsInfo fsInfo = new FlowStatisticsInfo();
        fsInfo.total = fs.countFlowStat();
        fsInfo.limit = DEFAULT_PAGE_LIMIT;
        int start = getPageStart(page);
        fsInfo.stats = fs.getStat(start, DEFAULT_PAGE_LIMIT);
        list.add(fsInfo);

        List<EventService.Event> eventList = eventService.getEvent(0,DEFAULT_PAGE_LIMIT);
        list.add(eventList);

        System.out.println("1111");
        System.out.println(list);
        String json = toJson(list);

        return (json);
    }

    @RequestMapping("attackTable")
    @ResponseBody
    public String getAttackTable(HttpServletRequest request,
                                 @RequestParam(value = "page",required = true) String page) {
        int start = getPageStart(page);

        FlowStatisticsInfo fsInfo = new FlowStatisticsInfo();
        fsInfo.total = fs.countFlowStat();
        fsInfo.limit = DEFAULT_PAGE_LIMIT;
        fsInfo.stats = fs.getStat(start,DEFAULT_PAGE_LIMIT);

        System.out.println(fsInfo);
        String json = toJson(fsInfo);

        return (json);
    }

    class FlowStatisticsInfo {
        private long total;
        private long limit;
        private List<FlowStatisticsService.DetailFlowStat> stats;
    }
}
