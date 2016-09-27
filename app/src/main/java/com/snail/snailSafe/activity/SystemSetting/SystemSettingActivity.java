package com.snail.snailSafe.activity.systemSetting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.snail.snailSafe.R;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.SettingSPO;
import com.snail.snailSafe.utils.LogcatUtils;
import com.snail.snailSafe.view.SettingItemView;

/**
 * Created by snail on 2016/9/26.
 */

public class SystemSettingActivity extends AppCompatActivity {
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(SystemSettingActivity.class);
    private Context mContext;
    private SettingSPO mSp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = SystemSettingActivity.this;
        setContentView(R.layout.activity_setting);
        mSp = new SettingSPO(mContext);
        initUpdateItem();
        initTelNumAddressItem();
        initData();
    }

    private void initData() {
    }

    /**
     * 电话归属地查询 设置 条目
     */
    private void initTelNumAddressItem() {
        final SettingItemView telItem = (SettingItemView) findViewById(R.id.siv_tel_num_address);

        //获取配置文件中 是否自动更新属性的值，并且给予默认值为false
        final boolean isQueryTelAddress = mSp.getBoolean(Constant.IS_QUERY_TEL_ADDRESS,false);
        telItem.setChecked(isQueryTelAddress);

        telItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nowStatus = telItem.isChecked();
                telItem.setChecked(!nowStatus);
                mSp.putBoolean(Constant.IS_QUERY_TEL_ADDRESS,!nowStatus);
            }
        });
    }


    /**
     * 系统自动更新设置 条目
     */
    private void initUpdateItem() {
        final SettingItemView settingItemView = (SettingItemView) findViewById(R.id.siv_update);

        //获取配置文件中 是否自动更新属性的值，并且给予默认值为false
        final boolean isAutoUpdate = mSp.getBoolean(Constant.IS_AUTO_UPDATE,false);
        settingItemView.setChecked(isAutoUpdate);

        settingItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nowStatus = settingItemView.isChecked();
                settingItemView.setChecked(!nowStatus);
                //把是否自动更新的值，写入配置文件中
                mSp.putBoolean(Constant.IS_AUTO_UPDATE,!nowStatus);
            }
        });
    }
}
