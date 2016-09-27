package com.snail.snailSafe.sharedPerfences;

import android.content.Context;

/**
 * Created by snail on 2016/9/27.
 * 系统设置对应的配置文件的相关操作
 */

public class SettingSPO extends SharedPreferencesOperate {

    private static final String FILE_NAME="systemSettingConfig";

    public SettingSPO(Context ctx) {
        super(ctx, FILE_NAME);
    }
}
