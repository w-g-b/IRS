package com.gb.dt;

import com.gb.ir.Constant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

//简单的端口映射实现，可以用于Socket实现的网络聊天室
@SuppressWarnings("Duplicates")
public class PortMapping {
    public static void main(String[] args) throws IOException {
        PortMapping dataTransfer = new PortMapping();
        dataTransfer.setUpServer(7777, 80);
    }


    public void setUpServer(int srcPort, int desPort) throws IOException {
        ServerSocket ss = new ServerSocket(srcPort);
        System.out.println("服务器已打开");
        Socket desClient = new Socket("127.0.0.1", desPort);
        BufferedOutputStream desBos = new BufferedOutputStream(desClient.getOutputStream());
        BufferedInputStream desBis = new BufferedInputStream(desClient.getInputStream());
//        while (true) {
        Socket srcClient = ss.accept();
        BufferedOutputStream srcBos = new BufferedOutputStream(srcClient.getOutputStream());
        BufferedInputStream srcBis = new BufferedInputStream(srcClient.getInputStream());
        new Thread(() -> {
            try {
                int len = 0;
                byte[] bytes = new byte[Constant.BUFFER_BYTE_SIZE];
                while (true) {
                    while ((len = srcBis.read(bytes)) > 0) {
                        System.out.println(new String(bytes, 0, len));
                        desBos.write(bytes, 0, len);
                        desBos.flush();
                    }
                    desBos.flush();
                }
            } catch (SocketException se) {
                System.out.println("连接断开");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                int len = 0;
                byte[] bytes = new byte[Constant.BUFFER_BYTE_SIZE];
                while (true) {
                    while ((len = desBis.read(bytes)) > 0) {
                        System.out.println(new String(bytes, 0, len));
                        srcBos.write(bytes, 0, len);
                        srcBos.flush();
                    }
                    srcBos.flush();
                }
            } catch (SocketException se) {
                System.out.println("连接断开");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

//        }
    }
}
