package com.snail.snailSafe.activity.frame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.activity.mobileLost.MobileLostActivity;
import com.snail.snailSafe.adapter.frame.SystemModulesAdapter;
import com.snail.snailSafe.pojo.frame.SystemModule;
import com.snail.snailSafe.utils.LogcatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by runningSnail on 2016/9/25.
 */
public class HomeActivity extends AppCompatActivity{
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(HomeActivity.class);
    private Context mContext;
    private GridView mGridView;
    private String[] mTitleAry;
    private int[] mIconAry;
    private List<SystemModule> systemModuleList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = HomeActivity.this;
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initData() {
        systemModuleList = new ArrayList<>();
        systemModuleList.add(new SystemModule("手机防盗",R.drawable.home_safe, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("通信卫士",R.drawable.home_callmsgsafe, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("软件管理",R.drawable.home_apps, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("进程管理",R.drawable.home_taskmanager, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("流量统计",R.drawable.home_netmanager, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("手机杀毒",R.drawable.home_trojan, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("缓存清理",R.drawable.home_sysoptimize, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("高级工具",R.drawable.home_tools, MobileLostActivity.class));
        systemModuleList.add(new SystemModule("设置中心",R.drawable.home_settings, MobileLostActivity.class));

        mGridView.setAdapter(new SystemModulesAdapter(mContext, systemModuleList) );


    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gv_home);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SystemModule systemModule =  systemModuleList.get(position);
                if(systemModule.getModuleInitActivity()!=null){
                    Intent intent = new Intent(mContext,systemModule.getModuleInitActivity());
                    startActivity(intent);
                }
            }
        });
    }
}
