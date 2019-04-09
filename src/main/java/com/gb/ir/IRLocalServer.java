package com.gb.ir;

import com.alibaba.fastjson.JSON;
import com.gb.ir.model.LSConfig;
import com.gb.ir.util.ByteUtils;
import com.gb.ir.util.PathUtils;
import com.gb.ir.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

//接受由服务器传来的数据，完成某种功能
//一般为需要完成某种功能的本地服务器，比如说打印等。
public class IRLocalServer {
    public static void main(String[] args) throws IOException {
        IRLocalServer irls = new IRLocalServer();
        String configInfo = PathUtils.getResourcesInfo("LSConfig.json");
        LSConfig lsConfig = JSON.parseObject(configInfo, LSConfig.class);
        irls.setUpServer(lsConfig);
    }

    private void setUpServer(LSConfig lsConfig) throws IOException {
        //添加标记，远程服务端根据这个判断是本地服务端还是客户端
        lsConfig.setLsName(assemblyLocalServerName(lsConfig.getLsName()));
        Socket client;
        try {
            client = new Socket(lsConfig.getRsIp(), lsConfig.getRsPort());
            System.out.println("连接服务器成功!");
        } catch (Exception ce) {
            System.out.println("连接服务器失败");
//            ce.printStackTrace();
            return;
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
            bos.write(lsConfig.getLsName().getBytes());
            bos.flush();
            while (true) {
                BufferedOutputStream bfos = null;
                StringBuilder result = new StringBuilder();
                //创建文件
                File file = null;
                if (lsConfig.getIfSave()) {
                    String fileName = "";
                    if (StringUtils.isEmpty(lsConfig.getFileName())) {
                        UUID uuid = UUID.randomUUID();
                        fileName += uuid;
                    } else {
                        fileName = lsConfig.getFileName();
                    }
                    if (!StringUtils.isEmpty(lsConfig.getFileSuffix())) {
                        fileName += "." + lsConfig.getFileSuffix();
                    }

                    File dir = null;
                    if (StringUtils.isEmpty(lsConfig.getSavePath())) {
                        dir = new File(PathUtils.getRootPath() + "output/");
                    } else {
                        String dirPath = lsConfig.getSavePath();
                        if (dirPath.startsWith("/") || dirPath.startsWith(".")) {
                            dirPath = PathUtils.getRootPath() + dirPath.substring(1);
                        }
                        dir = new File(dirPath);
                    }

                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    file = new File(dir, fileName);
//                    System.out.println(file.getAbsoluteFile());
                }
                boolean isStart = false;
                StringBuilder sb = new StringBuilder();
                byte[] bytes = new byte[Constant.BUFFER_BYTE_SIZE];
                int len = 0, start = 0, end = 0;
                while ((len = bis.read(bytes)) > 0) {
                    if (lsConfig.getIfSave()) {
                        if (bfos == null) {
                            bfos = new BufferedOutputStream(new FileOutputStream(file));
                            result.append("保存文件: " + file.getAbsolutePath() + "\n");
                        }
                    }
//                    System.out.println(new String(bytes, 0, len));
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
                                if (lsConfig.getIfSave()) bfos.write(bytes, start, end - start);
//                            System.out.println(new String(bytes, start, end));
                            }
                            break;
                        }
                        //TODO 写入数据
                        sb.append(new String(bytes, start, len - start));
                        if (lsConfig.getIfSave()) bfos.write(bytes, start, len - start);
//                    System.out.println(new String(bytes, start, len - start));
                        start = 0;
                    }
                }
                if (lsConfig.getIfSave()) {
                    //保存到文件
                    bfos.flush();
                    bfos.close();
                }

                //控制台打印查看数据
//                System.out.println(sb.toString());

                //直接执行
                if (lsConfig.isExecutable()) {
                    if (!StringUtils.isEmpty(lsConfig.getExeCommandWithMsg())) {
                        if (lsConfig.getExeCommandWithMsg().startsWith("run")) {
                            Runtime.getRuntime().exec(sb.toString());
                        } else {
                            Runtime.getRuntime().exec(lsConfig.getExeCommandWithMsg() + " " + sb.toString());
                        }
                        result.append("exeCommand: " + lsConfig.getExeCommandWithMsg() + " " + sb.toString() + "\n");
                    }
                    if (!StringUtils.isEmpty(lsConfig.getExeCommandDirectly())) {
                        Runtime.getRuntime().exec(lsConfig.getExeCommandDirectly());
                        result.append("exeCommand: " + lsConfig.getExeCommandDirectly() + "\n");
                    }
                    if (!StringUtils.isEmpty(lsConfig.getExeCommandWithFile())) {
                        Runtime.getRuntime().exec(lsConfig.getExeCommandWithFile() + " " + file.getAbsolutePath());
                        result.append("exeCommand: " + lsConfig.getExeCommandWithFile() + " " + file.getAbsolutePath() + "\n");
                    }
                }
                //
                System.out.print(result);
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
