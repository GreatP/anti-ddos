package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.*;
import com.cetc.security.ddos.persistence.dao.AttackIpDao;
import com.cetc.security.ddos.persistence.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/18.
 */

public class DataDisplayDB {

    /*
      * 毫秒转化时分秒毫秒
      */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static  void addAttackDetailInfo() {
        AttackDetailService attackDetailService = PersistenceEntry.getInstance().getAttackDetailService();
        POService poService = PersistenceEntry.getInstance().getPOService();
        List<ProtectObjectEntity> protectObjectEntity = null;
        if (poService != null) {
            protectObjectEntity = poService.getAllPO();
        }

        if (protectObjectEntity.size() == 0) {
            System.out.println("There is not protect object!");
            return;
        }


        int status =0;
        int duration = 400;

        Random ran=new Random(200);
        String str = secToTime(duration);
        long now = System.currentTimeMillis();
        long tmp;

        for (int i =1;i<60; i++) {
            tmp = now - i * 3600 * 1000;
            Date startTime = new Date(tmp);
            AttackDetailEntity attackDetailEntity = new AttackDetailEntity();

            if (i % 2 == 0) {
                status = 1;
            } else {
                status = 2;
            }

            attackDetailEntity.setAttackip(i + "." + i + "." + i + "."+ i);
            attackDetailEntity.setDuration(duration);
            attackDetailEntity.setPeak(ran.nextInt(2000000000));
            attackDetailEntity.setStartTime(startTime);
            attackDetailEntity.setStatus(status);
            attackDetailEntity.setTotalBytes(ran.nextInt(200000000));
            attackDetailEntity.setTotalPkts(ran.nextInt(2000000000));
            if (i % 18 == 0) {
                attackDetailEntity.setAttackType(1);;
            } else {
                attackDetailEntity.setAttackType(i % 18);
            }

            if (i % 2 == 0) {
                attackDetailEntity.setPoId(protectObjectEntity.get(0).getId());
            } else {
                if (protectObjectEntity.size() >= 2) {
                    if (protectObjectEntity.size() == 2) {
                        attackDetailEntity.setPoId(protectObjectEntity.get(1).getId());
                    } else {
                        attackDetailEntity.setPoId(protectObjectEntity.get(2).getId());
                    }
                } else {
                    attackDetailEntity.setPoId(protectObjectEntity.get(protectObjectEntity.size() -1).getId());
                }
            }
            attackDetailService.addAttackDetail(attackDetailEntity);
        }

    }

    public static  void delAttackDetailInfo() {
        AttackDetailService attackDetailService = PersistenceEntry.getInstance().getAttackDetailService();
        attackDetailService.delAll();
    }

    public static void addCleanDevStats() {
        CleanDevFlowStatisticsService cds = PersistenceEntry.getInstance().getCleanDevFlowStatService();


        CleanDevFlowStatisticsEntity cdE = new CleanDevFlowStatisticsEntity();
        cdE.setDefenseCount(10);
        cdE.setAttackCount(10);
        cdE.setAttackSrcNum(100);
        cdE.setCleanTraffic(1000);
        cdE.setSynFloodCount(10);
        cdE.setAckFloodCount(20);
        cdE.setSynAckFloodCount(30);
        cdE.setFinRstFloodCount(30);
        cdE.setUdpFloodCount(40);
        cdE.setDnsFloodCount(20);
        cdE.setCcFloodCount(50);
        cdE.setIcmpFloodCount(15);

        cds.addCleanDevStats(cdE);
    }
    public static void addTrafficContrast() {
        TrafficStatisticsService trafficStatisticsService = PersistenceEntry.getInstance().getTrafficContrastService();

        for (int i = 1; i<= 60; i++) {
            Random ran=new Random(i);
            long now = System.currentTimeMillis() + ran.nextInt(20000);
            Date date = new Date(now);

            TrafficStatisticsEntity trafficStatisticsEntity = new TrafficStatisticsEntity();

            trafficStatisticsEntity.setIcmp_bps(ran.nextInt(200));
            trafficStatisticsEntity.setIcmp_pps(ran.nextInt(200));

            trafficStatisticsEntity.setUdp_bps(ran.nextInt(200));
            trafficStatisticsEntity.setUdp_pps(ran.nextInt(200));

            trafficStatisticsEntity.setTcp_pps(ran.nextInt(200));
            trafficStatisticsEntity.setTcp_bps(ran.nextInt(200));

            trafficStatisticsEntity.setOther_bps(ran.nextInt(200));
            trafficStatisticsEntity.setOther_pps(ran.nextInt(200));

            trafficStatisticsEntity.setInput_traffic_bps(ran.nextInt(200));
            trafficStatisticsEntity.setOutput_traffic_bps(ran.nextInt(200));

            trafficStatisticsEntity.setInput_traffic_pps(ran.nextInt(200));
            trafficStatisticsEntity.setOutput_traffic_pps(ran.nextInt(200));

            trafficStatisticsEntity.setAttack_traffic_bps(ran.nextInt(200));
            trafficStatisticsEntity.setAttack_traffic_pps(ran.nextInt(200));

            trafficStatisticsEntity.setSave_time(date);

            trafficStatisticsService.addTrafficStatistic(trafficStatisticsEntity);

        }
    }

