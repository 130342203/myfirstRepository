package com.ck.framework.utils;

/**
 * @author ron
 *         2016/12/7.
 */
public class BrowserUtils {
    public static boolean isMicroMessenger(String agent){
        if(agent != null && agent.indexOf("MicroMessenger") != -1){
            return true;
        }
        String lowagent = agent.toLowerCase();
        if(lowagent.indexOf("iphone") != -1 || lowagent.indexOf("android") != -1){
            return true;
        }
        return false;
    }
}
