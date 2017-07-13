package com.cetc.backend.aop;


import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
/**
 * Created by Administrator on 10/20 0020.
 */
public class TestLog {
    private static Logger logger = AntiLogger.getLogger(TestLog.class);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++)
        {
            logger.warn("2B青年欢乐多 debug log " +  i);
            //System.out.println("2B青年欢乐多 debug log " +  i);
            //i++;
        }
    }


}
