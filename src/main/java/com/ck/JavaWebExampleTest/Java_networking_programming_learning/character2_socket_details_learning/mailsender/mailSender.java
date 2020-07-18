package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning.mailsender;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/*SMTP协议的理解--邮件发送*/
public class mailSender {
    private String smtpsServer = "smtp.qq.com";
    private int port = 25;

    public static void main(String[] args) throws IOException {
        String user = null;
        String toUser = null;
        String password = null;
        String content = null;
        if (args.length != 0){
            System.out.println(111);
            user = args[0];
            password = args[1];
            toUser = args[2];
            content =args[3];
        }else {
            System.out.println("运行程序时，请依次输入发送人邮箱，密码，收件人邮箱,邮件内容");
            user = "1203796151@qq.com";
//            password="ck2451930";
            password="nkwdwlkdijtwibgd";
//           System.exit(0);
        }
        /*message msg = new message(user,
                toUser,
                "testEmail",
                content);*/
        message msg = new message("1203796151@qq.com","chenkun@bireturn.com","testEmail","邮件协议测试");
        new mailSender().sendEmail(msg,user,password);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        return printWriter;
    }

    private void sendEmail(message msg,String user,String password) throws IOException {
        Socket socket = null;

        try {
            socket = new Socket(smtpsServer,port);
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

            //sendAndReceive();
            sendAndReceive("MAIL FROM:<"+msg.from +">",br,pw);
            sendAndReceive("RCPT TO:<"+msg.to+">.",br,pw);
            sendAndReceive("DATA",br,pw);
            pw.println("Content-Type:text/plain;charset=\"gb2312\"");//发送正文，需要添加这一行，并且后一行需要输出换行
            pw.println("");
            pw.println(msg.data+";double kill");//传输邮件内容
            System.out.println("Client>"+msg.data);
            sendAndReceive(".",br,pw);//告诉服务器发送完毕
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

    private void sendAndReceive(String str,BufferedReader br,PrintWriter pw) throws IOException {
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

    static class message{
        String from;
        String to;
        String subject;
        String content;
        String data;

        public message(String from,String to,String subject,String content){
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.content = content;
            data = "subject:"+subject+"\r\n"+content;
        }
    }
}