    public static void delTrafficContrastAll() {
        TrafficStatisticsService trafficStatisticsService = PersistenceEntry.getInstance().getTrafficContrastService();
        trafficStatisticsService.delAll();
    }

    public static void addInputTraffic() {
        InputTrafficInfoService inputTrafficService = PersistenceEntry.getInstance().getInputTrafficService();

        InputTrafficInfoEntity inputTrafficinfoEntity = new InputTrafficInfoEntity();
        inputTrafficinfoEntity.setProtocol(1);
        Random ran=new Random(2000000);
        inputTrafficinfoEntity.setRate_bps(ran.nextInt(20000000));
        inputTrafficinfoEntity.setRate_pps(ran.nextInt(200));
        inputTrafficService.addInputTrafficInfo(inputTrafficinfoEntity);

        InputTrafficInfoEntity inputTrafficinfoEntity1 = new InputTrafficInfoEntity();
        inputTrafficinfoEntity1.setProtocol(6);
        inputTrafficinfoEntity1.setRate_bps(ran.nextInt(20000000));
        inputTrafficinfoEntity1.setRate_pps(ran.nextInt(200));
        inputTrafficService.addInputTrafficInfo(inputTrafficinfoEntity1);

        InputTrafficInfoEntity inputTrafficinfoEntity2 = new InputTrafficInfoEntity();
        inputTrafficinfoEntity2.setProtocol(17);
        inputTrafficinfoEntity2.setRate_bps(ran.nextInt(20000000));
        inputTrafficinfoEntity2.setRate_pps(ran.nextInt(200));
        inputTrafficService.addInputTrafficInfo(inputTrafficinfoEntity2);

        InputTrafficInfoEntity inputTrafficinfoEntity3 = new InputTrafficInfoEntity();
        inputTrafficinfoEntity3.setProtocol(0);
        inputTrafficinfoEntity3.setRate_bps(ran.nextInt(20000000));
        inputTrafficinfoEntity3.setRate_pps(ran.nextInt(200));
        inputTrafficService.addInputTrafficInfo(inputTrafficinfoEntity3);

    }

    public static void delInputTrafficAll() {
        InputTrafficInfoService inputTrafficService = PersistenceEntry.getInstance().getInputTrafficService();
        inputTrafficService.delAll();
    }

    public static void addAttackDefenseInfo() {
        AttackDefenseService attackDefenseServicee = PersistenceEntry.getInstance().getAttackDefenseService();
        for (int i = 1;i <=20;i++) {
            Random ran=new Random(20000);
            long time = System.currentTimeMillis();
            Date date = new Date(time+ ran.nextInt(20000));
            AttackDefenseEntity attackDefenseEntity = new AttackDefenseEntity();
            attackDefenseEntity.setDefenseCount(ran.nextLong());
            attackDefenseEntity.setMaxBps(ran.nextInt(200));
            attackDefenseEntity.setSaveTime(date);
            attackDefenseServicee.addAttackDefense(attackDefenseEntity);
        }
    }

