package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning;
/*测试连接一个网址*/
import java.io.IOException;
import java.net.*;

public class test1_connectTester {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 25;
        //if (args.length > 1){//接收命令行参数
            host = "www.baidu.com";//args[0];
            port = 80;//Integer.parseInt(args[1]);
        //
        System.out.println(System.currentTimeMillis());
        new test1_connectTester().connect(host,port);
    }
    public void connect(String host,int port){
        SocketAddress remoteaddr = new InetSocketAddress(host,port);
        Socket socket = null;
        String result = "";
        try {
            long begin = System.currentTimeMillis();
            socket = new Socket();
            socket.connect(remoteaddr,1000);
            long end = System.currentTimeMillis();
            result = (end - begin) +"ms";
        } catch (BindException e){
            result = "地址 端口绑定失败！";
        }catch (UnknownHostException e){
            result ="未知域名！";
        }catch (ConnectException e){
            result ="连接失败！";
        }catch (SocketTimeoutException e){
            result ="连接超时！";
        }catch (IOException e) {
            result = "failsure";
        }finally {
            try {
                if (socket!=null)socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(remoteaddr+":"+result);
    }
}
