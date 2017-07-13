package com.cetc.security.ddos.persistence.service;

import java.util.ArrayList;
import java.util.List;

import com.cetc.security.ddos.persistence.*;
import com.cetc.security.ddos.persistence.dao.FlowDao;
import com.cetc.security.ddos.persistence.dao.FlowStatisticsDao;
import com.cetc.security.ddos.persistence.dao.ProtectObjectDao;


import com.cetc.security.ddos.persistence.dao.UserPoDao;
import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangtao on 2015/7/23.
 */
@Service("poService")
public class POService {
    @Autowired
    private FlowDao flowDao;
    @Autowired
    private ProtectObjectDao poDao;
    @Autowired
    private FlowStatisticsDao flowStatisticsDao;
    @Autowired
    private UserPoDao userPoDao;
    private static Logger logger = AntiLogger.getLogger(POService.class);

    private void flush() {
    	
    	poDao.flush();
    	flowDao.flush();
    }

    private void addFlowStatistics(int id) {
        FlowStatisticsEntity flowStatisticsEntity = new FlowStatisticsEntity();
        FlowEntity flowEntity = new FlowEntity();
        flowEntity.setId(id);
        flowStatisticsEntity.setFlowEntity(flowEntity);
        flowStatisticsDao.insertNoTrans(flowStatisticsEntity);
    }
    
    @Transactional
    public void addPo(ProtectObjectEntity poEntity, List<FlowEntity> poFlows) {
    	if (poFlows == null)
    	{
    		return;
    	}

        poEntity.setFlag(OpType.OP_ADD.getValue());
    	poDao.insertNoTrans(poEntity);
    	for (FlowEntity flowEntity:poFlows)
    	{
    		flowEntity.setPoId(poEntity.getId());
    		flowEntity.setName("po"+poEntity.getId()+"protocol"+flowEntity.getProtocol());
    		flowDao.insertNoTrans(flowEntity);

            addFlowStatistics(flowEntity.getId());
    	}
    	
    	//flush();
    }

    @Transactional
    public void updatePo(ProtectObjectEntity poEntity, List<FlowEntity> poFlows) {
    	
    	List<FlowEntity> poExistFlows = getFlowByPoId(poEntity.getId());
    	int flag = 0;
    	int i=0;
    	FlowEntity fOld;
    	
    	for(FlowEntity fNew:poFlows)
    	{
    		flag = 0;
            if (poExistFlows != null) {
                for (i = 0; i < poExistFlows.size(); i++) {
                    fOld = poExistFlows.get(i);
                    if (fOld.getProtocol() == fNew.getProtocol()) {
                        fNew.setId(fOld.getId());
                        fNew.setPoId(poEntity.getId());
                        fNew.setName(fOld.getName());
                        flowDao.updateNoTrans(fNew);
                        flag = 1;
                        break;
                    }

                }
            }
    		
    		if(flag == 0)
    		{
    			fNew.setPoId(poEntity.getId());
    			fNew.setName("po"+poEntity.getId()+"protocol"+fNew.getProtocol());
        		flowDao.insertNoTrans(fNew);

                addFlowStatistics(fNew.getId());
    		}
    		else
    		{
    			poExistFlows.remove(i);
    		}
    	}

        if (poExistFlows != null) {
            for (i = 0; i < poExistFlows.size(); i++) {
                flowDao.deleteNoTrans(poExistFlows.get(i).getId());
            }
        }

        poEntity.setFlag(OpType.OP_EDIT.getValue());
    	poDao.updateNoTrans(poEntity);
    	
    	flush();
    }
    
    public void setPoFlag(ProtectObjectEntity poEntity, short flag) {
        if (poEntity == null) {
            return;
        }
        poEntity.setFlag(flag);
        poDao.updateNoTrans(poEntity);
    }

    protected void poDel(ProtectObjectEntity poEntity) {
        if (poEntity == null) {
            return;
        }

        setPoFlag(poEntity, OpType.OP_DEL.getValue());

        List<FlowEntity> flowList = flowDao.findByPoId(poEntity.getId());
        for (FlowEntity f : flowList) {
            flowStatisticsDao.delNoTransByFlowId(f.getId());
        }
        /* 删除用户对应的保护对象关联 */
        userPoDao.delByPoId(poEntity.getId());
    }

    protected void poDel(int id) {
        ProtectObjectEntity poEntity = getProtectObject(id);
        poDel(poEntity);
    }

    @Transactional
    public void setPoDelFlag(int id) {
        poDel(id);
    }

