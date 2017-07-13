package com.cetc.security.ddos.policy.test;

import com.cetc.security.ddos.persistence.PersistenceEntry;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhangtao on 2015/7/23.
 */
public class PersistenceEntryTest {
    PersistenceEntry pe = PersistenceEntry.getInstance();

    @Test
    public void testGetPolicyEntry() {
        Assert.assertNotNull(pe);
    }

    @Test
    public void testGetControllerService() {
        Assert.assertNotNull(pe.getControllerService());
    }

    @Test
    public void testGetNetNodeService() {
        Assert.assertNotNull(pe.getNetNodeService());
    }

    @Test
    public void testGetPOService() {
        Assert.assertNotNull(pe.getPOService());
    }

}
