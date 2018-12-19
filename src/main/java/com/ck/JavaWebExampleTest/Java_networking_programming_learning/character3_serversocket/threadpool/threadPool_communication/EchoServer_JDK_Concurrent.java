package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.threadpool.threadPool_communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*使用JDK自带的线程池类实现线程与客户端通信，实现与EchoServer类一样的效果*/
public class EchoServer_JDK_Concurrent {
    private int port = 8000;
    private ServerSocket serverSocket;
    private int POOL_SIZE = 4;
    private ExecutorService executorService;

    public EchoServer_JDK_Concurrent() throws IOException {
        serverSocket = new ServerSocket(port);
        //创建线程池//Runtime的zvailableProcessors()方法返回当前系统的CPU数目
        //系统CPU越多，线程池中工作线程的数目就越多
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        System.out.println("服务器启动");
    }
    public void service(){
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                executorService.execute(new handler(socket));
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
