package com.cetc.security.ddos.policy.test;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.service.UserService;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by zhangtao on 2015/9/18.
 */
public class UserServerTest {
    UserService userService = PersistenceEntry.getInstance().getUserService();

    @Test
    public void testUserService() {
        //UserEntity userEntity = userService.getUser(13);
        //System.out.println(userEntity.getUsername());
        //Assert.assertNotNull(userEntity);
    }
}