    @Transactional
    public void setPoNormalFlag(ProtectObjectEntity poEntity) {
        setPoFlag(poEntity, OpType.OP_NORMAL.getValue());
    }
    
    @Transactional
    public void setPoDelFlag(List<Integer> ids) {
        for (int id : ids) {
            poDel(id);
        }
    }
    
    public ProtectObjectEntity getPo(int id) {
        return poDao.get(id);
    }
    
    public void addPo(ProtectObjectEntity poEntity) {
        poDao.insert(poEntity);
    }

    @Transactional
    public void delPo(int id) {
    	flowDao.delFlowsByPoId(id);
    	poDao.delete(id);
    }
    
    @Transactional
    public void delPo(List<Integer> ids) {
    		
    	for(int i:ids)
    	{
    		flowDao.delFlowsByPoId(i);
    		poDao.delete(i);
    	}
    }
    
    public List<ProtectObjectEntity> getAddPO() {
       return poDao.find(OpType.OP_ADD.getValue());
    }

    public List<ProtectObjectEntity> getEditPO() {
        return poDao.find(OpType.OP_EDIT.getValue());
    }

    public List<ProtectObjectEntity> getDelPO() {
        return poDao.find(OpType.OP_DEL.getValue());
    }


    public List<ProtectObjectEntity> getAllPO() {
        return poDao.findAll();
    }
    
    public List<ProtectObjectEntity> getProtectObject() {
        List<ProtectObjectEntity> poEntities = poDao.findNotEqualFlag(OpType.OP_DEL.getValue());
        return poEntities;
    }
    
    public List<ProtectObjectEntity> getProtectObject(int start, int limit) {
        return poDao.findOrderedDescByNotEqualFlag(OpType.OP_DEL.getValue(), start, limit);
    }
    
    public ProtectObjectEntity getProtectObject(int id) {
    	ProtectObjectEntity poEntity = poDao.findById(id);
        return poEntity;
    }

    public ProtectObjectEntity getProtectObject(String name) {
        ProtectObjectEntity poEntity = poDao.findByName(name);
        return poEntity;
    }
    
    public ProtectObjectEntity getPOByNetWork(String ip) {
        ProtectObjectEntity poEntity = poDao.findByIp(ip);
        return poEntity;
    }
    

    public List<ProtectObjectEntity> getPOByControllerId(int id) {
        return poDao.findByControllerIdAndNotEqualFlag(id, OpType.OP_DEL.getValue());
    }

    public List<ProtectObjectEntity> getPOByCleanDevId(int id) {
        return poDao.findByCleanDevIdAndNotEqualFlag(id, OpType.OP_DEL.getValue());
    }

    public List<FlowEntity> getFlowByPoId(int id) {
        return flowDao.findByPoId(id);
    }
    
    public long countAllPo() {
        return poDao.countByNotEqualFlag(OpType.OP_DEL.getValue());
    }

    public int getFlowId(int id, short protocol) {
        FlowEntity flowEntity = flowDao.findByPoIdAndProtocol(id, protocol);
        if (flowEntity == null) {
            return -1;
        }
        return flowEntity.getId();
    }

    public List<ProtectObjectEntity> getPoByUserId(int userId, int start, int limit) {

        List<UserPoEntity> l  = userPoDao.findById(userId, start, limit);
        if (l == null) {
            return null;
        }

        List<ProtectObjectEntity> protectObjectEntities = new ArrayList<ProtectObjectEntity>();
        for (UserPoEntity up : l) {
            protectObjectEntities.add(up.getProtectObjectEntity());
        }

        return protectObjectEntities;
    }

    public List<ProtectObjectEntity> getPoByUserId(int userId) {
        List<UserPoEntity> l  = userPoDao.findById(userId);
        if (l == null) {
            return null;
        }

        List<ProtectObjectEntity> protectObjectEntities = new ArrayList<ProtectObjectEntity>();
        for (UserPoEntity up : l) {
            protectObjectEntities.add(up.getProtectObjectEntity());
        }

        return protectObjectEntities;
    }

    @Transactional
    public void addPoByUserId(int userId, ProtectObjectEntity poEntity, List<FlowEntity> poFlows) {
        addPo(poEntity, poFlows);
        UserPoEntity userPoEntity = new UserPoEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userPoEntity.setUserEntity(userEntity);
        userPoEntity.setProtectObjectEntity(poEntity);
        userPoDao.insertNoTrans(userPoEntity);
    }

    public UserEntity getUserById(int id) {
        UserPoEntity upe = userPoDao.findByPoId(id);

        return upe == null ? null:upe.getUserEntity();
    }

}
