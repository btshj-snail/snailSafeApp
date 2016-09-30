package com.snail.snailSafe.activity.mobileLost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.snail.snailSafe.R;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;
import com.snail.snailSafe.view.SettingItemView;

/**
 * Created by snail on 2016/9/28.
 */

public class StepThirdActivity extends BaseStepActivity {
    private Context mContext;
    private Button btn_nextPage;
    private Button btn_prevPage;
    private SettingItemView siv_open_lost_mobile;
    private MobileLostSPO mobileLostSPO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_step_third);

        initUI();
        initData();
    }

    private void initData() {
        mobileLostSPO = new MobileLostSPO(mContext);
        mobileLostSPO.getBoolean(Constant.IS_OPEN_LOST_MOBILE,false);

    }

    private void initUI() {
        btn_nextPage = (Button) findViewById(R.id.btn_nextPage);
        btn_prevPage = (Button) findViewById(R.id.btn_prevPage);
        siv_open_lost_mobile = (SettingItemView) findViewById(R.id.siv_open_lost_mobile);

        btn_prevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevPage();
            }
        });



        btn_nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        siv_open_lost_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = siv_open_lost_mobile.isChecked();
                    siv_open_lost_mobile.setChecked(checked);
            }
        });
    }

    @Override
    public void prevPage() {
        Intent intent = new Intent(mContext,StepSecondActivity.class);
        startActivity(intent);
    }

    @Override
    public void nextPage() {
        if(mobileLostSPO.getBoolean(Constant.IS_OPEN_LOST_MOBILE,false)){
            Intent intent = new Intent(mContext,MobileLostActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(mContext,"请勾选开启手机防盗功能",Toast.LENGTH_SHORT).show();
        }
    }
}
