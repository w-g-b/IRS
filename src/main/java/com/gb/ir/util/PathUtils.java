package com.gb.ir.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class PathUtils {
    public final static String RESOURCE_PATH = "resources/";

    public static void main(String[] args) throws IOException {
//        System.out.println(getResourcesInfo("RSConfig.json"));
        System.out.println(getResourcesInfo("LSConfig.json"));
//        System.out.println(PathUtils.class.getResource("/"));
        System.out.println(getResourcesRootPath());
        System.out.println(getRootPath());

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

    public static InputStreamReader getResourcesFileStream(String fileName) {
        return new InputStreamReader(PathUtils.class.getResourceAsStream("/../resources/" + fileName));

    }

    public static String getResourcesRootPath() {
        URL url = PathUtils.class.getResource("/../" + RESOURCE_PATH);
        String protocol = url.getProtocol();
        return url.toString().substring(protocol.length() + 2);
    }

    public static String getRootPath() {
        return getResourcesRootPath().replace(RESOURCE_PATH, "");
    }
}
