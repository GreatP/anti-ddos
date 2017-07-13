package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.*;
import com.cetc.security.ddos.persistence.dao.EventDao;
import com.cetc.security.ddos.persistence.dao.ProtectObjectDao;

import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by zhangtao on 2015/10/10.
 */
@Service
public class EventService {
    private static Logger logger = AntiLogger.getLogger(EventService.class);
    @Autowired
    private EventDao eventDao;
    @Autowired
    private ProtectObjectDao protectObjectDao;

    private static short MAX_ITEM_NUM = 10;
    private static int INTERVAL_TIME = 24 * 60 * 60 * 1000;
    private static long lastDelEventTime = 0;


    public void addEvent(int flowId, EventEntity eventEntity) {
        FlowEntity flowEntity = new FlowEntity();
        flowEntity.setId(flowId);
        eventEntity.setFlowEntity(flowEntity);
        eventDao.insert(eventEntity);

        long now = System.currentTimeMillis();
        if (Math.abs(now - lastDelEventTime) >= INTERVAL_TIME) {
            lastDelEventTime = now;

            List<EventEntity> l = eventDao.findOrderedDesc(0, MAX_ITEM_NUM);
            if (l.size() < MAX_ITEM_NUM) {
                return;
            }

            EventEntity e = l.get(MAX_ITEM_NUM - 1);
            eventDao.deleteBeforeTime(e.getTime().getTime());
        }
    }

    public List<Event> getEvent(int start, int limit) {
        Map<Integer, ProtectObjectEntity> map = new HashMap<Integer, ProtectObjectEntity>();
        List<Event> list = new ArrayList<Event>();

        List<EventEntity> l = eventDao.findOrderedDesc(start, limit);
        for (EventEntity e : l) {
            ProtectObjectEntity po = protectObjectDao.findById(e.getFlowEntity().getPoId(), map);
            if (po == null) {
                continue;
            }

            Event event = new Event();
            event.setType(e.getType());
            event.setProtocol(e.getFlowEntity().getProtocol());
            event.setAttackSpeed(e.getAttackSpeed());
            event.setAttackPps(e.getAttackPps());
            event.setPoName(po.getName());
            event.setDefenseType(po.getDefenseType());
            event.setTime(e.getTime().getTime());
            list.add(event);
        }

        return list;
    }

    public class Event {
        private EventType type;
        private String poName;
        private short protocol;
        private double attackSpeed;
        private long attackPps;
        private DefenseType defenseType;
        //@Temporal(TemporalType.TIMESTAMP)
        private long time;

        public EventType getType() {
            return type;
        }

        public void setType(EventType type) {
            this.type = type;
        }

        public String getPoName() {
            return poName;
        }

        public void setPoName(String poName) {
            this.poName = poName;
        }

        public short getProtocol() {
            return protocol;
        }

        public void setProtocol(short protocol) {
            this.protocol = protocol;
        }

        public double getAttackSpeed() {
            return attackSpeed;
        }

        public void setAttackSpeed(double attackSpeed) {
            this.attackSpeed = attackSpeed;
        }

        public long getAttackPps() {
            return attackPps;
        }

        public void setAttackPps(long attackPps) {
            this.attackPps = attackPps;
        }

        public DefenseType getDefenseType() {
            return defenseType;
        }

        public void setDefenseType(DefenseType defenseType) {
            this.defenseType = defenseType;
        }

        /*
        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }
        */

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}