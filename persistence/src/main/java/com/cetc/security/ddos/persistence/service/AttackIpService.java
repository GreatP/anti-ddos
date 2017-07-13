package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.AttackIpEntity;
import com.cetc.security.ddos.persistence.dao.AttackIpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lb on 2016/5/17.
 */
@Service
public class AttackIpService {
    @Autowired
    private AttackIpDao attackIpDao;

    public List<AttackIpEntity> getAttackIpNotHandle () {
        return attackIpDao.findNotHandle();
    }

    public AttackIpEntity getAttackIpById(int id) {
        return  attackIpDao.findByid(id);
    }

    public List<AttackIpEntity> getAll () {
        return attackIpDao.findAll();
    }
    public void update (AttackIpEntity e) {
        attackIpDao.update(e);
    }

    public void addAttackIp(AttackIpEntity attackIpEntity) {
        attackIpDao.insert(attackIpEntity);
    }

    public void delAllAttackIp() {
        attackIpDao.deleteAll();
    }
}
