package com.ck.config;

/**
 * @author ron
 *         2016/5/26.
 */
public interface UrlConstants {

    String VERSION = "1";

    String URL_API_BASE = "/api/v" + VERSION + "/";

    String URL_WEB_LOGIN_PAGE = "/login.shtml";

    boolean IS_DEBUG = false; //web main项目 测试权限开关

    boolean MM_IS_DEBUG = false;//物流项目 测试权限开关
}
