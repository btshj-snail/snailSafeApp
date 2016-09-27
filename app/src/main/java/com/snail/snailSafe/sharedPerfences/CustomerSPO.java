package com.snail.snailSafe.sharedPerfences;

import android.content.Context;

/**
 * Created by snail on 2016/9/27.
 * 用于记录用户操作app的一些信息
 */

public class CustomerSPO extends SharedPreferencesOperate {
    private static final String FILE_NAME = "customerInfo";
    public CustomerSPO(Context ctx) {
        super(ctx, FILE_NAME);
    }
}
