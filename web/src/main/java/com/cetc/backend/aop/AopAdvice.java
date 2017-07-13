package com.cetc.backend.aop;

import com.cetc.security.ddos.common.type.MsgType;
import com.cetc.security.ddos.common.utils.Message;
import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
/**
 * Created by zhangtao on 2015/8/28.
 */

public class AopAdvice {
    private static Logger logger = AntiLogger.getLogger(AopAdvice.class);
    protected static Message notifyMsg;

     static {
         init();
    }

    protected static synchronized void init() {
        if (notifyMsg != null) {
            return;
        }

        try {
            notifyMsg = new Message();
        } catch (Exception e) {
            logger.error("Init notify message fail.");
        }
    }

    protected void sendNotify(short msgType) {
        notifyMsg.send(String.valueOf(msgType));
    }

    public void controllerAfterReturn(/*JoinPoint jp*/) {
        sendNotify(MsgType.MSG_CONTROLLER);
        logger.debug("Send controller data changing message to app.");
    }

    public void netNodeAfterReturn() {
        sendNotify(MsgType.MSG_NETNODE);
        logger.debug("Send net node data changing message to app.");
    }

    public void poAfterReturn() {
        sendNotify(MsgType.MSG_PO);
        logger.debug("Send net node data changing message to app.");
    }

    public void cleanDevAfterReturn() {
        //controllerAfterReturn();
        //netNodeAfterReturn();
        sendNotify(MsgType.MSG_CLEAN_DEV);
        logger.debug("Send clean device data changing message to app.");
    }

    public void restoreAfterReturn() {
        sendNotify(MsgType.MSG_RESTART);
        logger.debug("Send restart message to app.");
    }
}
