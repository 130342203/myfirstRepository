package com.ck.utils.msg.email;

import com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning.mailsender.mailSender;
import com.ck.dao.entity.EmailMessage;
import com.ck.framework.SpringContextHolder;
import com.ck.service.EmailService;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/4/10
 * @Content:
 */
public class EmailSendUtil {

    static EmailService emailService = SpringContextHolder.getBean(EmailService.class);


    public static void sendEmail(EmailMessage msg){
        emailService.sendMail(msg);
    }

}
