package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.threadAndServerAndClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*使用线程来管理serverSocket获取到的socket，简单的连接通信，不涉及线程处理复杂操作*/
public class EchoServer {
    private int port = 8000;
    private ServerSocket serverSocket;

    public EchoServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("服务器启动");
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }
    public void service(){
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                Thread workThread = new Thread(new handler(socket));
                workThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class handler implements Runnable{
        private Socket socket;
        public handler(Socket socket){
            this.socket = socket;
        }
        private BufferedReader getReader(Socket socket) throws IOException {
            InputStream socketIn = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(socketIn));
        }

        private PrintWriter getWriter(Socket socket) throws IOException {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            return printWriter;
        }
        public String echo(String msg){
            return  msg;
        }
        @Override
        public void run() {
            System.out.println("接收新连接："+socket.getInetAddress()+":"+socket.getPort());
            try {
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                String msg = null;
                while ((msg = br.readLine()) !=null){
                    System.out.println(msg);
                    pw.println(echo(msg));
                    if (msg.equals("bye")){
                        break;
                    }
                }
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
