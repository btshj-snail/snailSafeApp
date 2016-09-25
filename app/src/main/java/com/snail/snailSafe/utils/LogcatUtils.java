package com.snail.snailSafe.utils;

import android.util.Log;

/**
 * Created by snail on 2016/9/21.
 * 日志工具类
 * 通过isOpenLog可以控制是否开启日志输出。
 * 在开发中可以将其设为true。
 * 在正式环境中可以将其设为false，已关闭日志输出。
 * v 0.0.1
 * 在后面的版本可以将这个设置到xml文件中。
 */

public class LogcatUtils {
    private static final boolean IS_OPEN_LOG = true;
    private static final String DEFAULT_STR = "*************************************************************************************************************************************/n";
    private String targetClass;

    private LogcatUtils(Class clazz){
        this.targetClass = clazz.getName();
    }

    public static LogcatUtils getLogCat(Class clazz){
            return new LogcatUtils(clazz);
    }


    public void e(String message){
        if(IS_OPEN_LOG){
            Log.e(targetClass,DEFAULT_STR+message);
        }

    }
    public void e(String tag ,String message){
        if(IS_OPEN_LOG) {
            Log.e(tag, DEFAULT_STR+message);
        }
    }

    public void w(String message){
        if(IS_OPEN_LOG) {
            Log.w(targetClass,DEFAULT_STR+ message);
        }
    }
    public void w(String tag ,String message){
        Log.w(tag,DEFAULT_STR+message);
    }

    public void i(String message){
        if(IS_OPEN_LOG) {
            Log.i(targetClass, DEFAULT_STR+message);
        }
    }
    public void i(String tag ,String message){
        if(IS_OPEN_LOG) {
            Log.i(tag,DEFAULT_STR+ message);
        }
    }

    public void d(String message){
        if(IS_OPEN_LOG) {
            Log.d(targetClass, DEFAULT_STR+message);
        }
    }
    public void d(String tag ,String message){
        if(IS_OPEN_LOG) {
            Log.d(tag, DEFAULT_STR+message);
        }
    }

    public void v(String message){
        if(IS_OPEN_LOG) {
            Log.v(targetClass,DEFAULT_STR+ message);
        }
    }
    public void v(String tag ,String message){
        if(IS_OPEN_LOG) {
            Log.v(tag, DEFAULT_STR+message);
        }
    }
}
