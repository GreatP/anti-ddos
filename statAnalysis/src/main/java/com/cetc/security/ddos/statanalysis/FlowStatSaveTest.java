package com.cetc.security.ddos.statanalysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lb on 2015/8/26.
 */
public class FlowStatSaveTest {
    public static void main(String[] args) {
        /*String poname = "po1";
        int protocal = 17;
        FlowStatSave statSave=new FlowStatSave(poname,protocal);

        long i;//必须用long型，否则i*60*1000会出现翻转
        long now = System.currentTimeMillis();
        System.out.println("now:"+now);
        long tmp;
        for(i=7*1440;i>0;i--) {
            tmp = now-i*60*1000;
            statSave.latestRateSave(poname,protocal,10*i,i,tmp);
            System.out.println("i="+i+"now:"+now+" tmp "+tmp);
        }

        System.out.println("FlowStatSaveTest end");*/


        /*DecimalFormat df = new DecimalFormat( "0.0 ");//保留几位小数

        double d2 = 4.56789213232;
        String tmp = df.format(d2);
        System.out.println(tmp);*/

        class person {
            String name;
            int age;
            public person (String name,int age){
                this.age = age;
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }
        }

        List<person> personList = new ArrayList<person>();
        person person1 = new person("xiaoming",99);
        person person2 = new person("zhangbing",55);
        person person3 = new person("liuluoguo",19);

        personList.add(person1);
        personList.add(person2);
        personList.add(person3);

        for(person u : personList){
            System.out.println(u.getName());
        }

        Collections.sort(personList,new Comparator<person>(){
            public int compare(person arg0, person arg1) {
                int flag = arg0.getAge()-arg1.getAge();//根据age，升序排列。输出顺序为 19 55 99
                return flag;
            }
        });
        for(person u : personList){
            System.out.println(u.getAge());
        }

    }
}
