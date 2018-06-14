package com.ck.framework.response;

import com.ck.config.AogContextConstants;
import com.ck.config.ConfigConstants;
import com.ck.framework.ContextUtil;
import com.ck.framework.context.AppContext;
import com.ck.framework.utils.JacksonUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author ron
 *         2016/5/20.
 */
public class ResponseUtils {

    //private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    public static void write(Object obj, HttpServletResponse response) throws Exception {
        AppContext context = ContextUtil.getAppContext();
        String jsonpCallback = (String) context.get(AogContextConstants.KEY_JSONP_CALLBACK_PARAM);
        PrintWriter out = response.getWriter();
        //jsonp
        if (StringUtils.isNotBlank(jsonpCallback)) {
            response.setContentType(ConfigConstants.CONTENT_TYPE_JAVASCRIPT);
            JSONPObject jsonpObject = new JSONPObject(jsonpCallback, obj);
            response.getWriter().write(jsonpObject.getFunction() +
                    "(" + JacksonUtils.obj2json(obj) + ")");
            out.flush();
            out.close();
            return;
        }
        //standard output
        String outputType = (String) context.get(AogContextConstants.KEY_OUTPUT_TYPE_PARAM);
        if (StringUtils.isBlank(outputType)) {
            outputType = ConfigConstants.OUTPUT_TYPE_DEFAULT;
        }
        outputType = outputType.toUpperCase();
        if(!ConfigConstants.OUTPUT_TYPE_JSON.equals(outputType)
                && !ConfigConstants.OUTPUT_TYPE_XML.equals(outputType)
                && !ConfigConstants.OUTPUT_TYPE_TEXT.equals(outputType)){
            outputType = ConfigConstants.OUTPUT_TYPE_DEFAULT;
        }
        //郝秋月 特殊问题特殊处理，同一台电脑，不同的网络环境，user-agent不一样；
        String specalUa = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)";

        if (ConfigConstants.OUTPUT_TYPE_JSON.equals(outputType)) {
            String ua = (String) context.get(AogContextConstants.KEY_AGENT_PARAM);
            if(ua !=null && ua !="" && ua.indexOf("MSIE")  > 0 && !ua.equals(specalUa)){
               // logger.info("==============================outputType-JSON:text");
                response.setContentType(ConfigConstants.CONTENT_TYPE_TEXT);
            }else{
                response.setContentType(ConfigConstants.CONTENT_TYPE_JSON);
            }
            out.write(JacksonUtils.obj2json(obj));
        } else if (ConfigConstants.OUTPUT_TYPE_XML.equals(outputType)) {
            response.setContentType(ConfigConstants.CONTENT_TYPE_XML);
            out.write(JacksonUtils.obj2Xml(obj));
        } else  if (ConfigConstants.OUTPUT_TYPE_TEXT.equals(outputType)) {
            response.setContentType(ConfigConstants.CONTENT_TYPE_TEXT);
            out.write(obj.toString());
        }
        out.flush();
        out.close();
    }

}
