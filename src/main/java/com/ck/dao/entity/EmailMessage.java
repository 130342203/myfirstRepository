package com.ck.dao.entity;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/4/10
 * @Content:
 */
public class EmailMessage {
    String from;
    String to;
    String subject;
    String content;
    String data;

   /* public EmailMessage(String from,String to,String subject,String content){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        data = "subject:"+subject+"\r\n"+content;
    }*/


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
