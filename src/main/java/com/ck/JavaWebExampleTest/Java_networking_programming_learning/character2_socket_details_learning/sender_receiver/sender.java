package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning.sender_receiver;


import java.io.*;
import java.net.Socket;

public class sender {
    private String host = "localhost";
    private int port = 8000;
    private Socket socket;
    private OutputStream socketOut;
    private static int stopWay = 1;//结束通信的方式
    private static int NATURAL_STOP = 1;
    private static int SUDDEN_STOP = 2;
    private static int SOCKET_STOP = 3;
    private static int OUTPUT_STOP = 4;
    private static String filePath = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        long begin = System.currentTimeMillis();
        System.out.println("开始时间："+begin);
        if (args.length > 0){
            stopWay = Integer.parseInt(args[0]);
            filePath = args[1];
            new sender().send();
        }else {
            stopWay = 1;
            filePath ="G:\\课件\\深入浅出密码学习题答案.pdf";
            new sender().send();
        }
        long end = System.currentTimeMillis();
        System.out.println("结束时间："+end+"耗时："+(end - begin)+"ms");
    }

    public sender() throws IOException {
        socket = new Socket(host,port);
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        return printWriter;
    }

    public void send() throws IOException, InterruptedException {
        PrintWriter pw = getWriter(socket);
        //socketOut = socket.getOutputStream();
        for (int i = 0;i<20;i++){
            String msg = "hello"+i;
            pw.println(msg);
            pw.flush();
            //socketOut.write(msg.getBytes());
            //socketOut.flush();
            System.out.println("send:"+msg);
            //Thread.sleep(1000);
            if (i == 2){
                if (stopWay == SUDDEN_STOP){
                    System.out.println("程序突然中止");
                    System.exit(0);
                }
            }else if (stopWay == SOCKET_STOP){
                System.out.println("关闭socket并停止输出");
                socket.close();
                break;
            }else if (stopWay == OUTPUT_STOP){
                socket.shutdownOutput();
                System.out.println("关闭输出流并终止程序");
                break;
            }
        }
        //传输文件
        FileInputStream fis = new FileInputStream(filePath);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        byte[] buff = new byte[1024];
        int readsize;
        int sendLength = 0;
        int fileLength = fis.available();
        pw.println("fileLength-"+fileLength);
        pw.flush();
        System.out.println("开始传输，文件大小："+fileLength);
        while ((readsize = fis.read(buff)) > 0){
            dos.write(buff,0,readsize);
            dos.flush();
            sendLength += readsize;
            System.out.println("已发送------>"+(((double)sendLength/fileLength) *100)+"%");
        }
        dos.close();
        fis.close();
        if (stopWay == NATURAL_STOP){
            socket.close();
            System.out.println("程序自然终止");
        }
    }

}
