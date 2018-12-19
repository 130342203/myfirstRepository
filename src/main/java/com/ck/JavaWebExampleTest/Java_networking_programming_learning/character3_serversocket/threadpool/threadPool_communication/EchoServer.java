package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.threadpool.threadPool_communication;

import com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.threadpool.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*利用自己编写的线程池实现线程与客户的通信
* 注意：serverSocket默认的请求队列长度是50，超出长度时，会拒绝新的连接，只有连接被取出，腾出位置后才能接收新的客户端连接
* * */
public class EchoServer {
    private int port = 8000;
    private ServerSocket serverSocket;
    private int POOL_SIZE = 4;
    private ThreadPool threadPool;

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }
    public EchoServer() throws IOException {
        serverSocket = new ServerSocket(port);
        //创建线程池//Runtime的zvailableProcessors()方法返回当前系统的CPU数目
        //系统CPU越多，线程池中工作线程的数目就越多
        threadPool = new ThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        System.out.println("服务器启动--->");
    }
    public void service(){
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                threadPool.excute(new handler(socket));
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
            System.out.println("新连接--："+socket.getInetAddress()+":"+socket.getPort());
            try {
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                String msg = null;
                while ((msg = br.readLine()) !=null){
                    System.out.println("client:"+msg);
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
