package com.snail.snailSafe.activity.mobileLost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.snail.snailSafe.R;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;
import com.snail.snailSafe.utils.LogcatUtils;

/**
 * Created by snail on 2016/9/26.
 */

public class MobileLostActivity extends AppCompatActivity {
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(MobileLostActivity.class);
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MobileLostActivity.this;
        MobileLostSPO mobileLostSPO = new MobileLostSPO(mContext);
       boolean isCompleteSet =  mobileLostSPO.getBoolean(Constant.COMPLETE_SET,false);

        if (isCompleteSet) {
            setContentView(R.layout.activity_mobile_lost);
            initView();
            initData();
        }else{
            Intent intent = new Intent(mContext,StepOneActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {

    }

    private void initData() {

    }
}
