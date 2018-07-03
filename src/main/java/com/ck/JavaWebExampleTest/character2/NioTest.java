package com.ck.JavaWebExampleTest.character2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioTest {
    private void map(String[] args){
        int BUFFER_SIZE = 1024;
        String fileName = "test.db";
        long fileLength = new File(fileName).length();
        int bufferCount = (int) (fileLength/BUFFER_SIZE + 1);
        MappedByteBuffer[] buffers = new MappedByteBuffer[bufferCount];
        long remaining = fileLength;
        for (int i=0;i<bufferCount;i++){
            RandomAccessFile file;
            try {
                file = new RandomAccessFile(fileName,"r");//FileChannel.map读取文件
                buffers[i] = file.getChannel().map(FileChannel.MapMode.READ_ONLY,i*BUFFER_SIZE, (int)Math.min(remaining,BUFFER_SIZE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            remaining -= BUFFER_SIZE;
        }
    }
    public void selector() throws IOException {
        /*java NIO的工作机制，基础用法展示*/
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设置为非阻塞模式
        ssc.socket().bind(new InetSocketAddress(8080));
        ssc.register(selector,SelectionKey.OP_ACCEPT);//注册监听的事件,通过该selector可以获取到对应的channel集合
        while (true){
            Set selectedKeys = selector.selectedKeys();
            //selector.select();//select()方法可用于检测信道I/O的变化，若该通道阻塞或者响应超时则返回0。
            Iterator iterator = selectedKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = (SelectionKey) iterator.next();
                if((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssChannel.accept();//接收到服务端的请求
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                    iterator.remove();
                }else if((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                    SocketChannel sc = (SocketChannel) key.channel();
                    while (true){
                        buffer.clear();
                        int n = sc.read(buffer);
                        if (n<=0){
                            break;
                        }
                        buffer.flip();
                    }
                    iterator.remove();
                }
            }
        }
    }
}
