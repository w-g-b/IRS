package com.gb.ir;

public class Constant {
    private static final String MESSAGE_HEAD = "=====MessageHead=====";
    public static final byte[] MESSAGE_HEAD_BYTES = MESSAGE_HEAD.getBytes();
    private static final String MESSAGE_END = "=====MessageEND=====";
    public static final byte[] MESSAGE_END_BYTES = MESSAGE_END.getBytes();
    public static int BUFFER_BYTE_SIZE = 1024;
}
