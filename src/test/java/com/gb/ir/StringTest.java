package com.gb.ir;

import com.gb.ir.util.ByteUtils;

@SuppressWarnings("Duplicates")
public class StringTest {
    public static boolean isMatch(String string) {
        return string.matches("^--.+--$");
    }

    public static void trimString(byte[] bytes) {
        boolean isStart = false;
        StringBuilder sb = new StringBuilder();
        int len = bytes.length, start = 0, end = 0;
        if (!isStart) {
//                  判断是否含有开始标志，如果有，删除bytes内的开始信息（即为start赋值），并且设置isStart为true
            if ((start = ByteUtils.findBytes(bytes, Constant.MESSAGE_HEAD_BYTES)) >= 0) {
                isStart = true;
                start += Constant.MESSAGE_HEAD_BYTES.length;
                if (start == len) {
                    start = 0;
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
//                            System.out.println(new String(bytes, start, end));
                }
            }
            //TODO 写入数据
//            sb.append(new String(bytes, start, len - start));
//                    System.out.println(new String(bytes, start, len - start));
            start = 0;
        }
        System.out.println(sb.toString());

    }

    public static void main(String[] args) {
        trimString("=====MessageHead=====calc=====MessageEND=====".getBytes());
//        System.out.println(isMatch("---1--"));
    }
}
