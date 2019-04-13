package com.gb.browser;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyBrowser {
    public static void main(String[] args) throws IOException {
        Socket browser = new Socket("127.0.0.1", 80);
        BufferedOutputStream bos = new BufferedOutputStream(browser.getOutputStream());
        BufferedInputStream bis = new BufferedInputStream(browser.getInputStream());
        bos.write(("GET /a.jpg HTTP/1.1\r\n" +
                "Host: 127.0.0.1:80\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/71.0.3578.98 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: zh-CN,zh;q=0.9\r\n" +
                "\r\n").getBytes());

        bos.flush();
        int len = 0;
        byte[] bytes = new byte[2048];
        while (true) {
            while ((len = bis.read(bytes)) > 0) {
                System.out.println(new String(bytes, 0, len));
            }
        }
    }
//        测试百度
//        Socket browser = new Socket("baidu.com", 80);

//        bos.write(("GET / HTTP/1.1\r\n" +
//                "cache-control: no-cache\r\n" +
//                "Postman-Token: 94b30c7e-095b-496e-a5b5-9308fbf01c79\r\n" +
//                "User-Agent: PostmanRuntime/7.6.1\r\n" +
//                "Accept: */*\r\n" +
//                "Host: baidu.com\r\n" +
//                "accept-encoding: gzip, deflate\r\n" +
//                "content-type: multipart/form-data; boundary=--------------------------119855980562193837159749\n" +
//                "content-length: 0\r\n" +
//                "Connection: keep-alive\r\n\r\n").getBytes());
}
