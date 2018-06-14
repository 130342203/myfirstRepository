package com.ck.config;

/**
 * Created by Administrator on 2018/6/6.
 */
public interface ConfigConstants {
    /**
     * config charset
     * */
    String CHARSET = "utf-8";

    String PRIMARY_TRANSACTION_MANAGER = "primaryTransactionManager";
    String PRIMARY_DATA_SOURCE = "primaryDataSource";
    String PRIMARY_SESSION_FACTORY = "primarySessionFactory";
    String PRIMARY_JDBC_TEMPLATE = "primaryJdbcTemplate";

    String SCAN_PACKAGE_PATH = "com.ck";

    /**
     * device
     * */
    String DEVICE_TYPE_PC = "PC";
    String DEVCIE_TYPE_ANDROID = "ANDROID";
    String DEVICE_TYPE_IOS = "IOS";
    String DEVICE_TYPE_UNKNOW = "UNKNOW";
    /**
     * output
     * */
    String OUTPUT_TYPE_JSON = "JSON";
    String OUTPUT_TYPE_XML = "XML";
    String OUTPUT_TYPE_TEXT = "TEXT";
    String OUTPUT_TYPE_DEFAULT = OUTPUT_TYPE_JSON;

    String CONTENT_TYPE_JSON = "application/json;charset=" + CHARSET;
    String CONTENT_TYPE_XML = "text/xml;charset=" + CHARSET;
    String CONTENT_TYPE_TEXT = "text/plain;charset=" + CHARSET;
    String CONTENT_TYPE_JAVASCRIPT = "application/javascript;charset=" + CHARSET;

}
