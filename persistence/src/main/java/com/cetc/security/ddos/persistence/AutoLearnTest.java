package com.cetc.security.ddos.persistence;

import com.cetc.security.ddos.persistence.service.AutoLearnBaseValueService;
import com.cetc.security.ddos.persistence.service.IpCityService;

import java.util.Random;

/**
 * Created by lb on 2015/8/13.
 */
public class AutoLearnTest {
    public static void addIpCity() {
        IpCityService cityService = PersistenceEntry.getInstance().getIpCityService();
        String city[] = {"成都","自贡","汕头","厦门","揭阳","苏州","温州","合肥","唐山","青岛",
                "拉萨","克拉玛依","乌鲁木齐","金昌","攀枝花","包头","昆明","宝鸡", "上海","北京",
                "三亚","长春","哈尔滨","深圳","大连","沈阳","苏州", "桂林","大庆"};
        for(int i=0;i<city.length;i++) {
            IpCityEntity e = new IpCityEntity();
            e.setCity(city[i]);
            e.setCount((int) (Math.random() * 500));
            cityService.addCity(e);
        }
    }
    public static void delIpCity() {
        IpCityService cityService = PersistenceEntry.getInstance().getIpCityService();
        cityService.delCityAll();
    }

    public static void main(String[] args) {

        delIpCity();
        addIpCity();

        /*Random ran=new Random();
        AutoLearnBaseValueService baseValueService = PersistenceEntry.getInstance().getAutoLearnBaseValueService();
        AutoLearnBaseValueEntity base;
        int protocal = 6;
        //插入表项
        for (int i=3;i<7;i++) {
            for (int j=0;j<24;j++) {
                base = new AutoLearnBaseValueEntity();
                base.setFlowid(500);
                base.setPoname("po1");
                base.setProtocal(protocal);
                base.setBps(String.valueOf(ran.nextDouble()));
                base.setPps(String.valueOf(ran.nextDouble()));
                base.setWeek(i);
                base.setHour(j);
                baseValueService.addBaseValue(base);

                base = new AutoLearnBaseValueEntity();
                base.setFlowid(600);
                base.setPoname("po2");
                base.setProtocal(protocal);
                base.setBps(String.valueOf(ran.nextDouble()));
                base.setPps(String.valueOf(ran.nextDouble()));
                base.setWeek(i);
                base.setHour(j);
                baseValueService.addBaseValue(base);
            }
        }
        for (int i=0;i<3;i++) {
            for (int j=0;j<24;j++) {
                base = new AutoLearnBaseValueEntity();
                base.setFlowid(500);
                base.setPoname("po1");
                base.setProtocal(protocal);
                base.setBps(String.valueOf(ran.nextDouble()));
                base.setPps(String.valueOf(ran.nextDouble()));
                base.setWeek(i);
                base.setHour(j);
                baseValueService.addBaseValue(base);

                base = new AutoLearnBaseValueEntity();
                base.setFlowid(600);
                base.setPoname("po2");
                base.setProtocal(protocal);
                base.setBps(String.valueOf(ran.nextDouble()));
                base.setPps(String.valueOf(ran.nextDouble()));
                base.setWeek(i);
                base.setHour(j);
                baseValueService.addBaseValue(base);
            }
        }*/
        //根据id查找表项
        /*base = baseValueDao.get(100);
        System.out.println(base.getWeek() + "," + base.getHour() +","+base.getPps());
        //更改表项
        base.setPps("0.68");
        baseValueDao.update(base);*/
        //base = baseValueService.getBaseValue("po1",protocal,5,6);
        //System.out.println(base.getWeek() + "," + base.getHour() +","+base.getPps());



        System.out.println("end");
    }
}