    public static void addPoTraffic() {
        PoTrafficInfoService poTrafficService = PersistenceEntry.getInstance().getPoTrafficService();
        POService poService = PersistenceEntry.getInstance().getPOService();
        CleanDevEntity cleanDevEntity = new CleanDevEntity();
        CleanDevService cleanDevService = PersistenceEntry.getInstance().getCleanDevService();
        List<CleanDevEntity> cleanDevEntities =  cleanDevService.getCleanDev();
        cleanDevEntity = cleanDevEntities.get(0);
        /*List<ProtectObjectEntity> protectObjectEntities = null;
        if (poService != null) {
            protectObjectEntities = poService.getAllPO();
        }

        if (protectObjectEntities.size() == 0) {
            System.out.println("There is not protect object!");
            return;
        }*/

        for (int i = 1;i <=10; i++) {
            Random ran=new Random(20000*i);
            ProtectObjectEntity protectObjectEntity = new ProtectObjectEntity();
            protectObjectEntity.setName("po" + i);
           // protectObjectEntity.setId(i);
            protectObjectEntity.setIpType((short) 0);
            protectObjectEntity.setDefenseType(DefenseType.LIMIT_RATE);
            protectObjectEntity.setFlag((short) 0);
            protectObjectEntity.setInPort("1");
            protectObjectEntity.setOutPort("2");
            protectObjectEntity.setLearnStatus((short) 0);
            protectObjectEntity.setCleanInport("5");
            protectObjectEntity.setCleanOutport("1");
            protectObjectEntity.setControllerId(7);
            protectObjectEntity.setHttpSrcAuth(false);
            protectObjectEntity.setHttp(1000);
            protectObjectEntity.setIcmp(100);
            protectObjectEntity.setIpOption(10);
            protectObjectEntity.setCheckInterval(60);
            protectObjectEntity.setDns_port(53);
            protectObjectEntity.setUdp(1000);
            protectObjectEntity.setTcpSynAck(10);
            protectObjectEntity.setSnmpPort(161);
            protectObjectEntity.setNtpPort(151);
            protectObjectEntity.setIcmpRedirect(true);
            protectObjectEntity.setHttps_thc(1000);
            protectObjectEntity.setHttps(1000);
            protectObjectEntity.setNtp(100);
            protectObjectEntity.setSnmp(100);
            protectObjectEntity.setNetWork(i + "." + i + "." + i + "." + i + "/32");
            protectObjectEntity.setCleanDevEntity(cleanDevEntity);
            poService.addPo(protectObjectEntity);

            PoTrafficInfoEntity poTrafficInfoEntity = new PoTrafficInfoEntity();
            poTrafficInfoEntity.setFlowrate_pps(ran.nextInt(20000 * i));
            poTrafficInfoEntity.setFlowrate_bps(ran.nextInt(20000*i));
            poTrafficInfoEntity.setPo_id(poService.getAllPO().get(i-1).getId());
            poTrafficService.addPoTrafficInfo(poTrafficInfoEntity);
        }
    }

    public static void delPoTrafficAll() {
        POService poService = PersistenceEntry.getInstance().getPOService();
        PoTrafficInfoService poTrafficService = PersistenceEntry.getInstance().getPoTrafficService();
        poTrafficService.delAll();
    }
    public static void addIpTraffic() {
        IpTrafficInfoService ipTrafficService=PersistenceEntry.getInstance().getIpTrafficService();

        for (int i=1;i<=20;i++) {
            Random ran=new Random(200);
            IpTrafficInfoEntity ipFlowEntity = new IpTrafficInfoEntity();
            ipFlowEntity.setFlowrate_bps(ran.nextInt(200));
            ipFlowEntity.setFlowrate_pps(ran.nextInt(200));
            ipFlowEntity.setIp(i+"."+i+"."+i+"."+i);
            ipTrafficService.addIpFlow(ipFlowEntity);
        }
    }

    public static void addAttackIp() {
        AttackIpService attackIpService = PersistenceEntry.getInstance().getAttackIpService();

        for (int i=1;i<60;i++) {
            AttackIpEntity attackIpEntity = new AttackIpEntity();
            attackIpEntity.setIp(i+"."+i+"."+i+"."+i);
            attackIpService.addAttackIp(attackIpEntity);
        }
    }

