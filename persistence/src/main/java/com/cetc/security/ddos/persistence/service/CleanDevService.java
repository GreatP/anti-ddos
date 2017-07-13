package com.cetc.security.ddos.persistence.service;

import java.util.List;

import com.cetc.security.ddos.persistence.*;
import com.cetc.security.ddos.persistence.dao.CleanDevDao;
import com.cetc.security.ddos.persistence.dao.FlowDao;
import com.cetc.security.ddos.persistence.dao.FlowStatisticsDao;
import com.cetc.security.ddos.persistence.dao.ProtectObjectDao;


import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hanqsheng on 2016/4/27.
 */
@Service("cleandevService")
public class CleanDevService {
    @Autowired
    private CleanDevDao cdDao;
    private static Logger logger = AntiLogger.getLogger(CleanDevService.class);

    private void flush() {
        cdDao.flush();
    }

    public void addCleanDev(CleanDevEntity cdEntity) {
        cdEntity.setFlag(OpType.OP_ADD.getValue());
        cdDao.insert(cdEntity);
    }

    public void updateCleanDev(CleanDevEntity cdEntity) {
        cdEntity.setFlag(OpType.OP_EDIT.getValue());

        cdDao.update(cdEntity);
    }

    public void delCleanDev(int id) {
        cdDao.delete(id);
    }

    public void setCleanDevFlag(CleanDevEntity cdEntity, short flag) {
        if (cdEntity == null) {
            return;
        }
        cdEntity.setFlag(flag);
        cdDao.updateNoTrans(cdEntity);
    }

    protected void cleanDevDel(CleanDevEntity cdEntity) {
        if (cdEntity == null) {
            return;
        }

        setCleanDevFlag(cdEntity, OpType.OP_DEL.getValue());
    }

    protected void cleanDevDel(int id) {
        CleanDevEntity cdEntity = getCleanDev(id);
        cleanDevDel(cdEntity);
    }

    @Transactional
    public void setCleanDevDelFlag(int id) {
        cleanDevDel(id);
    }

    @Transactional
    public void setCleanDevNormalFlag(CleanDevEntity cdEntity) {
        setCleanDevFlag(cdEntity, OpType.OP_NORMAL.getValue());
    }

    @Transactional
    public void setCleanDevDelFlag(List<Integer> ids) {
        for (int id : ids) {
            delCleanDev(id);
        }
    }

    public CleanDevEntity getPo(int id) {
        return cdDao.get(id);
    }

    @Transactional
    public void delCleanDev(List<Integer> ids) {

        for(int i:ids)
        {
            cdDao.delete(i);
        }
    }

    public List<CleanDevEntity> getAddCleanDev() {
        return cdDao.find(OpType.OP_ADD.getValue());
    }

    public List<CleanDevEntity> getEditCleanDev() {
        return cdDao.find(OpType.OP_EDIT.getValue());
    }

    public List<CleanDevEntity> getDelCleanDev() {
        return cdDao.find(OpType.OP_DEL.getValue());
    }


    public List<CleanDevEntity> getAllCleanDev() {
        return cdDao.findAll();
    }

    public List<CleanDevEntity> getCleanDev() {
        List<CleanDevEntity> cdEntities = cdDao.findNotEqualFlag(OpType.OP_DEL.getValue());
        return cdEntities;
    }

    public List<CleanDevEntity> getCleanDev(int start, int limit) {
        return cdDao.findOrderedDescByNotEqualFlag(OpType.OP_DEL.getValue(), start, limit);
    }

    public CleanDevEntity getCleanDev(int id) {
        CleanDevEntity cdEntity = cdDao.findById(id);
        return cdEntity;
    }

    public CleanDevEntity getCleanDevByIp(String ip) {
        CleanDevEntity cdEntity = cdDao.findByIp(ip);
        return cdEntity;
    }

    public long countCleanDev() {
        return cdDao.countByNotEqualFlag(OpType.OP_DEL.getValue());
    }

}