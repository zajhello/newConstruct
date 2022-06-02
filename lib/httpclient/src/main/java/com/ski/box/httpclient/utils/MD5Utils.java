package com.ski.box.httpclient.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    /**
     * MD5加密
     *
     * @param string 字符串
     * @return 加密后的字符串
     */
    public static String getMd5(String string) {
        if (string == null) return null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        if (md == null) return null;
        md.update(string.getBytes());
        byte[] bytes = md.digest();
        StringBuilder buf = new StringBuilder();
        for (int aByte : bytes) {
            int i = aByte;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        //32位加密
        return buf.toString();
    }

    /**
     * 旧版本 MD5加密
     */
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static String toMD5(String str) {
        try {
            return toHex(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"))).toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder str = new StringBuilder(bytes.length * 2);
        final int fifteen = 0x0f;			//十六进制中的 15
        for (byte b : bytes) {				//byte 为 32 位
            str.append(HEX_CHARS[(b >> 4) & fifteen]);		//获取第 25 位到第 28 位的二进制数
            str.append(HEX_CHARS[b & fifteen]);				//获取第 29 位到第 32 位的二进制数
        }
        return str.toString();
    }
}
