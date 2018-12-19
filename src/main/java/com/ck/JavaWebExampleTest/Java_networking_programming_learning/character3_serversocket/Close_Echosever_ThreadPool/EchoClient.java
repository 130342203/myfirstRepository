package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.Close_Echosever_ThreadPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/*测试命令关闭服务器端*/
public class EchoClient {
    private static PrintWriter getWriter(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        return printWriter;
    }
    public static void main(String[] args) throws IOException {
        final int length = 100;
        String host = "localhost";
        int port = 8001;
        Socket[] sockets = new Socket[length];
        for (int i = 0; i < length; i++) {
            sockets[i] = new Socket(host, port);
            System.out.println("第" + (i + 1) + "次连接");
            PrintWriter pw = getWriter(sockets[i]);
            pw.println("shutdown");//关闭命令,一次连接足够
            pw.flush();
        }
        try {
            Thread.sleep(10000);
            for (int i = 0; i < length; i++) {
                System.out.println("关闭第" + (i + 1) + "次连接");
                sockets[i].close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

