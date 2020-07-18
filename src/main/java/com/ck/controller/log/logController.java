package com.ck.controller.log;

import com.ck.dao.entity.City;
import com.ck.dao.entity.EmailMessage;
import com.ck.dao.entity.IcSysLog;
import com.ck.dao.mapper.IcSysLogMapper;
import com.ck.framework.ContextUtil;
import com.ck.framework.context.AppContext;
import com.ck.utils.msg.email.EmailSendUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/4/9
 * @Content:
 */
@Controller
public class logController {
    @Resource
    IcSysLogMapper icSysLogMapper;

    @RequestMapping(value = "log/record")
    @ResponseBody
    public void test() throws IOException {
        AppContext context = ContextUtil.getAppContext();
        IcSysLog log = new IcSysLog();
        log.setCreateDate(new Date());
        log.setContent("你来了");
        icSysLogMapper.insert(log);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("测试邮件");
        emailMessage.setContent("有人访问了");
        emailMessage.setData("有人访问了");
        EmailSendUtil.sendEmail(emailMessage);
    }
    @RequestMapping(value = "log/test")
    @ResponseBody
    public void test_log() throws IOException {
        AppContext context = ContextUtil.getAppContext();
        List<IcSysLog> sysLogs = icSysLogMapper.selectAll();
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("测试邮件");
        emailMessage.setContent("有人访问了");
        emailMessage.setData("有人访问了");
        EmailSendUtil.sendEmail(emailMessage);
    }
}
