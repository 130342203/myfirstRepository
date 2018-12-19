package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.Close_Echosever_ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/*通过发送命令，由服务器程序自动关闭退出*/
public class EchoServer {
    private int port = 8000;
    private ServerSocket serverSocket;
    private int POOL_SIZE = 4;
    private ExecutorService executorService;

    private int portForShutdown = 8001;
    private ServerSocket serverSocketForShutdown;
    private boolean isShutdown = false;

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }

    private Thread shutdownThread = new Thread(){
        public void start(){
            this.setDaemon(true);
            super.start();
        }
        public void run(){
            while (!isShutdown){
                Socket socketForShutdown = null;
                try {
                    socketForShutdown = serverSocketForShutdown.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(socketForShutdown.getInputStream()));
                    String command = br.readLine();
                    if (command.equals("shutdown")){
                        long begin = System.currentTimeMillis();
                        socketForShutdown.getOutputStream().write("服务器正在关闭\r\n".getBytes());
                        System.out.println("服务器正在关闭");
                        isShutdown = true;
                        //请求关闭线程
                        // 线程池不再接收新的任务，但是会继续执行完工作队列中现有的任务
                        executorService.shutdown();
                        //等待关闭线程池，每次等待的超时时间为30秒
                        while (!executorService.isTerminated()){
                            executorService.awaitTermination(30, TimeUnit.SECONDS);
                        }
                        serverSocket.close();//关闭服务器连接
                        long entime = System.currentTimeMillis();
                        socketForShutdown.getOutputStream().write(("服务器已关闭，用时："+(entime-begin)+"ms\r\n").getBytes());
                        System.out.println("服务器已关闭，用时："+(entime-begin)+"ms");
                        socketForShutdown.close();
                        serverSocketForShutdown.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public EchoServer() throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(60000);//限定客户端连接时间为60秒
        serverSocketForShutdown = new ServerSocket(portForShutdown);
        //创建线程池//Runtime的zvailableProcessors()方法返回当前系统的CPU数目
        //系统CPU越多，线程池中工作线程的数目就越多
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        shutdownThread.start();
        System.out.println("服务器启动");
    }
    public void service(){
        while (!isShutdown){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(60000);//等待客户发送数据的连接时间为60秒
                executorService.execute(new handler(socket));
            }catch (SocketTimeoutException e){
                //超时异常不必处理
                System.out.println("客户端发送数据超时，不必处理");
            }catch (RejectedExecutionException e){
                try {
                    if (socket != null){socket.close();}
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }catch (SocketException e){
                //如果是由于在执行serverSocket.accept()方法
                //serversocket被shutdownThread线程关闭而导致的异常，就退出service()方法
                if (e.getMessage().indexOf("socket closed") != -1){
                    return;
                }
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
