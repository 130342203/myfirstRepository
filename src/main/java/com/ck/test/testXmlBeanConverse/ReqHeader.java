package com.ck.test.testXmlBeanConverse;

import javax.xml.bind.annotation.XmlElement;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/5/25
 * @Content:
 */
public class ReqHeader {

    /**
     * 系统编号
     */
    private String sysId;

    /**
     * 鉴权码
     */
    private String authCode;

    /**
     * 流水号
     */
    private String reqNo;

    public String getSysId() {
        return sysId;
    }

    @XmlElement(name="SYSID")
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getAuthCode() {
        return authCode;
    }

    @XmlElement(name="AUTHCODE")
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getReqNo() {
        return reqNo;
    }

    @XmlElement(name="REQNO")
    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }



}