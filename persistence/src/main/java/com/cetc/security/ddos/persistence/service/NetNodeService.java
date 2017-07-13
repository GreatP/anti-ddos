package com.cetc.security.ddos.persistence.service;

import java.util.List;

import com.cetc.security.ddos.persistence.NetNodeEntity;
import com.cetc.security.ddos.persistence.OpType;
import com.cetc.security.ddos.persistence.ProtectObjectEntity;
import com.cetc.security.ddos.persistence.dao.NetNodeDao;
import com.cetc.security.ddos.persistence.dao.ProtectObjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangtao on 2015/7/23.
 */
@Service
public class NetNodeService {
    @Autowired
    private NetNodeDao netNodeDao;
    @Autowired
    private ProtectObjectDao protectObjectDao;

    public List<NetNodeEntity> getAllNetNode() {
        return netNodeDao.findAll();
    }

    public NetNodeEntity getNetNode(int id) {
        return netNodeDao.findById(id);
    }

    public List<NetNodeEntity> getNetNodeByControllerId(int id) {
        return netNodeDao.findByControllerIdAndNotEqualFlag(id, OpType.OP_EDIT.getValue());
    }
    /*
    public NetNodeEntity getNetNodeByswId(String swid) {
        return netNodeDao.findByswIdAndNotEqualFlag(swid, OpType.OP_EDIT.getValue());
    }
    */
    public NetNodeEntity getNetNodeByName(String nodeName) {
        return netNodeDao.findByName(nodeName);
    }

    @Transactional
    public void addNetNode(NetNodeEntity netnodeEntity) {
        netnodeEntity.setFlag(OpType.OP_ADD.getValue());
        //System.out.println(netnodeEntity.getFlag());
        netNodeDao.insert(netnodeEntity);
    }

    public void updateNetNode(NetNodeEntity netnodeEntity) {
        netnodeEntity.setFlag(OpType.OP_EDIT.getValue());

        netNodeDao.update(netnodeEntity);
    }

    public void delNetNode(int id) {
        netNodeDao.delete(id);
    }

    @Transactional
    public void delNetNode(List<Integer> ids) {

        for(int i:ids) {
            netNodeDao.delete(i);
        }
    }


    public void setNetNodeFlag(NetNodeEntity netnodeEntity, short flag) {
        if (netnodeEntity == null) {
            return;
        }
        netnodeEntity.setFlag(flag);
        netNodeDao.update(netnodeEntity);
    }

    protected void changeNetNodeFlagToDel(NetNodeEntity netNodeEntity) {
        if (netNodeEntity == null) {
            return;
        }

        netNodeEntity.setFlag(OpType.OP_DEL.getValue());
        netNodeDao.updateNoTrans(netNodeEntity);

        //List<ProtectObjectEntity> protectObjectEntitys = protectObjectDao.findByNetNodeIdAndNotEqualFlag(netNodeEntity.getId(), OpType.OP_DEL.getValue());
        List<ProtectObjectEntity> protectObjectEntitys = null;
        if (protectObjectEntitys == null) {
            return;
        }
        for (ProtectObjectEntity po : protectObjectEntitys) {
            po.setFlag(OpType.OP_DEL.getValue());
            protectObjectDao.updateNoTrans(po);
        }

        protectObjectDao.flush();
        netNodeDao.flush();
    }

    protected void changeNetNodeFlagToDel(int id) {
        NetNodeEntity netNodeEntity = getNetNode(id);
        if (netNodeEntity == null) {
            return;
        }

        changeNetNodeFlagToDel(netNodeEntity);
    }

    @Transactional
    public void setNetNodeDelFlag(int id) {
        changeNetNodeFlagToDel(id);
    }

    public void setNetNodeNormalFlag(NetNodeEntity netnodeEntity) {

        setNetNodeFlag(netnodeEntity, OpType.OP_NORMAL.getValue());
    }

    @Transactional
    public void setNetNodeDelFlag(List<Integer> ids) {
        for (int id : ids) {
            changeNetNodeFlagToDel(id);
        }
    }

    public List<NetNodeEntity> getAddNetNode() {
        List<NetNodeEntity> netnodeEntity = netNodeDao.find(OpType.OP_ADD.getValue());
        return netnodeEntity;
    }

    public List<NetNodeEntity> getEditNetNode() {
        List<NetNodeEntity> netnodeEntity = netNodeDao.find(OpType.OP_EDIT.getValue());
        return netnodeEntity;
    }

    public List<NetNodeEntity> getDelNetNode() {
        List<NetNodeEntity> netnodeEntity = netNodeDao.find(OpType.OP_DEL.getValue());
        return netnodeEntity;
    }

    public List<NetNodeEntity> getNetNode() {
        List<NetNodeEntity> netnodeEntity = netNodeDao.findNotEqualFlag(OpType.OP_DEL.getValue());
        return netnodeEntity;
    }

    public List<NetNodeEntity> getNetNode(int start, int limit) {
        return netNodeDao.findOrderedDescByNotEqualFlag(OpType.OP_DEL.getValue(), start, limit);
    }

    public long countNetNode() {
        return netNodeDao.countByNotEqualFlag(OpType.OP_DEL.getValue());
    }

}
