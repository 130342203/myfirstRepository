package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.serverAndClient;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        final int length = 100;
        String host = "localhost";
        int port = 8000;
        Socket[] sockets = new Socket[length];
        for (int i=0;i<length;i++){
            sockets[i] = new Socket(host,port);
            System.out.println("第"+(i+1)+"次连接");
        }
        try {
            Thread.sleep(3000);
            for (int i =0;i<length;i++){
                System.out.println("关闭第"+(i+1)+"次连接");
                sockets[i].close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
