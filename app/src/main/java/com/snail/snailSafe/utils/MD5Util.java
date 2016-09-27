package com.snail.snailSafe.utils;

import android.os.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by snail on 2016/9/27.
 * MD5加密算法
 */

public class MD5Util {

    private static LogcatUtils logcatUtils = LogcatUtils.getLogCat(MD5Util.class);

    private static final  String SALT="hahajiushirangnijiexibuchulai";

    public static String encoder(String str) {
        String willEncoderStr = str+SALT;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bs = md.digest(willEncoderStr.getBytes());
            StringBuffer sb = new StringBuffer();
            for(byte b : bs){
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if(hexString.length()<2){
                    hexString = "0"+hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logcatUtils.e("MD5加密失败；"+e.getMessage());
        }
        return "";
    }
}
