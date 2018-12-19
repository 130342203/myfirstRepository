package com.ck.dao.entity;

import javax.persistence.Id;
import java.util.Date;

public class PredictNumber {
    @Id
    private Integer pkid;
    private Date time;
    private String open;
    private String close;
    private String lower;
    private String higher;

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public String getHigher() {
        return higher;
    }

    public void setHigher(String higher) {
        this.higher = higher;
    }
}
