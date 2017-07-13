package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.AutoLearnBaseValueEntity;
import com.cetc.security.ddos.persistence.dao.AutoLearnBaseValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lingbo on 2015/9/25.
 */
@Service
public class AutoLearnBaseValueService {
    @Autowired
    private AutoLearnBaseValueDao autoLearnBaseValueDao;

    public List<AutoLearnBaseValueEntity> getBaseValue(int flowid) {
        return autoLearnBaseValueDao.findOrder(flowid);
    }

    public List<AutoLearnBaseValueEntity> getBaseValue(int flowid, int week) {
        return autoLearnBaseValueDao.findOrderOneday(flowid, week);
    }

    /*public List<AutoLearnBaseValueEntity> getBaseValue(String name, int protocol) {
        return autoLearnBaseValueDao.findOrder(name, protocol);
    }

    public List<AutoLearnBaseValueEntity> getBaseValue(String name, int protocol, int week) {
        return autoLearnBaseValueDao.findOrderOneday(name, protocol, week);
    }*/

    public AutoLearnBaseValueEntity getBaseValue(int flowid,int week,int hour) {
        return autoLearnBaseValueDao.find(flowid, week, hour);
    }

    public void addBaseValue(AutoLearnBaseValueEntity e) {
        autoLearnBaseValueDao.insert(e);
    }

    public void updateBaseValue(AutoLearnBaseValueEntity e) {
        autoLearnBaseValueDao.update(e);
    }

    public void delBaseValue(int flowid,int week,int hour) {
        autoLearnBaseValueDao.delOneBaseValue(flowid, week, hour);
    }
}
