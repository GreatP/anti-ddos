package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.AttackDetailEntity;
import com.cetc.security.ddos.persistence.AttackTypeCount;
import com.cetc.security.ddos.persistence.dao.AttackDetailDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.List;

/**
 * Created by lb on 2016/5/30.
 */
public class ServiceApiTest {
    private static AttackDetailService attackDetailService;
    private static AttackDetailDao attackDetailDao;


    private static void loadApplicationContext(){
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        attackDetailService = (AttackDetailService) appContext.getBean("attackDetailService");
        attackDetailDao = (AttackDetailDao) appContext.getBean("attackDetailDao");
    }

    public static void main(String[] args) {
        loadApplicationContext();

        /*List<AttackTypeCount> attackTypeCounts = attackDetailService.getAttackTypeCount();
        for (AttackTypeCount object : attackTypeCounts) {
            System.out.println(object.getAttack_type() + ":" + object.getCount());
        }*/
        /*List<AttackDetailEntity> attackDetailEntities = attackDetailService.getByStatus(1);
        for (AttackDetailEntity object : attackDetailEntities) {
            System.out.println(object.getAttackip()+ "  " +object.getDuration() + "  " +object.getTotalBytes());
        }
        List<AttackDetailEntity> attackDetailEntities2 = attackDetailService.get("16.16.16.16",1,16);
        for (AttackDetailEntity object : attackDetailEntities2) {
            System.out.println(object.getAttackip()+ "  " +object.getDuration() + "  " +object.getTotalBytes());
            object.setDuration(800);
            object.setTotalBytes(1500);
            attackDetailService.update(object);
        }*/

        List<AttackDetailService.AttackSrcTop10> attackSrcTop10 = attackDetailService.getAttackSrcTop10();
        for (AttackDetailService.AttackSrcTop10 object : attackSrcTop10) {
            System.out.println(object.getIp()+ "  " +object.getNewPeak());
        }


       /* AttackDetailService.AttackTotalStat attackTotalStat = attackDetailService.getAttackTotalStat();
        System.out.println(attackTotalStat.getAttackCount());
        System.out.println(attackTotalStat.getDefenseCount());
        System.out.println(attackTotalStat.getCleanTraffic());
        System.out.println(attackTotalStat.getAttackSrcNum());*/
        //System.out.println(attackDetailDao.sumByProperty("totalBytes"));
    }

}
