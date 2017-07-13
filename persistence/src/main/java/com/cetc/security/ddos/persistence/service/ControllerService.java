package com.cetc.security.ddos.persistence.service;

import java.util.List;

import com.cetc.security.ddos.persistence.ControllerEntity;
import com.cetc.security.ddos.persistence.ControllerIfaceEntity;
import com.cetc.security.ddos.persistence.DDoSParamEntity;
import com.cetc.security.ddos.persistence.NetNodeEntity;
import com.cetc.security.ddos.persistence.OpType;
import com.cetc.security.ddos.persistence.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangtao on 2015/7/23.
 */
@Service
public class ControllerService {
    @Autowired
    private ControllerDao controllerDao;
    @Autowired
    private DDoSParamDao dDoSParamDao;
    @Autowired
    private ControllerIfaceDao controllerIfaceDao;
    @Autowired
    private NetNodeDao netNodeDao;
    @Autowired
    private NetNodeService netNodeService;

    private void flush() {
        controllerDao.flush();
        dDoSParamDao.flush();
        controllerIfaceDao.flush();
    }

    @Transactional
    public void addController(ControllerEntity controllerEntity, DDoSParamEntity dDoSParamEntity,
    		ControllerIfaceEntity controllerIfaceEntity) {
        controllerEntity.setFlag(OpType.OP_ADD.getValue());
        controllerDao.insertNoTrans(controllerEntity);
        dDoSParamEntity.setControllerId(controllerEntity.getId());
        dDoSParamDao.insertNoTrans(dDoSParamEntity);
        controllerIfaceEntity.setControllerId(controllerEntity.getId());
        controllerIfaceDao.insertNoTrans(controllerIfaceEntity);
        //flush();
    }

    @Transactional
    public void updateController(ControllerEntity controllerEntity, DDoSParamEntity dDoSParamEntity,
    		ControllerIfaceEntity controllerIfaceEntity) {
        controllerEntity.setFlag(OpType.OP_EDIT.getValue());
        controllerDao.updateNoTrans(controllerEntity);
        dDoSParamDao.updateByControllerId(dDoSParamEntity, dDoSParamEntity.getControllerId());   
        controllerIfaceDao.updateByControllerId(controllerIfaceEntity, controllerIfaceEntity.getControllerId());
        //flush();
    }

    public void setControllerFlag(ControllerEntity controllerEntity, short flag) {
        if (controllerEntity == null) {
            return;
        }
        controllerEntity.setFlag(flag);
        controllerDao.update(controllerEntity);
    }


    protected void changeControllerFlagToDel(int id) {
        ControllerEntity controllerEntity = getController(id);
        if (controllerEntity == null) {
            return;
        }

        controllerEntity.setFlag(OpType.OP_DEL.getValue());
        controllerDao.updateNoTrans(controllerEntity);

        List<NetNodeEntity> NetNodeEntitys = netNodeService.getNetNodeByControllerId(controllerEntity.getId());
        if (NetNodeEntitys == null) {
            return;
        }
        for (NetNodeEntity n : NetNodeEntitys) {
            netNodeService.changeNetNodeFlagToDel(n);
        }
        controllerDao.flush();
    }

    @Transactional
    public void setControllerDelFlag(int id) {
        changeControllerFlagToDel(id);
    }

    public void setControllerNormalFlag(ControllerEntity controllerEntity) {
        setControllerFlag(controllerEntity, OpType.OP_NORMAL.getValue());
    }

    @Transactional
    public void setControllerDelFlag(List<Integer> ids) {
        for (int id : ids) {
            changeControllerFlagToDel(id);
        }
    }

    public void delController(int id) {
        controllerDao.delete(id);
    }

    public void delController(List<Integer> ids) {
        controllerDao.delete(ids);
    }

    public List<ControllerEntity> getAddController() {
        List<ControllerEntity> controllerEntities = controllerDao.find(OpType.OP_ADD.getValue());
        return controllerEntities;
    }

    public List<ControllerEntity> getEditController() {
        List<ControllerEntity> controllerEntities = controllerDao.find(OpType.OP_EDIT.getValue());
        return controllerEntities;
    }

    public List<ControllerEntity> getDelController() {
        List<ControllerEntity> controllerEntities = controllerDao.find(OpType.OP_DEL.getValue());
        return controllerEntities;
    }

    public List<ControllerEntity> getController() {
        List<ControllerEntity> controllerEntities = controllerDao.findNotEqualFlag(OpType.OP_DEL.getValue());
        return controllerEntities;
    }

    public List<ControllerEntity> getController(int start, int limit) {
        return controllerDao.findOrderedDescByNotEqualFlag(OpType.OP_DEL.getValue(), start, limit);
    }

    public ControllerEntity getController(int id) {
        ControllerEntity controllerEntity = controllerDao.findById(id);
        return controllerEntity;
    }

    public DDoSParamEntity getDDoSParam(int id) {
        return dDoSParamDao.findByControllerId(id);
    }
    
    public ControllerIfaceEntity getControllerIface(int id) {
    	return controllerIfaceDao.findByControllerId(id);
    }

    public long countController() {
        return controllerDao.countByNotEqualFlag(OpType.OP_DEL.getValue());
        //return controllerDao.countAll();
    }

    public ControllerEntity getController(String ip, int port) {
        return controllerDao.find(ip, port);
    }

}
