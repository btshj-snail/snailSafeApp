package com.snail.snailSafe.activity.mobileLost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;
import com.snail.snailSafe.utils.LogcatUtils;
import com.snail.snailSafe.view.ImageTextView;
import com.snail.snailSafe.view.SettingItemArrowView;

/**
 * Created by snail on 2016/9/26.
 */

public class MobileLostActivity extends AppCompatActivity implements View.OnClickListener{
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(MobileLostActivity.class);
    private Context mContext;
    private SettingItemArrowView mRestConfigInfo;
    private SettingItemArrowView mChangeSecretKey;
    private SettingItemArrowView mChangeSafeConstant;
    private SettingItemArrowView mHowUseMobileLostFunction;
    private MobileLostSPO mobileLostSPO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MobileLostActivity.this;
        MobileLostSPO mobileLostSPO = new MobileLostSPO(mContext);
       boolean isCompleteSet =  mobileLostSPO.getBoolean(Constant.IS_OPEN_LOST_MOBILE,false);
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
        mRestConfigInfo = (SettingItemArrowView) findViewById(R.id.restConfigInfo);
        mChangeSecretKey = (SettingItemArrowView) findViewById(R.id.changeSecretKey);
        mChangeSafeConstant = (SettingItemArrowView) findViewById(R.id.changeSafeConstant);
        mHowUseMobileLostFunction = (SettingItemArrowView) findViewById(R.id.howUseMobileLostFunction);
        mobileLostSPO = new MobileLostSPO(mContext);
        mRestConfigInfo.setOnClickListener(this);
        mChangeSecretKey.setOnClickListener(this);
        mChangeSafeConstant.setOnClickListener(this);
        mHowUseMobileLostFunction.setOnClickListener(this);
    }


    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.restConfigInfo:restConfigInfo();break;
            case R.id.changeSecretKey:changeSecretKey();break;
            case R.id.changeSafeConstant:changeSafeConstant();break;
            case R.id.howUseMobileLostFunction:howUseMobileLostFunction();break;
        }
    }


    /**
     * 重置设置信息
     */
    private void restConfigInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog alertDialog = builder.create();
        View view = View.inflate(mContext,R.layout.dialog_confirm,null);
        alertDialog.setView(view);
        alertDialog.show();
        ImageTextView dialogConfirmTitle = (ImageTextView) view.findViewById(R.id.dialogConfirmTitle);
        TextView dialogConfirmDesc = (TextView) view.findViewById(R.id.dialogConfirmDesc);

        Button confirmBtn = (Button) view.findViewById(R.id.btn_confirm);
        Button cancelBtn = (Button) view.findViewById(R.id.btn_cancel);
        dialogConfirmTitle.setText("提示");
        dialogConfirmDesc.setText("您确定要重置设置信息吗？");

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileLostSPO.remove(Constant.SIM_CODE);
                mobileLostSPO.remove(Constant.SAFE_TEL_NUM);
                mobileLostSPO.remove(Constant.IS_OPEN_LOST_MOBILE);
                Intent intent = new Intent(mContext,StepOneActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });




    }

    /**
     * 跳转到功能详细介绍
     */
    private void howUseMobileLostFunction() {
        Intent intent = new Intent(mContext,FunctionIntroduceActivity.class);
        startActivity(intent);
    }

    /**
     * 更换安全联系人
     */
    private void changeSafeConstant() {
        Intent intent = new Intent(mContext,StepSecondActivity.class);
        startActivity(intent);
    }

    /**
     * 更换密钥
     */
    private void changeSecretKey() {
        Intent intent = new Intent(mContext,ChangeSecretKeyActivity.class);
        startActivity(intent);
    }
}
