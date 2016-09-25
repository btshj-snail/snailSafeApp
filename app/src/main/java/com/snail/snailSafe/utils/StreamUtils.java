package com.snail.snailSafe.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by runningSnail on 2016/9/25.
 * 流处理工具集合
 */
public class StreamUtils {
    /**
     * 流转字符串
     * @param in
     * @return  字符串  返回null 表示异常
     */
    public static String stream2String(InputStream in) {

      ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] ary  = new byte[1024];

        int temp = -1;
        try {
            while((temp = in.read(ary))!=-1){
                bos.write(ary,0,temp);
            }
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
