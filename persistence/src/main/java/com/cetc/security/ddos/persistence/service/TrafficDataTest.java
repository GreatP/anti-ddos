package com.cetc.security.ddos.persistence.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cetc.security.ddos.persistence.TrafficInfoEntity;
import com.cetc.security.ddos.persistence.service.TrafficDataService;

public class TrafficDataTest {
	
	//private static FlowDataService flowData = new FlowDataService();
	private static TrafficDataService trafficDataService;
	
	/*
	static {
		try{
			TrafficData = new TrafficDataService();
		
		} catch (Exception ex){
			
		}
	}*/
	
	private static void loadApplicationContext(){
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		trafficDataService = (TrafficDataService) appContext.getBean("trafficDataService");
	}
	
	public static void main(String[] args) {

		loadApplicationContext();
		
		List<TrafficInfoEntity> list;

		System.out.println("ooxx");
		

		list = trafficDataService.getTrafficByTime(23,1, 1463036658000L, 1463036677800L);

		//String json = toJson(list);
		System.out.println("xxoo");
	}

}
