package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.controller.adapter.TrafficTuple;
import com.cetc.security.ddos.persistence.EventEntity;
import com.cetc.security.ddos.persistence.EventType;
import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.EventService;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

/**
 * Created by lb on 2015/10/12.
 */
public class FlowEventSave {
    private static Logger logger = AntiLogger.getLogger(FlowEventSave.class);
    private static EventService eventService = PersistenceEntry.getInstance().getEventService();

    int flowID;

    public FlowEventSave(int flowID) {
        this.flowID = flowID;
    }

    public void flowEventAdd(EventType type,TrafficTuple attackRate) {
        EventEntity eventEntity = new EventEntity();
        if (attackRate != null) {
            eventEntity.setAttackSpeed(attackRate.getBytes());
            eventEntity.setAttackPps((long) attackRate.getPackets());
        }
        eventEntity.setType(type);

        eventService.addEvent(flowID,eventEntity);
        if (attackRate != null) {
            logger.info("flow event add,flow id "+ flowID +" EventType "+type+" attackBps "+attackRate.getBytes()
                    +" attackpps "+ attackRate.getPackets());
        } else {
            logger.info("flow event add,flow id "+ flowID +" EventType "+type);
        }

    }
}
