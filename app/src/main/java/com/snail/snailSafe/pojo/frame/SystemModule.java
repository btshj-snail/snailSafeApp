package com.snail.snailSafe.pojo.frame;

import android.app.Activity;

/**
 * Created by snail on 2016/9/26.
 */

public class SystemModule {

    /**
     * 模块id
     */
    private int id;

    /**
     * 模块名称
     */
    private String moduleTitle;

    /**
     * 模块图片
     */
    private int moduleIcon;

    /**
     * 模块对应的初始活动类
     */
    private Class moduleInitActivity;

    public SystemModule(String moduleTitle, int moduleIcon, Class moduleInitActivity) {
        this.moduleTitle = moduleTitle;
        this.moduleIcon = moduleIcon;
        this.moduleInitActivity = moduleInitActivity;
    }

    public SystemModule(String moduleTitle, int moduleIcon) {
        this.moduleTitle = moduleTitle;
        this.moduleIcon = moduleIcon;
    }

    public SystemModule(int id, String moduleTitle, int moduleIcon, Class moduleInitActivity) {
        this.id = id;
        this.moduleTitle = moduleTitle;
        this.moduleIcon = moduleIcon;
        this.moduleInitActivity = moduleInitActivity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public int getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(int moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public Class getModuleInitActivity() {
        return moduleInitActivity;
    }

    public void setModuleInitActivity(Class moduleInitActivity) {
        this.moduleInitActivity = moduleInitActivity;
    }
}
