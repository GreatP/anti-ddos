package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.FlowStatisticsService;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

/**
 * Created by lb on 2015/10/10.
 */
public class OverViewSave {
    private static Logger logger = AntiLogger.getLogger(OverViewSave.class);
    private static FlowStatisticsService fs = PersistenceEntry.getInstance().getFlowStatService();

    private long normalSpeedCount = 0;
    private long exceptionSpeedCount = 0;
    private long attackCount = 0;
    private long defenseCount = 0;
    private long recoverCount = 0;

    int flowID;
    private long startTime = 0;
    private static final int saveTimeInterval = 0;//用于控制多长时间写一次数据库，发行版本设为30分钟，测试设为30秒

    public OverViewSave(int flowID) {
        this.flowID = flowID;
    }

    private void countSetZero() {
        this.normalSpeedCount = 0;
        this.exceptionSpeedCount = 0;
        this.attackCount = 0;
        this.defenseCount = 0;
        this.recoverCount = 0;
    }

    public void countIncrease(StatReport statReport ,boolean normal,boolean exception,boolean attack,boolean defense,boolean recover) {
        long time = statReport.getReadingTime();

        if (normal) {
            normalSpeedCount++;
        }

        if (exception) {
            exceptionSpeedCount++;
        }

        if (attack) {
            attackCount++;
        }

        if (defense) {
            defenseCount++;
        }

        if (recover) {
            recoverCount++;
        }

        if (startTime == 0 || time - startTime >= saveTimeInterval) {//第一次无需缓存直接保存到数据库
            logger.debug("increase overView Count into SQL,flow id "+flowID+" normal "+normalSpeedCount
                    +" exception "+exceptionSpeedCount+" attackCount "+attackCount
                    +" defenseCount "+defenseCount+" recoverCount "+recoverCount);
            fs.updateStat(flowID,normalSpeedCount,exceptionSpeedCount,attackCount,defenseCount,recoverCount);

            countSetZero();
        }

        startTime = time;
    }

}
