package com.gb.ir.util;

public class ByteUtils {
    //判断mainBytes是否包含subBytes，如果包含，则返回mainBytes和subBytes相同部分的开始位置
    public static int findBytes(byte[] mainBytes, byte[] subBytes) {
        int i = 0, j = 0;
        while (i < mainBytes.length) {
            if (mainBytes[i] == subBytes[j]) {
                i++;
                j++;
                if (j == subBytes.length) {
                    break;
                }
            } else {
                i -= j - 1;
                j = 0;
            }
        }
        if (j == subBytes.length) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
//        byte[] bytes1 = {12, 12, 14, 14, 51, 12};
//        byte[] bytes2 = {14, 51, 12};
        byte[] bytes1 = {1, 12, 1, 1, 14, 51, 12, 123, 13};
        byte[] bytes2 = {14, 51, 12, 123, 13, 123};
        System.out.println(findBytes(bytes1, bytes2));
    }
}
