package com.gb.ir;

import com.gb.ir.util.PathUtils;

import java.io.*;
import java.net.Socket;

//客户端，可任意，根据协议，根据协议发送指定的数据到远程服务器，由远程服务器转给客户端，客户端的实现可以任意选择
public class IRClient {
    public static void main(String[] args) throws IOException {
        IRClient irClient = new IRClient();
        irClient.sendMsg("127.0.0.1", 4567);
    }

    public void sendMsg(String ip, int port) throws IOException {
        File file = new File(PathUtils.getRootPath() + "input/ThePalaceMuseum.jpg");
        BufferedInputStream bfis = new BufferedInputStream(new FileInputStream(file));
        Socket client = new Socket(ip, port);
        BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
        bos.write("printer".getBytes());
        bos.flush();
        //写入开始表示

        //写入文件
        bos.write(Constant.MESSAGE_HEAD_BYTES);

//        文件操作
        int len;
        byte[] bytes = new byte[Constant.BUFFER_BYTE_SIZE];
        while ((len = bfis.read(bytes)) > 0) {
            bos.write(bytes, 0, len);
        }
        bos.flush();
//
//        其他操作
//        bos.write("This is the really Message!".getBytes());
//        bos.write("This is the really Message!".getBytes());
//        bos.write("This is the really Message!".getBytes());
//        bos.write("notepad".getBytes());
//        bos.write("calc".getBytes());
//        bos.write("java HelloWorld".getBytes());
//        bos.flush();
        bfis.close();
        bos.write(Constant.MESSAGE_END_BYTES);
        bos.flush();
        client.close();
        System.out.println("客户端已经关闭！");
    }
}