    public static void delAttackIp() {
        AttackIpService attackIpService = PersistenceEntry.getInstance().getAttackIpService();

       attackIpService.delAllAttackIp();

    }

    public static void delIpTrafficAll() {
        IpTrafficInfoService ipTrafficService=PersistenceEntry.getInstance().getIpTrafficService();
        ipTrafficService.delAll();
    }

    //private static FlowDataService flowData = new FlowDataService();
    private static TrafficDataService trafficDataService;

    private static void loadApplicationContext(){
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        trafficDataService = (TrafficDataService) appContext.getBean("trafficDataService");
    }


    public static void addTrafficData_hour(int po_id) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);



        int hour=2;//添加2小时的数据,一天60个点
        for (int i=15*hour;i>0;i-=2) {
            TrafficInfoEntity trafficEntity = new TrafficInfoEntity();
            if (i % 3 == 0) {
                trafficEntity.setType(1);
            } else if (i % 3 == 1) {
                trafficEntity.setType(2);
            } else if (i % 3 == 2) {
                trafficEntity.setType(2);
            }

            trafficEntity.setBps_tcp((ran.nextInt(10)+10)*10000L);
            trafficEntity.setBps_udp((ran.nextInt(10)+10)*10000L);
            trafficEntity.setBps_icmp((ran.nextInt(10)+10)*10000L);
            trafficEntity.setBps_other((ran.nextInt(10)+10)*10000L);
            trafficEntity.setBps_all(trafficEntity.getBps_icmp()+trafficEntity.getBps_tcp()+trafficEntity.getBps_udp()+trafficEntity.getBps_other());

            trafficEntity.setPps_other(ran.nextInt(10)+10);
            trafficEntity.setPps_icmp(ran.nextInt(10)+10);
            trafficEntity.setPps_udp(ran.nextInt(10)+10);
            trafficEntity.setPps_tcp(ran.nextInt(10)+10);
            trafficEntity.setPps_all(trafficEntity.getPps_icmp()+trafficEntity.getPps_tcp()+trafficEntity.getPps_udp()+trafficEntity.getPps_other());

            trafficEntity.setOutput_bps(ran.nextInt(10)*10000L);
            trafficEntity.setOutput_pps(ran.nextInt(10));

            trafficEntity.setAttack_bps(trafficEntity.getBps_all()-trafficEntity.getOutput_bps());
            trafficEntity.setAttack_pps(trafficEntity.getPps_all()-trafficEntity.getOutput_pps());


            trafficEntity.setTime(now-i*60*1000);
            trafficEntity.setPo_id(po_id);

            trafficDataService.addTrafficNode(trafficEntity);

        }
    }

    public static void addTrafficData_day(int po_id) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);



        int day=2;//添加2小时的数据,一天48个点
        for (int i=3*day;i>0;i--) {
            TrafficInfoEntity trafficEntity = new TrafficInfoEntity();
            trafficEntity.setType(2);
            trafficEntity.setBps_tcp((ran.nextInt(1000000)+10)*10000L);
            trafficEntity.setBps_udp((ran.nextInt(10000000)+10)*10000L);
            trafficEntity.setBps_icmp((ran.nextInt(10000000)+10)*10000L);
            trafficEntity.setBps_other((ran.nextInt(10000000)+10)*10000L);
            trafficEntity.setBps_all(trafficEntity.getBps_icmp()+trafficEntity.getBps_tcp()+trafficEntity.getBps_udp()+trafficEntity.getBps_other());

            trafficEntity.setPps_other(ran.nextInt(10)+10);
            trafficEntity.setPps_icmp(ran.nextInt(10)+10);
            trafficEntity.setPps_udp(ran.nextInt(10)+10);
            trafficEntity.setPps_tcp(ran.nextInt(10)+10);
            trafficEntity.setPps_all(trafficEntity.getPps_icmp()+trafficEntity.getPps_tcp()+trafficEntity.getPps_udp()+trafficEntity.getPps_other());

            trafficEntity.setOutput_bps(ran.nextInt(10)*10000L);
            trafficEntity.setOutput_pps(ran.nextInt(10));

            trafficEntity.setAttack_bps(trafficEntity.getBps_all()-trafficEntity.getOutput_bps());
            trafficEntity.setAttack_pps(trafficEntity.getPps_all()-trafficEntity.getOutput_pps());

            trafficEntity.setTime(now-i*1800*1000);
            trafficEntity.setPo_id(po_id);

            trafficDataService.addTrafficNode(trafficEntity);

        }
    }

    public static void addTrafficData_week(int po_id) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);



        int week=2;//添加2小时的数据
        for (int i=21*week;i>0;i--) {
            TrafficInfoEntity trafficEntity = new TrafficInfoEntity();
            trafficEntity.setType(3);
            trafficEntity.setBps_tcp((ran.nextInt(10)+10)*100000L);
            trafficEntity.setBps_udp((ran.nextInt(10)+10)*100000L);
            trafficEntity.setBps_icmp((ran.nextInt(10)+10)*100000L);
            trafficEntity.setBps_other((ran.nextInt(10)+10)*100000L);
            trafficEntity.setBps_all(trafficEntity.getBps_icmp()+trafficEntity.getBps_tcp()+trafficEntity.getBps_udp()+trafficEntity.getBps_other());

            trafficEntity.setPps_other(ran.nextInt(10)+10);
            trafficEntity.setPps_icmp(ran.nextInt(10)+10);
            trafficEntity.setPps_udp(ran.nextInt(10)+10);
            trafficEntity.setPps_tcp(ran.nextInt(10)+10);
            trafficEntity.setPps_all(trafficEntity.getPps_icmp()+trafficEntity.getPps_tcp()+trafficEntity.getPps_udp()+trafficEntity.getPps_other());

            trafficEntity.setOutput_bps(ran.nextInt(10)*100000L);
            trafficEntity.setOutput_pps(ran.nextInt(10));

            trafficEntity.setAttack_bps(trafficEntity.getBps_all()-trafficEntity.getOutput_bps());
            trafficEntity.setAttack_pps(trafficEntity.getPps_all()-trafficEntity.getOutput_pps());

            trafficEntity.setTime(now-i*4*3600*1000L);
            trafficEntity.setPo_id(po_id);

            trafficDataService.addTrafficNode(trafficEntity);

        }
    }

    public static void addTrafficData_month(int po_id) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);

        int m=2;//添加2小时的数据
        for (int i=30*m;i>0;i--) {
            TrafficInfoEntity trafficEntity = new TrafficInfoEntity();
            trafficEntity.setType(4);
            trafficEntity.setBps_tcp((ran.nextInt(10)+10)*300000L);
            trafficEntity.setBps_udp((ran.nextInt(10)+10)*300000L);
            trafficEntity.setBps_icmp((ran.nextInt(10)+10)*300000L);
            trafficEntity.setBps_other((ran.nextInt(10)+10)*300000L);
            trafficEntity.setBps_all(trafficEntity.getBps_icmp()+trafficEntity.getBps_tcp()+trafficEntity.getBps_udp()+trafficEntity.getBps_other());

            trafficEntity.setPps_other(ran.nextInt(10)+10);
            trafficEntity.setPps_icmp(ran.nextInt(10)+10);
            trafficEntity.setPps_udp(ran.nextInt(10)+10);
            trafficEntity.setPps_tcp(ran.nextInt(10)+10);
            trafficEntity.setPps_all(trafficEntity.getPps_icmp()+trafficEntity.getPps_tcp()+trafficEntity.getPps_udp()+trafficEntity.getPps_other());

            trafficEntity.setOutput_bps(ran.nextInt(10)*300000L);
            trafficEntity.setOutput_pps(ran.nextInt(10));

            trafficEntity.setAttack_bps(trafficEntity.getBps_all()-trafficEntity.getOutput_bps());
            trafficEntity.setAttack_pps(trafficEntity.getPps_all()-trafficEntity.getOutput_pps());

            trafficEntity.setTime(now-i*24*3600*1000L);
            trafficEntity.setPo_id(po_id);

            trafficDataService.addTrafficNode(trafficEntity);

        }
    }

    public static void addTrafficData_year(int po_id) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);

        for (int i=52;i>0;i--) {
            TrafficInfoEntity trafficEntity = new TrafficInfoEntity();
            trafficEntity.setType(5);
            trafficEntity.setBps_tcp((ran.nextInt(10)+10)*1000000L);
            trafficEntity.setBps_udp((ran.nextInt(10)+10)*1000000L);
            trafficEntity.setBps_icmp((ran.nextInt(10)+10)*1000000L);
            trafficEntity.setBps_other((ran.nextInt(10)+10)*1000000L);
            trafficEntity.setBps_all(trafficEntity.getBps_icmp()+trafficEntity.getBps_tcp()+trafficEntity.getBps_udp()+trafficEntity.getBps_other());

            trafficEntity.setPps_other(ran.nextInt(10)+10);
            trafficEntity.setPps_icmp(ran.nextInt(10)+10);
            trafficEntity.setPps_udp(ran.nextInt(10)+10);
            trafficEntity.setPps_tcp(ran.nextInt(10)+10);
            trafficEntity.setPps_all(trafficEntity.getPps_icmp()+trafficEntity.getPps_tcp()+trafficEntity.getPps_udp()+trafficEntity.getPps_other());

            trafficEntity.setOutput_bps(ran.nextInt(10)*1000000L);
            trafficEntity.setOutput_pps(ran.nextInt(10));

            trafficEntity.setAttack_bps(trafficEntity.getBps_all()-trafficEntity.getOutput_bps());
            trafficEntity.setAttack_pps(trafficEntity.getPps_all()-trafficEntity.getOutput_pps());

            trafficEntity.setTime(now-i*7*24*3600*1000L);
            trafficEntity.setPo_id(po_id);

            trafficDataService.addTrafficNode(trafficEntity);

        }
    }

    public static void delDataByType(int type){

        trafficDataService.delTrafficByType(type);
        return;
    }

    public static void main(String[] args) {
        POService poService = PersistenceEntry.getInstance().getPOService();
        List<ProtectObjectEntity> protectObjectEntity = null;
        if (poService != null) {
            protectObjectEntity = poService.getAllPO();
            for (int i =0;i<protectObjectEntity.size();i++) {
                poService.delPo(protectObjectEntity.get(i).getId());
            }
        }

        loadApplicationContext();

        delAttackIp();
        addAttackIp();
        delTrafficContrastAll();
        addTrafficContrast();
        delPoTrafficAll();
        addPoTraffic();
        delInputTrafficAll();
        addInputTraffic();
        delAttackDetailInfo();
        addAttackDetailInfo();

        POService poService2 = PersistenceEntry.getInstance().getPOService();
        List<ProtectObjectEntity> protectObjectEntity2 = null;

        if (poService2 != null) {
            protectObjectEntity2 = poService2.getAllPO();
        }

        if (protectObjectEntity2.size() == 0) {
            System.out.println("There is not protect object!");
            return;
        }

        for (int i = 1;i<=5;i++) {
            delDataByType(i);
        }
        for (int i = 0; i < protectObjectEntity2.size();i++) {

            addTrafficData_hour(protectObjectEntity2.get(i).getId());

            addTrafficData_day(protectObjectEntity2.get(i).getId());

            addTrafficData_week(protectObjectEntity2.get(i).getId());

            addTrafficData_month(protectObjectEntity2.get(i).getId());

            addTrafficData_year(protectObjectEntity2.get(i).getId());
        }
/*
        delTrafficContrastAll();
        addTrafficContrast();
        delPoTrafficAll();
        addPoTraffic();
        delInputTrafficAll();
        addInputTraffic();
        delAttackDetailInfo();
        addAttackDetailInfo();
*/
        //addAttackDefenseInfo();
       // addCleanDevStats();
      //  addAttackDetailInfo();

//        delInputTrafficAll();
//        addInputTraffic();
//

//
//        delIpTrafficAll();
//        addIpTraffic();

     /*   int i = 3;
        int k = 5;
        int j = 4;

        System.out.print(i/2.0 + "\n");
        System.out.print(1/2+ "\n");
        System.out.print(k/2+ "\n");
        System.out.print(j/2.0+ "\n");

        double ans = 3/4;

        System.out.println("The answer is " + ans);*/
    }
}
