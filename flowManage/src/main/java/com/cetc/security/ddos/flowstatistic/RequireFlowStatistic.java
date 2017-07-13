package com.cetc.security.ddos.flowstatistic;

import com.cetc.security.ddos.controller.adapter.FlowConfig;
import com.cetc.security.ddos.controller.adapter.TrafficTuple;
import com.cetc.security.ddos.controller.adapter.Controller;


import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;

/**
 * Created by hasen on 2015/6/30.
 */
public class RequireFlowStatistic {
    private static Logger logger = AntiLogger.getLogger(RequireFlowStatistic.class);

    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public interface JsonPreprocessor {
        public String preProcess(String jsonStr);
    }

    public RequireFlowStatistic(Controller controller) throws Exception {
        this.setController(controller);
    }

    public TrafficTuple getFlowStats(FlowConfig fc, TrafficTuple trafficTuple) throws Exception {
        try {
            if (fc == null) {
                logger.error("Input parm FlowConfig object is null.");
                throw new Exception("Input parm FlowConfig object is null");
            }

            if (trafficTuple == null) {
                logger.error("Input parm TrafficTuple object is null.");
                throw new Exception("Input parm TrafficTuple object is null");
            }

            return controller.getFlowStats(fc, trafficTuple);
        } catch (Throwable e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

}