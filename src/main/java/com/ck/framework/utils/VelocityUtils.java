/*
package com.ck.framework.utils;

import com.ck.framework.SpringContextHolder;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Map;

*/
/**
 * @author ron
 *         2016/10/8.
 *//*

public class VelocityUtils {

    public static String buildTemlplate(VelocityEngine engine, String template, Map<String, Object> model ){
        String s = VelocityEngineUtils.mergeTemplateIntoString(engine, template, "utf-8", model);
        System.out.println(s);
        return s;
    }
    public static String buildTemlplate(String template, Map<String, Object> model ){
        VelocityEngine engine  = SpringContextHolder.getBean(VelocityEngine.class);
        return buildTemlplate(engine, template, model );
    }

}
*/
