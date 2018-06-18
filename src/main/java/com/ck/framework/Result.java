package com.ck.framework;

import com.ck.framework.error.ErrorCommonData;
import com.ck.framework.error.ErrorDict;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author ron
 *         2016/5/20.
 */
@SuppressWarnings("serial")
public class Result<T> implements Serializable {
    public Result(){}

    public Result(int code){
        this.code = code;
        this.msg = ErrorDict.asString(code);
    }

    public Result(int code, Object data){
        this.code = code;
        this.msg = ErrorDict.asString(code);
        this.data = data;
    }

    public Result(int code, Object data, int page, long total){
        this.code = code;
        this.msg = ErrorDict.asString(code);
        this.data = data;
        this.page = page;
        this.total = total;
    }


    private int code;
    private int page = 1;
    private long total = 0;
    private String msg = "";
    private String[] msgData = new String[]{};
    private Object data = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        this.msg = ErrorDict.asString(code);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
        if(data instanceof Page){
            this.total = ((Page) data).getTotal();
            this.page = ((Page) data).getPageNum();
        }else if(data instanceof PageInfo){
            this.total = ((PageInfo) data).getTotal();
            this.page = ((PageInfo) data).getPageNum();
        }
        if(this.page == 0){
            this.page = 1;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String[] getMsgData() {
        return msgData;
    }

    public void setMsgData(String[] msgData) {
        this.msgData = msgData;
    }

    public static Result buildSuccess(){
        return  new Result(ErrorCommonData.SUCCESS_CODE, ErrorDict.asString(ErrorCommonData.SUCCESS_CODE));
    }

    public static Result buildError(){
        return  new Result(ErrorCommonData.SERVER_ERROR, ErrorDict.asString(ErrorCommonData.SERVER_ERROR));
    }
}
