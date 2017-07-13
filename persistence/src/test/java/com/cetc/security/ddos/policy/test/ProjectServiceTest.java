package com.cetc.security.ddos.policy.test;

import com.cetc.security.ddos.persistence.service.POService;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangtao on 2015/7/23.
 */
public class ProjectServiceTest {
    static final AbstractApplicationContext contextTest = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    POService poService = (POService)contextTest.getBean("poService");

    @Test
    public void testGetAllPO() {
        //Assert.assertNotNull(poService.getAllPO());
    }
}
