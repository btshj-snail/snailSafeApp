package com.snail.snailSafe.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.utils.LogcatUtils;

public class StartAppActivity extends AppCompatActivity {
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(StartAppActivity.class);
    private Context mContext;
    private TextView tv_versionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = StartAppActivity.this;
        setContentView(R.layout.activity_start_app);
        initView();
        initData();
    }

    private void initData() {
        tv_versionName.setText("版本："+obtainVersionName());
    }

    /**
     * 获取系统的版本名称
     * @return 版本名称 返回null 表示异常
     */
    private String obtainVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initView() {
        tv_versionName = (TextView) findViewById(R.id.tv_version_name);
    }
}
