package com.beyondtwo.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtils {
    private static Logger logger = null;
    public static void log(String msg){
        if(logger==null){
            logger = Logger.getAnonymousLogger();
        }
        logger.setLevel(Level.OFF);
        logger.info(msg);
    }
}
