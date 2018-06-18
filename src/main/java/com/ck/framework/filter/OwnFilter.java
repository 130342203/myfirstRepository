package com.ck.framework.filter;

import com.alibaba.fastjson.JSON;
import com.ck.config.AogContextConstants;
import com.ck.config.ConfigConstants;
import com.ck.config.ParamConstants;
import com.ck.framework.ContextUtil;
import com.ck.framework.Result;
import com.ck.framework.context.AppContext;
import com.ck.framework.error.ErrorCommonData;
import com.ck.framework.error.ErrorDict;
import com.ck.framework.param.BaseParam;
import com.ck.framework.response.ResponseUtils;
import com.ck.framework.utils.BrowserUtils;
import com.ck.framework.utils.CookieUtil;
import com.ck.framework.utils.Identities;
import com.ck.framework.utils.RequestId;
import com.ck.framework.utils.crypto.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class OwnFilter implements Filter, InitializingBean {
    Logger logger = LoggerFactory.getLogger(OwnFilter.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private final long DUP_TIME = 2;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            AppContext context = ContextUtil.getAppContext();
            //加载 context参数
            loadParam(request, response);
            request.setAttribute(AogContextConstants.REQUEST_CONTEXT_NAME, context);
            context.put(AogContextConstants.KEY_REQUEST, request);
            context.put(AogContextConstants.KEY_RESPONSE, response);
            context.put(AogContextConstants.KEY_REQUESTID, RequestId.getRequestId());
            context.put(AogContextConstants.KEY_JSONP_CALLBACK_PARAM, request.getParameter(ParamConstants.KEY_JSONP_CALLBACK));
            context.put(AogContextConstants.KEY_OUTPUT_TYPE_PARAM, request.getParameter(ParamConstants.KEY_OUTPUT_TYPE));
            request.setAttribute(AogContextConstants.REQUEST_CONTEXT_NAME, context);
            String uri = (String) context.get(AogContextConstants.KEY_URI_PARAM);
            String sessionKey = context.getSessionKey();
            //todo
            //LoginUser user = null;


            boolean needAuth = true;
            String ua = (String) context.get(AogContextConstants.KEY_AGENT_PARAM);
            //todo 微信登录认证
            //boolean ismm = BrowserUtils.isMicroMessenger(ua) && weiXinConfig.getPublicAuthUrl() != null;

           /* //除忽略列表以外，所有接口请求均必须授权，其他WEB请求不需要拦截
            if (ignList.contains(uri) || uri == null
                    || uri.indexOf(".shtml") > 0  //view 下的页面 不需要授权
                    || uri.contains("wechat/")
                    || uri.contains("qiuqiuapi/")
                    || uri.contains("api/v2/order/")) {
                needAuth = false;
            }
            if (ismm && !ignList.contains(uri)) {
                needAuth = true;
            }*/

            if ((uri.indexOf(".shtml") > 0 && uri.indexOf("/template/") > -1) || uri.contains("app.shtml")) {
                needAuth = true;
            }

            /*if (ismm && uri.contains("noAvailable.shtml")) {
                needAuth = false;
            }*/


            /*if (StringUtils.isNotBlank(sessionKey)) {
                user = userService.getUserLoginUser(sessionKey);
                context.put(AogContextConstants.KEY_USER, user);
                if (!ismm && user != null) {
                    boolean isPageAuth = checkPageAuth(uri, user);
                    if (!isPageAuth) {
                        response.sendRedirect("/error/p/restrict_error.shtml");
                        return;
                    }
                }
            }*/


            //接口重复点击治理
            List<String> outSideUri = new ArrayList<>();
            outSideUri.add("/api/v1/stock/city/list");
            outSideUri.add("/api/v1/planrelation/listForCombobox");
            outSideUri.add("/api/v1/dataDomainEnum/list");

                logger.debug(JSON.toJSONString(context.getRequestParamMap()));

            if (uri.indexOf("/api/v1/") > -1 && !outSideUri.contains(uri)) {
                logger.debug(JSON.toJSONString(context.getRequestParamMap()));
                String rmd5 = "rqk:" + MD5Util.md5Hex(uri + JSON.toJSONString(context.getRequestParamMap()));
                //logger.debug("rqk:" + rmd5);

                //logger.debug("dk:" + stringRedisTemplate.getExpire(rmd5));

                if (stringRedisTemplate.getExpire(rmd5) > 0) {
                    Result result = Result.buildSuccess();
                    result.setCode(ErrorCommonData.ERR_USER_REPEAT_CLICK);
                    result.setMsg(ErrorDict.asString(ErrorCommonData.ERR_USER_REPEAT_CLICK));
                    ResponseUtils.write(result, response);
                    return;
                } else {
                    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
                    ops.set(rmd5, "", DUP_TIME, TimeUnit.SECONDS);
                }
            }

            /*if (user == null) {
                String moniUser = request.getParameter("moniUser");
                user = new LoginUser();
                user.setSessionKey("xxx");
                if (StringUtils.isNotBlank(moniUser)) {
                    user.setUserId(Long.parseLong(moniUser));
                } else {
                    user.setUserId(1083l);
                }
                userService.refreshUserCacheAndCookie(user);

            }*/
          /*  if (needAuth) {
                if (user == null) {
                    toAuth(request, response, ismm);
                    return;
                } else {
                    userService.refreshUserCacheAndCookie(user);
                    context.put(AogContextConstants.KEY_USER, user);
                }
            }

            if (!ismm && !uri.contains("restrict_error.shtml") && !checkIpRestrict()) {
                response.sendRedirect("/error/p/restrict_error.shtml");
                return;
            }
*/
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        } finally {
            //清理context;
            ContextUtil.cleanContext();
        }

    }

    @Override
    public void destroy() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


    // 加载参数
    private void loadParam(HttpServletRequest request, HttpServletResponse response) {

        String uri = convertUri(request.getRequestURI());
        String device = getDevice(request);
        String sessionKey = getSessionKey(request);
        String udid = getUdid(request);
        String version = getVersion(request);
        String ua = getUserAgent(request);

        try {
            request.setCharacterEncoding("utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AppContext context = ContextUtil.getAppContext();
        if (ConfigConstants.DEVICE_TYPE_PC.equals(device) && StringUtils.isBlank(udid)) {
            udid = Identities.uuid2();
            CookieUtil.addCookie(response, ParamConstants.KEY_UDID_PARAM, udid, Integer.MAX_VALUE);
        }

        context.put(AogContextConstants.KEY_AGENT_PARAM, ua);
        context.put(AogContextConstants.KEY_URI_PARAM, uri);
        context.put(AogContextConstants.KEY_UDID_PARAM, udid);
        context.put(AogContextConstants.KEY_SESSIONKEY_PARAM, sessionKey);
        context.put(AogContextConstants.KEY_DEVICE_PARAM, device);
        context.put(AogContextConstants.KEY_VERSION_PARAM, version);
        context.put(AogContextConstants.KEY_REQUEST, request);
        context.put(AogContextConstants.KEY_RESPONSE, response);
        context.put(AogContextConstants.KEY_REQUESTID, RequestId.getRequestId());
        context.put(AogContextConstants.KEY_JSONP_CALLBACK_PARAM, request.getParameter(ParamConstants.KEY_JSONP_CALLBACK));
        context.put(AogContextConstants.KEY_OUTPUT_TYPE_PARAM, request.getParameter(ParamConstants.KEY_OUTPUT_TYPE));
        Map<String, String> map = BaseParam.initParamMap(request);
        map.remove("_");
        context.put(AogContextConstants.KEY_REQUEST_PARAM_MAP, map);
        context.put(AogContextConstants.KEY_REQUEST_PARAM_IP, getAddressIP(request));

    }

    private static String convertUri(String uri) {
        if (uri.indexOf("//") > 0) {
            return convertUri(uri.replace("//", "/"));
        }
        return uri;
    }

    private String getDevice(HttpServletRequest request) {
        String val = getVal(request, ParamConstants.KEY_DEVICE_PARAM);
        if (StringUtils.isNotBlank(val)) {
            return val;
        }
        String ua = getUserAgent(request);
        if (ua.indexOf("android") > 0) {
            val = ConfigConstants.DEVCIE_TYPE_ANDROID;
        } else if (ua.indexOf("ipad") > 0 || ua.indexOf("iphone") > 0 || ua.indexOf("ipod") > 0) {
            val = ConfigConstants.DEVICE_TYPE_IOS;
        } else {
            val = ConfigConstants.DEVICE_TYPE_PC;
        }
        return val;
    }

    private String getSessionKey(HttpServletRequest request) {
        String val = getVal(request, ParamConstants.KEY_SESSIONKEY_PARAM);
        return val;
    }

    private String getUdid(HttpServletRequest request) {
        String val = getVal(request, ParamConstants.KEY_UDID_PARAM);
        return val;
    }

    private String getVersion(HttpServletRequest request) {
        String val = getVal(request, ParamConstants.KEY_VERSION_PARAM);
        if (StringUtils.isBlank(val)) {
            val = "1.0";
        }
        return val;
    }

    private String getUserAgent(HttpServletRequest request) {
        String val = getVal(request, ParamConstants.KEY_USER_AGENT);
        return val;
    }

    private String getVal(HttpServletRequest request, String key) {
        String val = request.getHeader(key);
        if (StringUtils.isNotBlank(val)) {
            return val;
        }
        val = CookieUtil.getCookieValue(request, key);
        if (StringUtils.isNotBlank(val)) {
            return val;
        }
        val = request.getParameter(key);
        return val;
    }

    public static String getAddressIP(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        return ipAddress;
    }
}
