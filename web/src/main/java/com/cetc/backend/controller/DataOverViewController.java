package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.service.*;
import com.cetc.security.ddos.persistence.service.IpTrafficInfoService.DetailIpTrafficInfo;
import com.cetc.security.ddos.persistence.service.PoTrafficInfoService.DetailPoTrafficInfo;
import com.cetc.security.ddos.persistence.service.InputTrafficInfoService.DetailInputTrafficInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hanqsheng on 2016/5/10.
 */
@Controller
@RequestMapping("/dataOverview")
public class DataOverViewController extends BaseController {
    @Autowired
    private PoTrafficInfoService poTrafficService;

    @Autowired
    private IpTrafficInfoService ipTrafficService;

    @Autowired
    private InputTrafficInfoService inputTrafficInfoService;

    @Autowired
    private AttackDetailService attackDetailService;

    @Autowired
    private TrafficDataService trafficDataService;

    @RequestMapping("dataDisplay")
    @ResponseBody
    public String getOverView() {
        int start = 0;
        int hour;
        int limit = 10;
        long interval = 0;
        long start_time = 0;
        String tsStartStr = "";
        String tsEndStr = "";
        Timestamp startTimeStamp;
        Timestamp endTimeStamp;
        Date startDate = null;
        Date endDate = null;
        List list= new ArrayList();
        List<TrafficDataService.DetailTrafficContrastInfo> detailTrafficContrastInfos = null;

        /* 展示最近一个小时内的流量对比情况 */
        long now = System.currentTimeMillis();

        endTimeStamp = new Timestamp(now);
       // endDate = new Date(now);
        endDate = endTimeStamp;

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");


        interval =  3600*1000;
        start_time = now - interval;

        //startDate = new Date(start_time);
        startTimeStamp = new Timestamp(start_time);
        startDate = startTimeStamp;


        detailTrafficContrastInfos = trafficDataService.getTrafficInfoRangeTime(start_time, now);
        /* 如果近小时内无流量，则查询近两小时，的以此类推，直到一天前的流量 */
        if (detailTrafficContrastInfos == null) {
            for (hour =1; hour < 24; hour++) {
                start_time = now - (interval * (hour + 1));
                startTimeStamp = new Timestamp(start_time);
                startDate = startTimeStamp;
                detailTrafficContrastInfos = trafficDataService.getTrafficInfoRangeTime(start_time, now);
                if (detailTrafficContrastInfos != null) {
                    break;
                }
            }
        }
        list.add(detailTrafficContrastInfos);

        List<DetailInputTrafficInfo> detailInputTrafficInfo = inputTrafficInfoService.getInputTrafficStat();
        list.add(detailInputTrafficInfo);

        List<DetailPoTrafficInfo> detailPoTrafficInfos = poTrafficService.getPoTrafficStat(start, limit);
        list.add(detailPoTrafficInfos);

        List<AttackDetailService.AttackSrcTop10> attackSrcTop10 = attackDetailService.getAttackSrcTop10();
        list.add(attackSrcTop10);


        System.out.println(list);
        String json = toJson(list);

        return (json);
    }
}
