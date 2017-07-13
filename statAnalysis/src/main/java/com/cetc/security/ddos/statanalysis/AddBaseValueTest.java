package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.persistence.AutoLearnBaseValueEntity;
import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.AutoLearnBaseValueService;

import java.util.Scanner;

/**
 * Created by lb on 2015/11/30.
 */
public class AddBaseValueTest {
    public static void addBaseValue(int flowid,String PPS ,String BPS,String poname,int protocol) {
        AutoLearnBaseValueService baseValueService = PersistenceEntry.getInstance().getAutoLearnBaseValueService();
        AutoLearnBaseValueEntity entity = null;

        entity = baseValueService.getBaseValue(flowid, 5, 5);
        AutoLearnBaseValueEntity baseValueEntity;

        if(entity == null) {//添加
            for(int i=0; i < 7;i++) {
                for (int j=0; j < 24;j++) {
                    baseValueEntity = new AutoLearnBaseValueEntity();
                    baseValueEntity.setPoname(poname);
                    baseValueEntity.setProtocal(protocol);
                    baseValueEntity.setFlowid(flowid);
                    baseValueEntity.setWeek(i);
                    baseValueEntity.setHour(j);

                    baseValueEntity.setBps(BPS);
                    baseValueEntity.setPps(PPS);

                    baseValueService.addBaseValue(baseValueEntity);
                }
            }
        } else {//更新
            for(int i=0; i < 7;i++) {
                for (int j=0; j < 24;j++) {
                    baseValueEntity = baseValueService.getBaseValue(flowid, i, j);
                    baseValueEntity.setBps(BPS);
                    baseValueEntity.setPps(PPS);
                    baseValueService.updateBaseValue(baseValueEntity);
                }
            }
        }
    }

    public static void main(String[] args) {
        String poname = "poxxx";
        int protocol = 17;
        int flowid = 39;
        System.out.print("input flow id:");
        Scanner input = new Scanner(System.in);
        flowid = input.nextInt();
        //System.out.println("输入数据："+flowid);

        System.out.print("input baseValue PPS:");
        input = new Scanner(System.in);
        String PPS = input.nextLine();
        //System.out.println("PPS："+PPS);

        System.out.print("input baseValue BPS:");
        input = new Scanner(System.in);
        String BPS = input.nextLine();
        //System.out.println("BPS："+BPS);

       addBaseValue(flowid,PPS,BPS,poname,protocol);
    }
}
