package com.cetc.security.ddos.policy.test;

import com.cetc.security.ddos.persistence.service.NetNodeService;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangtao on 2015/7/23.
 */
public class NetNodeServiceTest {
    static final AbstractApplicationContext contextTest = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    NetNodeService nodeService = (NetNodeService)contextTest.getBean("netNodeService");

    @Test
    public void testGetAllNetNode() {
        //Assert.assertNotNull(nodeService.getNetNode(1, 1));
    }

    @Test
    public void testGetNetNodeById() {
        //Assert.assertNotNull(nodeService.getNetNode(1));
        //Assert.assertNotNull(nodeService.getNetNodeByControllerId(1));
    }
}
