package com.cetc.security.ddos.persistence.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangtao on 2016/6/20.
 */
public class UserPoTest {
    static UserService  poService;

    private static void loadApplicationContext(){
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        poService = (UserService) appContext.getBean("userService");
    }

    public static void main(String[] args) {
        loadApplicationContext();
        //poService.getPoByUserId(1, 1, 10);
        //poService.getUserCleanDevById(14);
        //poService.getUserCleanDevById(14);
    }
}
