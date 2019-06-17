package com.example.a11708.graduationproject.Utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String md5(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            //字符串中每个字符转换为对应的ASCII值作为字节数组中的一个元素
            byte[] bytes = md5.digest(str.getBytes());
            String result = "";
            for (byte b : bytes) {
                //将哈希字节数组的每个元素通过0xff与运算转换为两位无符号16进制的字符串
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    //将不足两位的无符号16进制的字符串前面加0
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return "";
    }

}
