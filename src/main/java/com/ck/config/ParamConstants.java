package com.ck.config;

/**
 * @author ron
 *         2016/5/20.
 */
public interface ParamConstants {
    /**
     * 分页相关
     * */
    String KEY_PAGE = "page";
    String KEY_ROWS = "rows";

    Integer DEFAULT_ROWS = 20;

    Long USER_REDIS_EXPIRE_TIME = 60*60*1000L*1; //1 hours
    Integer USER_COOKIE_EXPIRE_TIME = 60*60*1; //1 hours

    /**
     * SqlExecuteService 执行sql
     */
    String PARAM_SQL = "sql";

    /**
     * 跨域相关
     * */
    String KEY_JSONP_CALLBACK = "jsonpcallback";

    /**
     * 输出格式相关
     * */
    String KEY_OUTPUT_TYPE = "output";

    /**
     * 自定义功能PARAM名字
     * */
    String PARAM_PLUGIN_CODE = "pluginCode";

    /**
     * session verify_code
     * */
    String KEY_LOGIN_USER = "SE_KEY_LOGIN_USER";
    String KEY_CAPTCHA = "SE_KEY_MM_CODE";

    /**
     * deivce  param
     * */
    String KEY_UDID_PARAM = "_udid";
    String KEY_VERSION_PARAM = "_ver";
    String KEY_SESSIONKEY_PARAM = "_sk";
    String KEY_DEVICE_PARAM = "_dev";
    String KEY_USER_AGENT = "User-Agent";
    /**
     * 国际化cookie key
     */
    String KEY_I18NEXT_PARAM = "i18next";

    /**
     * 请求参数 key 定义
     */
    String KEY_REQ_FUNCTION_CODE = "FunctionCode";

}
