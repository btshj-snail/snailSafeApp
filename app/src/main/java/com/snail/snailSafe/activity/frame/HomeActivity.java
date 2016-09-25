package com.snail.snailSafe.activity.frame;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.snail.snailSafe.R;
import com.snail.snailSafe.utils.LogcatUtils;

/**
 * Created by runningSnail on 2016/9/25.
 */
public class HomeActivity extends AppCompatActivity{
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(HomeActivity.class);
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = HomeActivity.this;
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {

    }
}
