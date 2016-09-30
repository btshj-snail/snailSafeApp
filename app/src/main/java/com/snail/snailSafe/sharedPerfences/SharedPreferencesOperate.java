package com.snail.snailSafe.sharedPerfences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by snail on 2016/9/27.
 */

public class SharedPreferencesOperate {
    private Context context;

    private String fileName;
    private SharedPreferences sharedPreferences;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getFileName() {
        return fileName;
    }

    public SharedPreferencesOperate(Context ctx, String fileName) {
        this.context = ctx;
        this.fileName = fileName;
    }

    /**
     * 写入boolean值
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        if (sharedPreferences == null)
            sharedPreferences = this.getContext().getSharedPreferences(this.getFileName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 读取boolean值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        if (sharedPreferences == null)
            sharedPreferences = this.getContext().getSharedPreferences(this.getFileName(), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 写入数字型值
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        if (sharedPreferences == null)
            sharedPreferences = this.getContext().getSharedPreferences(this.getFileName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 读取数字型值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public int getInt(String key, int defaultValue) {
        if (sharedPreferences == null)
            sharedPreferences = this.getContext().getSharedPreferences(this.getFileName(), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 写入String型值
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        if (sharedPreferences == null)
            sharedPreferences = this.getContext().getSharedPreferences(this.getFileName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 读取String型值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key, String defaultValue) {
        if (sharedPreferences == null)
            sharedPreferences = this.getContext().getSharedPreferences(this.getFileName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 移除条目
     * @param key
     */
    public void remove(String key) {
        if (sharedPreferences == null)
            sharedPreferences = this.getContext().getSharedPreferences(this.getFileName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }
}
