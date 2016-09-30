package com.snail.snailSafe.sharedPerfences;

import android.content.Context;

/**
 * Created by snail on 2016/9/28.
 */

public class MobileLostSPO extends SharedPreferencesOperate {
    private static final String FILE_NAME = "mobileLostConfig";
    public MobileLostSPO(Context ctx) {
        super(ctx, FILE_NAME);
    }
}
