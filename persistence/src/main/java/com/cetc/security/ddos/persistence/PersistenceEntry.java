package com.cetc.security.ddos.persistence;

import com.cetc.security.ddos.persistence.service.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangtao on 2015/7/23.
 */
public class PersistenceEntry {
    private static PersistenceEntry instance = null;
    private static AbstractApplicationContext ctx;


    private PersistenceEntry() {
         ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static synchronized PersistenceEntry getInstance() {
        if (instance == null) {
            instance = new PersistenceEntry();
        }
        return instance;
    }

    public ControllerService getControllerService() {
        //return controllerService;
        return (ControllerService)ctx.getBean("controllerService");
    }

    public NetNodeService getNetNodeService() {
        return (NetNodeService)ctx.getBean("netNodeService");
    }

    public POService getPOService() {
        return (POService)ctx.getBean("poService");
    }

    public CleanDevService getCleanDevService() {
        return (CleanDevService)ctx.getBean("cleandevService");
    }

    //从spring been对象库中获取AutoLearnBaseValueDao对象
    public AutoLearnBaseValueService getAutoLearnBaseValueService() {
        return (AutoLearnBaseValueService)ctx.getBean("autoLearnBaseValueService");
    }

    public IpCityService getIpCityService() {
        return (IpCityService)ctx.getBean("ipCityService");
    }

    public AttackIpService getAttackIpService() {
        return (AttackIpService)ctx.getBean("attackIpService");
    }
    public UserService getUserService() {
        return (UserService)ctx.getBean("userService");
    }

    public FlowDataService getFlowDataService() {
        return (FlowDataService)ctx.getBean("flowDataService");
    }

    public InputTrafficInfoService getInputTrafficService() {
        return (InputTrafficInfoService)ctx.getBean("inputTrafficService");
    }

    public TrafficStatisticsService getTrafficContrastService() {
        return (TrafficStatisticsService)ctx.getBean("trafficStatisticsService");
    }

    public CleanDevFlowStatisticsService getCleanDevFlowStatService() {
        return (CleanDevFlowStatisticsService)ctx.getBean("cleanDevFlowStatisticsService");
    }

    public AttackDetailService getAttackDetailService() {
        return (AttackDetailService)ctx.getBean("attackDetailService");
    }

    public AttackDefenseService getAttackDefenseService() {
        return (AttackDefenseService)ctx.getBean("attackDefenseService");
    }

    public PoTrafficInfoService getPoTrafficService() {
        return (PoTrafficInfoService)ctx.getBean("poTrafficService");
    }

    public IpTrafficInfoService getIpTrafficService() {
        return (IpTrafficInfoService)ctx.getBean("ipTrafficService");
    }
	
	public TrafficDataService getTrafficDataService() {
    	return (TrafficDataService)ctx.getBean("trafficDataService");
    	
    }

    public FlowStatisticsService getFlowStatService() {
        return (FlowStatisticsService)ctx.getBean("flowStatisticsService");
    }

    public EventService getEventService() {
        return (EventService)ctx.getBean("eventService");
    }

}
