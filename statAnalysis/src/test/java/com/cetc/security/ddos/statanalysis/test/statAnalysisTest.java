package com.cetc.security.ddos.statanalysis.test;

import org.junit.Test;

/**
 * Created by lb on 2015/6/18.
 */
public class statAnalysisTest {
    /* @Test
     public void testTx() {
         int cnt = 0;
         String pnkey = "po_cetc";
         String trafficFloorKey = "po_cetc_tcp";
         long readingTime;
         RateBasedDetectorImpl detector = new RateBasedDetectorImpl(20);
         TrafficTuple stats;
         StatReport statReport;
         while(cnt < 7) {
             readingTime = System.currentTimeMillis()/1000 + 10*cnt;
             stats = new TrafficTuple(100 + cnt*cnt*200,3+cnt,50+10*cnt);
             statReport = new StatReport(6,0,stats,readingTime,"po_cetc","po_cetc_tcp",null,0, ThresholdType.AUTO_LEARNING,0,0);
             detector.processStatReport(statReport);
             cnt++;
         }

         readingTime = System.currentTimeMillis()/1000 + 10*cnt;
         stats = new TrafficTuple(10000 + cnt*cnt*200,30+cnt,50+10*cnt);
         statReport = new StatReport(6,0,stats,readingTime,"po_cetc","po_cetc_tcp",null,0, ThresholdType.AUTO_LEARNING,0,0);
         detector.processStatReport(statReport);

         int i = 1;
         while(i < 5) {
             readingTime += 10*cnt;
             stats = new TrafficTuple(10000 + cnt*cnt*200+i,30+cnt+i,50+10*cnt+i*10);
             statReport = new StatReport(6,0,stats,readingTime,"po_cetc","po_cetc_tcp",null,0, ThresholdType.AUTO_LEARNING,0,0);
             detector.processStatReport(statReport);
             i++;
         }
     }*/
    /*@Test
     public void testSample() {
         int cnt = 60*24*7+60*3;
        int max = cnt;
         String pnkey = "po_cetc";
         String trafficFloorKey = "po_cetc_tcp";
         long readingTime;
         RateBasedDetectorImpl detector = new RateBasedDetectorImpl(20);
         Calendar cc = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
         TrafficTuple stats;
         StatReport statReport;
         long now = System.currentTimeMillis();
         while(cnt > 0) {

             readingTime =  now - 60*1000*cnt;
             cc.setTimeInMillis(readingTime);

             stats = new TrafficTuple(max*61-60*cnt,max+1-cnt,now/1000-60*cnt);
             statReport = new StatReport(6,0,stats,readingTime,"po_cetc","po_cetc_tcp",null,0, ThresholdType.AUTO_LEARNING,0,0);
             statReport.setWeekday(cc.get(Calendar.DAY_OF_WEEK)-1);
             statReport.setHour(cc.get(Calendar.HOUR_OF_DAY));
             detector.processStatReport(statReport);
             cnt--;
         }

     }*/
    @Test
    public void test() {
        float m = (float)Long.MAX_VALUE;
        double n = (double)Long.MAX_VALUE;
        System.out.println("haha"+Float.MAX_VALUE);

        float k = 888888888L;
        double xx = 8888888888888888L;
        System.out.printf("%f,m=%f,n=%f,%d,k=%f,xx=%f",Float.MAX_VALUE,m,n,Long.MAX_VALUE,k,xx);
    }
}
