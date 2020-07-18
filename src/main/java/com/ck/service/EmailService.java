package com.ck.service;

import com.ck.dao.entity.EmailMessage;
import com.ck.utils.msg.email.EmailSendUtil;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.Authenticator;
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
@Service
public class EmailService {

    private static  String smtpsServer = "smtp.qq.com";
    private static  String smtpsServer2 = "smtp.163.com";
    private static  String fromUser = "1203796151@qq.com";
    private static  String fromUser2 = "13012239233@163.com";
    private static  String fromPass = "nkwdwlkdijtwibgd";
    private static  String fromPass2 = "LXKMJLAHXYIGMPWI";
    private static  String toUser = "493447608@qq.com";
//    private static int port = 25;
    private static int port = 465;



    public void sendMail(EmailMessage msg){
        ExecutorService pool = null;
        try {
            //发送消息、记录消息、记录发送反馈

            int taskSize = 2;
            if (pool == null) {
                pool = Executors.newFixedThreadPool(taskSize);
            }
            msg.setFrom(fromUser2);
            msg.setTo(fromUser);
            SendRunner sendRunner = new SendRunner(msg, fromUser2, fromPass2);
            pool.submit(sendRunner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }



    private class SendRunner implements Runnable{
        private EmailMessage msg;
        private String user;
        private String passWord;

        private SendRunner(EmailMessage msg, String user, String password){
            this.msg = msg;
            this.user = user;
            this.passWord = password;
        }


        @Override
        public void run() {
            try {
                sendEmail(msg,user,passWord);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void sendEmail(EmailMessage msg, String user, String password) throws IOException {
        Socket socket = null;

        try {
            socket = new Socket(smtpsServer2,port);
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            String localHost = InetAddress.getLocalHost().getHostName();

            sendAndReceive(null,br,pw);
            sendAndReceive("HELO "+localHost,br,pw);
            //sendAndReceive("EHLO "+localHost,br,pw);
            String loginUser = new BASE64Encoder().encode(user.substring(0,user.indexOf("@")).getBytes());
            String loginPassword = new BASE64Encoder().encode(password.getBytes());
            sendAndReceive("AUTH LOGIN",br,pw);
            sendAndReceive(loginUser,br,pw);
            sendAndReceive(loginPassword,br,pw);

            sendAndReceive("MAIL FROM:<"+msg.getFrom() +">",br,pw);
            sendAndReceive("RCPT TO:<"+msg.getTo()+">",br,pw);
            sendAndReceive("DATA",br,pw);
            sendNotReceive("Subject:<"+msg.getSubject()+">",pw);//只发 不接
            sendNotReceive("From:<"+msg.getFrom()+">\n",pw);
//            pw.println("Content-Type:text/plain;charset=\"gb2312\"");//发送正文，需要添加这一行，并且后一行需要输出换行
//            pw.println("Content-Type:text/plain;charset=\"UTF-8\"");//发送正文，需要添加这一行，并且后一行需要输出换行

            sendNotReceive(msg.getData(),pw);//传输邮件内容
            sendAndReceive("\n.\n",br,pw);//告诉服务器发送完毕
            sendAndReceive("RSET",br,pw);
            sendAndReceive("QUIT",br,pw);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (socket != null){
                    socket.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void sendMailOfSsl(){
        
    }

    private static void sendAndReceive(String str,BufferedReader br,PrintWriter pw) throws IOException {
        if (str != null){
            System.out.println("Client>"+str);
            pw.println(str);pw.flush();
        }

        /*while ((response = br.readLine()) != null){
            System.out.println("server>"+response);
        }*/
        String response = null;
        if ((response = br.readLine()) != null){
            System.out.println("server>"+response);
        }
    }

    private static void sendNotReceive(String str,PrintWriter pw) throws IOException {
        if (str != null){
            System.out.println("Client>"+str);
            pw.println(str);pw.flush();
        }
    }

    private static BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    private static PrintWriter getWriter(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        return printWriter;
    }

}
