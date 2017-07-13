package com.cetc.security.ddos.policy.test;

import com.cetc.security.ddos.persistence.service.ControllerService;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangtao on 2015/7/23.
 */
public class ControllerServiceTest {
    static final AbstractApplicationContext contextTest = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    ControllerService controllerService = (ControllerService)contextTest.getBean("controllerService");

    @Test
    public void testGetUnLoadController() {
        //Assert.assertNotNull(controllerService.getAddController());
    }

    @Test
    public void testGetDDoSParam() {
        //Assert.assertNotNull(controllerService.getDDoSParam(1));
    }
}
