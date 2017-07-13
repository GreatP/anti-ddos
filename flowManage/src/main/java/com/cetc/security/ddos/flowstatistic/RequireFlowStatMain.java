package com.cetc.security.ddos.flowstatistic;

import com.cetc.security.ddos.controller.adapter.Controller;
import com.cetc.security.ddos.controller.adapter.FlowConfig;
import com.cetc.security.ddos.controller.adapter.TrafficTuple;
import com.cetc.security.ddos.controller.adapter.odlhelium.OdlHeliumController;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by hasen on 2015/6/26.
 */
public class RequireFlowStatMain {
    public static void main(String[] args) {

        //final String nodeId = "openflow:6790874762845576647";
        final String nodeId =  "openflow:10308186071821934023";
        final int tableId = 0;
        final int flowId = 1;

      //  NetNode netNode = new NetNode("aaa", "openflow:345052807169", 1, "OF");
        Controller contrl = new OdlHeliumController(1, "10.111.121.27", 8181, "admin", "admin");
       // contrl.init();

     //    netNode.createProtectLink("1","2");
     //    netNode.createProtectObject("bbb", "192.168.188.19/32");
     //   netNode.createNodeFlows(contrl);

        FlowConfig flowInfo = new FlowConfig();
        flowInfo.setTableId(tableId);
        flowInfo.setNodeSwId(nodeId);
        flowInfo.setFlowId(flowId);

        GregorianCalendar now = new GregorianCalendar();
        int month = now.get(Calendar.MONTH);
        int weekday = now.get(Calendar.DAY_OF_WEEK);
        int firstdayofweek = now.getFirstDayOfWeek();
        System.out.println(month);
        System.out.println(weekday);
        try {
            TrafficTuple trafficTuple = new TrafficTuple();
            RequireFlowStatistic flowStatistic = new RequireFlowStatistic(contrl);
            trafficTuple = flowStatistic.getFlowStats(flowInfo, trafficTuple);
            System.out.println("Flow id statistic: ");
            System.out.println("    ByteCount:" + trafficTuple.getBytes());
            System.out.println("    PacketCount:" + trafficTuple.getPackets());
            System.out.println("    duration:" + trafficTuple.getDuration());
        } catch (Throwable e) {
            System.out.println("Can't new RequireFlowStatistics");
        }
    }
}
