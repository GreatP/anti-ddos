package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.AttackTypeCount;
import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.service.*;
import com.cetc.security.ddos.persistence.service.CleanDevFlowStatisticsService.*;
import com.cetc.security.ddos.persistence.service.AttackDetailService.DetailAttackInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanqsheng on 2016/5/23.
 */
@Controller
@RequestMapping("/attackAnalyse")
public class attackAnalyseController extends BaseController {
    @Autowired
    private AttackDefenseService attackDefenseService;

    @Autowired
    private AttackIpService attackIpService;

    @Autowired
    private POService poService;

    @Autowired
    private CleanDevFlowStatisticsService cleanDevFlowStatisticsService;

    @Autowired
    private AttackDetailService attackDetailService;

    @RequestMapping()
    @ResponseBody
    public String getOverView(@RequestParam(value = "page", required = false) String page) {
        int userId;
        List list=new ArrayList();

        UserEntity userEntity = this.getCurrentUser();
        if (userEntity == null) {
            System.out.println("AttackAnalyse:getOverView userEntity is null.");
        }

        userId = this.getCurrentUser().getId();
        AttackStatisticsInfo attackStatisticsInfo = new AttackStatisticsInfo();
        attackStatisticsInfo.limit = DEFAULT_PAGE_LIMIT;
        int start = getPageStart(page);


        if (this.getCurrentUser().getRole().equals(Role.TENANT)) {
            AttackDetailService.AttackTotalStat attackTotalStat = attackDetailService.getAttackTotalStatByUserId(userId);
            list.add(attackTotalStat);

            List<AttackTypeCount> attackTypeStat = attackDetailService.getAttackTypeCountByUserId(userId);
            list.add(attackTypeStat);

            List<AttackDetailService.DetailAttackDefenseInfo> detailAttackDefenseInfo = attackDetailService.getAttackDefenseInfoByTimeAndUserId(userId);
            list.add(detailAttackDefenseInfo);

            attackStatisticsInfo.total = attackDetailService.countAttackDetailStatByUserId(userId);
            attackStatisticsInfo.stats = attackDetailService.getstatByUserId(userId, start, DEFAULT_PAGE_LIMIT);
            list.add(attackStatisticsInfo);

        } else {
            AttackDetailService.AttackTotalStat attackTotalStat = attackDetailService.getAttackTotalStat();
            list.add(attackTotalStat);

            List<AttackTypeCount> attackTypeStat = attackDetailService.getAttackTypeCount();
            list.add(attackTypeStat);

            List<AttackDetailService.DetailAttackDefenseInfo> detailAttackDefenseInfo = attackDetailService.getAttackDefenseInfoByTime();
            list.add(detailAttackDefenseInfo);

            attackStatisticsInfo.total = attackDetailService.countAttackDetailStat();
            attackStatisticsInfo.stats = attackDetailService.getstat(start, DEFAULT_PAGE_LIMIT);
            list.add(attackStatisticsInfo);

        }

        //List<DetailAttackInfo> detailAttackInfo = attackDetailService.getAllAttackDetailInfo();

        System.out.println(list);
        String json = toJson(list);

        return (json);
    }

    @RequestMapping("attackDetailTable")
    @ResponseBody
    public String getAttackTable(HttpServletRequest request,
                                 @RequestParam(value = "page",required = true) String page) {
        int start = getPageStart(page);
        int userId = this.getCurrentUser().getId();

        AttackStatisticsInfo attackStatisticsInfo = new AttackStatisticsInfo();
        attackStatisticsInfo.limit = DEFAULT_PAGE_LIMIT;

        if (this.getCurrentUser().getRole().equals(Role.TENANT)) {
            attackStatisticsInfo.total = attackDetailService.countAttackDetailStatByUserId(userId);
            attackStatisticsInfo.stats = attackDetailService.getstatByUserId(userId, start, DEFAULT_PAGE_LIMIT);
        } else {
            attackStatisticsInfo.total = attackDetailService.countAttackDetailStat();
            attackStatisticsInfo.stats = attackDetailService.getstat(start, DEFAULT_PAGE_LIMIT);
        }

        System.out.println(attackStatisticsInfo);
        String json = toJson(attackStatisticsInfo);

        return (json);
    }

    class AttackStatisticsInfo {
        private long total;
        private long limit;
        private List<AttackDetailService.DetailAttackInfo> stats;
    }
}
