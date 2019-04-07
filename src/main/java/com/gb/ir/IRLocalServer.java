package com.gb.ir;

import com.alibaba.fastjson.JSON;
import com.gb.ir.model.LSConfig;
import com.gb.ir.util.ByteUtils;
import com.gb.ir.util.PathUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

//接受由服务器传来的数据，完成某种功能
//一般为需要完成某种功能的本地服务器，比如说打印等。
public class IRLocalServer {
    public static void main(String[] args) throws IOException {
        IRLocalServer irls = new IRLocalServer();
        String configInfo = PathUtils.getResourcesInfo("LSConfig.json");
        LSConfig lsConfig = JSON.parseObject(configInfo, LSConfig.class);
        irls.setUpServer(lsConfig.getRsIp(), lsConfig.getRsPort(), lsConfig.getLsName());
    }

    private void setUpServer(String ip, int port, String serverName) throws IOException {
        File file = new File(PathUtils.getRootPath() + "output/aaa.png");
        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println(file.getAbsoluteFile());
        serverName = assemblyLocalServerName(serverName);
        Socket client;
        try {
            client = new Socket(ip, port);
            System.out.println("连接服务器成功!");
        } catch (Exception ce) {
            System.out.println("连接服务器失败");
//            ce.printStackTrace();
            return;
        }
        try {

            BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
            bos.write(serverName.getBytes());
            bos.flush();
            while (true) {
                BufferedOutputStream bfos = new BufferedOutputStream(new FileOutputStream(file));
                boolean isStart = false;
                StringBuilder sb = new StringBuilder();
                byte[] bytes = new byte[Constant.BUFFER_BYTE_SIZE];
                int len = 0, start = 0, end = 0;
                while ((len = bis.read(bytes)) > 0) {
                    System.out.println(new String(bytes, 0, len));
                    if (!isStart) {
//                  判断是否含有开始标志，如果有，删除bytes内的开始信息（即为start赋值），并且设置isStart为true
                        if ((start = ByteUtils.findBytes(bytes, Constant.MESSAGE_HEAD_BYTES)) >= 0) {
                            isStart = true;
                            start += Constant.MESSAGE_HEAD_BYTES.length;
                            if (start == len) {
                                start = 0;
                                continue;
                            }
                        }

                    }
                    if (isStart) {
                        //判断是否含有结束位置，有则把结尾删除后写入(len缩短)，然后跳出循环
                        //没有则把所有信息都写入集训循环
                        if ((end = ByteUtils.findBytes(bytes, Constant.MESSAGE_END_BYTES)) >= 0) {
                            if (end > 0) {
                                //TODO 写入数据
                                sb.append(new String(bytes, start, end - start));
                                bfos.write(bytes, start, end - start);
//                            System.out.println(new String(bytes, start, end));
                            }
                            break;
                        }
                        //TODO 写入数据
                        sb.append(new String(bytes, start, len - start));
                        bfos.write(bytes, start, len - start);
//                    System.out.println(new String(bytes, start, len - start));
                        start = 0;
                    }
                }
                //保存到文件
                bfos.flush();
                bfos.close();
                System.out.println(sb.toString());

                //直接执行
                Runtime.getRuntime().exec(sb.toString());

            }
        } catch (SocketException se) {
            System.out.println("远程服务器已经断开！");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }

    private String assemblyLocalServerName(String serverName) {
        return "--" + serverName + "--";
    }
}
