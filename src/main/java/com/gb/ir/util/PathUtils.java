package com.gb.ir.util;

import java.io.*;
import java.net.URL;

public class PathUtils {
    public final static String RESOURCE_PATH = "resources\\";

    public static void main(String[] args) throws IOException {
        System.out.println(PathUtils.class.getResource("/"));
        System.out.println(getResourcesRootPath());
        System.out.println(getRootPath());
        System.out.println(getResourcesInfo("RSConfig.json"));
//        System.out.println(getResourcesInfo("RSConfig.json"));
//        System.out.println(getResourcesInfo("LSConfig.json"));
//        System.out.println(PathUtils.class.getResource("/"));
//        System.out.println(getResourcesRootPath());
//        System.out.println(isRightAbsolutePath("Z:/aaa/ccc"));

    }

    public static String getResourcesInfo(String fileName) throws IOException {
        InputStreamReader isr = getResourcesFileStream(fileName);
        char[] chars = new char[1024];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        while ((len = isr.read(chars)) > 0) {
            sb.append(chars, 0, len);
        }
        return sb.toString();
    }

    public static InputStreamReader getResourcesFileStream(String fileName) throws FileNotFoundException {
//        return PathUtils.class.getResourceAsStream(fileName);
        File file = new File(getResourcesRootPath() + fileName);
        return new InputStreamReader(new FileInputStream(file));

    }

    public static String getResourcesRootPath() {
//        URL url = PathUtils.class.getResource("/../resources/");
//        String protocol = url.getProtocol();
//        return url.toString().substring(protocol.length() + 2);
        URL url = PathUtils.class.getResource("/");
        String protocol = url.getProtocol();
        File file = new File(url.toString().substring(protocol.length() + 2));
        return file.getParent() + "\\resources\\";
    }

    public static String getRootPath() {
        return getResourcesRootPath().replace(RESOURCE_PATH, "");
    }

}
