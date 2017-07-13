package com.cetc.security.ddos.persistence.service;


import com.cetc.security.ddos.persistence.AttackDefenseEntity;
import com.cetc.security.ddos.persistence.dao.AttackDefenseDao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AttackDefenseService {

	@Autowired
	private AttackDefenseDao attackDefenseDao;


	public List<AttackDefenseEntity> getAllAttackDefenseInfo() {
        return attackDefenseDao.findAll();
    }

	public List<AttackDefenseEntity> getAttackDefenseInfoByTime(int start_time, int end_time) {
        return attackDefenseDao.findOrderedDescBySaveTime(start_time, end_time);
    }

    public AttackDefenseEntity getAttackDefenseInfoById(int id) {
        return attackDefenseDao.findById(id);
    }

    public void addAttackDefense(AttackDefenseEntity attackDefenseEntity) {
        attackDefenseDao.insert(attackDefenseEntity);
    }

    public  void delAll(){
        attackDefenseDao.deleteAll();
    }

    public List<DetailAttackDefenseInfo> getAttackDefenseInfoRangeTime(int start_time, int end_time) {

        List<DetailAttackDefenseInfo> list = new ArrayList<DetailAttackDefenseInfo>();
        List<AttackDefenseEntity> attackDefenseEntities =  attackDefenseDao.getAllAttackDefenseInfo();
        if (attackDefenseEntities == null) {
            return  null;
        }

        for (AttackDefenseEntity f: attackDefenseEntities) {
            DetailAttackDefenseInfo detailAttackDefenseInfo = new DetailAttackDefenseInfo();
            detailAttackDefenseInfo.setMaxBps(f.getMaxBps());
            detailAttackDefenseInfo.setDefenseCount(f.getDefenseCount());
            detailAttackDefenseInfo.setSaveTime(f.getSaveTime());
            list.add(detailAttackDefenseInfo);
        }

        return  list;
    }

    public List<DetailAttackDefenseInfo> getAllAttackDefenseDetailInfo() {

        List<DetailAttackDefenseInfo> list = new ArrayList<DetailAttackDefenseInfo>();
        List<AttackDefenseEntity> attackDefenseEntities =  this.getAllAttackDefenseInfo();
        if (attackDefenseEntities == null) {
            return  null;
        }

        for (AttackDefenseEntity f: attackDefenseEntities) {
            DetailAttackDefenseInfo detailAttackDefenseInfo = new DetailAttackDefenseInfo();
            detailAttackDefenseInfo.setMaxBps(f.getMaxBps());
            detailAttackDefenseInfo.setDefenseCount(f.getDefenseCount());
            detailAttackDefenseInfo.setSaveTime(f.getSaveTime());
            list.add(detailAttackDefenseInfo);
        }

        return  list;
    }

    public class DetailAttackDefenseInfo {
        private long maxBps;
        private long defenseCount;
        private Date saveTime;


        public long getMaxBps() {
            return maxBps;
        }

        public void setMaxBps(long maxBps) {
            this.maxBps = maxBps;
        }

        public long getDefenseCount() {
            return defenseCount;
        }

        public void setDefenseCount(long defenseCount) {
            this.defenseCount = defenseCount;
        }

        public Date getSaveTime() {
            return saveTime;
        }

        public void setSaveTime(Date saveTime) {
            this.saveTime = saveTime;
        }
    }
}
