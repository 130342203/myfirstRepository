package com.ck.framework.error;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ron
 *         2016/5/23.
 */

public class ErrorDict {

    private static Map<Integer, String> errorMap = new HashMap<>();

    private static Boolean lock = false;

    public static String asString(int code){
        String msgI18nText =  errorMap.get(code);
        if(StringUtils.isNotBlank(msgI18nText)) {
            return msgI18nText;
        }
        return "MessageCode : " + code;
    }

    public synchronized static void init(Map<Integer, String> map){
        if(!lock.booleanValue()){
            errorMap.clear();
            errorMap.putAll(map);
            lock = true;
        }
    }
}
