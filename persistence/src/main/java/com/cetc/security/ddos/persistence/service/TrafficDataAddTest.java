package com.cetc.security.ddos.persistence.service;

import java.util.List;
import java.util.Random;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cetc.security.ddos.persistence.TrafficInfoEntity;
import com.cetc.security.ddos.persistence.service.TrafficDataService;

public class TrafficDataAddTest {
	
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
        for (int i=30*hour;i>0;i-=2) {
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
        for (int i=42*week;i>0;i--) {
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

		loadApplicationContext();
		
		//List<TrafficInfoEntity> list;

		System.out.println("ooxx");
		//list = trafficDataService.getTrafficByTime(23,1, 1463036658000L, 1463036677800L);
		
		//list = trafficDataService.getTrafficByBpsOrder(23, 1, 1463138100000L, 1463138350000L, 5);

       // delDataByType(1);
        POService poService = PersistenceEntry.getInstance().getPOService();
        List<ProtectObjectEntity> protectObjectEntity = null;
        if (poService != null) {
            protectObjectEntity = poService.getAllPO();
        }

        if (protectObjectEntity.size() == 0) {
            System.out.println("There is not protect object!");
            return;
        }

        for (int i = 1;i<=5;i++) {
            delDataByType(i);
        }
        for(int i=0;i< protectObjectEntity.size();i++) {

            addTrafficData_hour(protectObjectEntity.get(i).getId());

            addTrafficData_day(protectObjectEntity.get(i).getId());

            addTrafficData_week(protectObjectEntity.get(i).getId());

            addTrafficData_month(protectObjectEntity.get(i).getId());

            addTrafficData_year(protectObjectEntity.get(i).getId());
        }
		//delDataByType(1);
		//addTrafficData_day(23);
		//delDataByType(2);
		//addTrafficData_week(23);
		//delDataByType(3);
		//addTrafficData_month(23);
		//delDataByType(4);
		//addTrafficData_year(23);
		//delDataByType(5);
		

		System.out.println("xxoo");
	}

}
