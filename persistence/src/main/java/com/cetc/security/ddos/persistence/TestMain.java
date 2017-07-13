package com.cetc.security.ddos.persistence;

import com.cetc.security.ddos.persistence.service.EventService;
import com.cetc.security.ddos.persistence.service.FlowStatisticsService;
import com.cetc.security.ddos.persistence.service.POService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangtao on 2015/7/21.
 */
//@Service
public class TestMain {


    /*public void xx() {
        db.add();
    }
    */

    public static void main(String[] args) {
        //ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        //AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //TestDb dg = (TestDb)context.getBean("testDb");
        //dg.add();
        //f.db = (TestDb)ctx.getBean("TestDb");
        //f.xx();
        FlowStatisticsService fs = PersistenceEntry.getInstance().getFlowStatService();
        EventService es = PersistenceEntry.getInstance().getEventService();
        POService ps = PersistenceEntry.getInstance().getPOService();

        ps.setPoDelFlag(101);


        Integer i = new Integer(6);
            int j = 0;
        //int a = fs.getTotalStat(i, j, j, j);
        //fs.updateStat(187, 3, 4, 5, 6);
        //List<FlowStatisticsEntity> l = fs.getStat(0, 10);
        /*
        EventEntity eventEntity = new EventEntity();
        try {
            es.addEvent(172, eventEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        //es.getEvent(0, 20);
        System.out.println(i);
    }
}
