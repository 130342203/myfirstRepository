package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning.sender_receiver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class receiver {
    private int port = 8000;
    private ServerSocket serverSocket;
    private static int stopWay = 1;//结束通信的方式
    private static int NATURAL_STOP = 1;
    private static int SUDDEN_STOP = 2;
    private static int SOCKET_STOP = 3;
    private static int OUTPUT_STOP = 4;
    private static int SERVERSOCKET_STOP = 5;//关闭serverSocket
    private static String savePath ="";

    public static void main(String[] args) throws IOException, InterruptedException {
        long begin = System.currentTimeMillis();
        System.out.println("开始时间："+begin);
        if (args.length > 0){
            stopWay = Integer.parseInt(args[0]);
            savePath = args[1];
            new receiver().receive();
        }else {
            stopWay = 1;
            savePath = "\"D:\\\\myProjects\\\\test\"+File.separator+begin+\".text\"";
            new receiver().receive();
        }
        long end = System.currentTimeMillis();
        System.out.println("结束时间："+end+"耗时："+(end - begin)+"ms");
    }

    public receiver() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("服务器已启动");
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public void receive() throws IOException, InterruptedException {
        Socket socket = null;
        socket = serverSocket.accept();
        BufferedReader br = getReader(socket);
        int fileLength = 0;

        for (int i=0;i<30;i++){
            String msg = br.readLine();
            System.out.println("receive："+msg);
            //Thread.sleep(1000);
            if (msg.contains("fileLength")){
                fileLength = Integer.parseInt(msg.split("-")[1]);
                break;
            }
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
            }else if (stopWay == SERVERSOCKET_STOP){
                System.out.println("关闭serverSocket并终止程序");
                serverSocket.close();
                break;
            }
        }
        //接收文件
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        FileOutputStream fos = new FileOutputStream(savePath);
        byte[] buff = new byte[4096];
        int readSize;
        int readLength = 0;
        System.out.println("开始接收，文件大小："+fileLength);
        if (fileLength == 0){
            System.exit(0);
        }
        while ((readSize = dis.read(buff)) >0){
            fos.write(buff,0,readSize);
            readLength += readSize;
            fos.flush();
            System.out.println("已接收进度----->"+(((double)readLength/fileLength) * 100) +"%");
        }
        fos.close();
        dis.close();
        if (stopWay == NATURAL_STOP){
            socket.close();
            serverSocket.close();
            System.out.println("程序自然终止");
        }
    }

}
