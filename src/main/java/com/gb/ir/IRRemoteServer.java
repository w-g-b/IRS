package com.gb.ir;

import com.alibaba.fastjson.JSON;
import com.gb.ir.model.RSConfig;
import com.gb.ir.util.PathUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

//部署在远程服务器上，相当于一个中转站的功能，获取到客户端的资源，然后传给本地的服务器
public class IRRemoteServer {
    public static void main(String[] args) throws IOException {
        IRRemoteServer irrs = new IRRemoteServer();
        String configInfo = PathUtils.getResourcesInfo("RSConfig.json");
        RSConfig rsConfig = JSON.parseObject(configInfo, RSConfig.class);
        irrs.startupServer(rsConfig.getPort());
    }

    //保存已经连接了的本地服务器Socket
    private HashMap<String, Socket> lsMap = new HashMap<>();

    public void startupServer(int port) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            System.out.println("服务器已启动！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("启动服务器失败，请检查原因!");
            return;
        }
        while (true) {
            String msg = "";
            try {
                Socket client = ss.accept();
                BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
                byte[] bytes = new byte[Constant.BUFFER_BYTE_SIZE];
                int len = bis.read(bytes);
                msg = new String(bytes, 0, len);
                if (msg.matches("^--.+--$")) {
                    String clientName = msg.substring(2, msg.length() - 2);
                    lsMap.put(clientName, client);
                    System.out.println("本地服务器： " + clientName + " 连接到服务器");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int l;
                            byte[] bs = new byte[1024];
                            try {
                                while (true) {
                                    if ((l = bis.read(bs)) > 0) {
                                        System.out.println(new String(bs, 0, l));
                                    }
                                }
                            } catch (SocketException e) {
                                System.out.println(clientName + "本地服务关闭");
                                lsMap.remove(clientName);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    if (lsMap.containsKey(msg)) {
//                    if (true) {
                        System.out.println("客户端发送信息给本地服务器（" + msg + ")");
                        //TODO 读取客户端的信息
                        Socket ls = lsMap.get(msg);
                        BufferedOutputStream bos = new BufferedOutputStream(ls.getOutputStream());
                        while ((len = bis.read(bytes)) > 0) {
//                            System.out.println(new String(bytes, 0, len));
                            bos.write(bytes, 0, len);
                            bos.flush();
                        }
                    } else {
                        System.out.println("客户端不存在!");
                    }
                    //本地客户端发送完消息之后则可以直接关闭socket连接了
//                    client.close();
                }
            } catch (SocketException se) {
                //TODO 判断是否为服务器，如果是，显示退出提示
//                System.out.println(msg + "断开连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
