package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.FlowDataService;

import java.util.Random;

/**
 * Created by lb on 2015/8/17.
 */
public class FlowDataAddTest {
    private static FlowDataService flowData;

    public static void addFlowData_hour(String poname,int protocal) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);

        int hour=2;//添加2小时的数据
        for (int i=60*hour;i>0;i--) {
            flowData.addFlowData(poname,protocal,"hour",now-i*60*1000,ran.nextDouble(),ran.nextDouble()*10000);
        }
    }

    public static  void addFlowData_day(String poname,int protocal) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);

        int day=2;//添加2天的数据
        for (int i=48*day;i>0;i--) {
            flowData.addFlowData(poname,protocal,"day",now-i*1800*1000,ran.nextDouble(),ran.nextDouble()*10000);
        }
    }

    public static  void addFlowData_week(String poname,int protocal) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);

        int week=2;//添加2周的数据
        for (int i=42*week;i>0;i--) {
            flowData.addFlowData(poname,protocal,"week",now-i*4*3600*1000L,ran.nextDouble(),ran.nextDouble()*10000);
        }
    }

    public static  void addFlowData_month(String poname,int protocal) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);

        int m=2;//添加2月的数据
        for (int i=30*m;i>0;i--) {
            flowData.addFlowData(poname,protocal,"month",now-i*24*3600*1000L,ran.nextDouble(),ran.nextDouble()*10000);
        }
    }

    public static  void addFlowData_year(String poname,int protocal) {
        long now = System.currentTimeMillis();//System.currentTimeMillis()单位是毫秒，1秒等于1000毫秒
        Random ran=new Random();
        System.out.println("now:"+now);

        for (int i=52;i>0;i--) {
            flowData.addFlowData(poname,protocal,"year",now-i*7*24*3600*1000L,ran.nextDouble(),ran.nextDouble()*10000);
        }
    }
    public static void main(String[] args) {
        flowData = PersistenceEntry.getInstance().getFlowDataService();
        System.out.println("1+1");

        String poname="anti";
        int flowid = 10;
        //addFlowData_hour(poname,flowid);

        /*addFlowData_day(poname,flowid);
        addFlowData_week(poname, flowid);

        addFlowData_month(poname, flowid);
        addFlowData_year(poname, flowid);*/
        System.out.println("FlowDataAddTest end");
    }
}
