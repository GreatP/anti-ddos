package com.cetc.security.ddos.statanalysis;

import com.cetc.security.ddos.persistence.AutoLearnBaseValueEntity;
import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.service.AutoLearnBaseValueService;

import java.util.Scanner;

/**
 * Created by lb on 2015/11/30.
 */
public class DeleteBaseValueTest {
    public static void delBaseValue(int flowid) {
        AutoLearnBaseValueService baseValueService = PersistenceEntry.getInstance().getAutoLearnBaseValueService();
        AutoLearnBaseValueEntity entity;
        for(int i=0; i < 7;i++) {
            for (int j=0; j < 24;j++) {
                baseValueService.delBaseValue(flowid, i, j);
            }
        }
    }

    public static void main(String[] args) {
        int flowid = 39;
        System.out.print("input delete flow id:");
        Scanner input = new Scanner(System.in);
        flowid = input.nextInt();
        //System.out.println("输入数据："+flowid);
        delBaseValue(flowid);
    }
}
