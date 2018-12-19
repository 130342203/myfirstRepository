package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.serverAndClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*serverSocket用法详解-几种重载方式
* 1、serverSocket();2、serverSocket(port);3、serverSocket(int port,int backlog);
* 4、serverSocket(int port,int backlog,InetAddress bindAddr)
* --serverSocket几种配置
* 1、超时时间设置；2、端口重用的设置（可能受到操作系统的限制）；3、接收数据缓存大小设置，以字节为单位
* 4、是指连接时间、带宽和延迟的相对重要性（setPerformancePreferences(int connectionTime,int latency,int bandwidth)）
* */
public class server {
    private int port =8000;
    private ServerSocket serverSocket;

    public server() throws IOException {
        serverSocket = new ServerSocket(port,3);//连接请求队列长度为3,注意是请求队列长度，
        // 一旦执行了seversocket.accept()既不在队列中
        System.out.println("服务器启动");
    }

    public static void main(String[] args) {
        try {
            server server = new server();
            //Thread.sleep(10000);
            server.service();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
    public void service(){
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("新连接："+socket.getInetAddress()+":"+socket.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {

                    if (socket != null){
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
