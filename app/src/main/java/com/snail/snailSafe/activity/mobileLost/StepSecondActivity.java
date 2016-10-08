package com.snail.snailSafe.activity.mobileLost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snail.snailSafe.R;
import com.snail.snailSafe.activity.frame.ChoseContactActivity;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;
import com.snail.snailSafe.utils.LogcatUtils;

/**
 * Created by snail on 2016/9/28.
 */

public class StepSecondActivity extends BaseStepActivity {
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(StepSecondActivity.class);
    private Context mContext;
    private Button btn_nextPage;
    private EditText ed_contact;
    private Button btn_chose_contact;
    private MobileLostSPO mobileLostSPO;
    private Button btn_prevPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mobileLostSPO = new MobileLostSPO(mContext);
        setContentView(R.layout.activity_step_second);
        initUI();
        initData();
    }

    private void initData() {
        ed_contact.setText(mobileLostSPO.getString(Constant.SAFE_TEL_NUM,""));
    }


    private void initUI() {
        btn_nextPage = (Button) findViewById(R.id.btn_nextPage);
        btn_prevPage = (Button) findViewById(R.id.btn_prevPage);
        ed_contact = (EditText) findViewById(R.id.ed_contact);
        btn_chose_contact = (Button) findViewById(R.id.btn_chose_contact);

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

        btn_chose_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ChoseContactActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            String choseTelNum = (String) data.getCharSequenceExtra("contactTelNum");
            choseTelNum = choseTelNum.replace("-","").replace(" ","").trim();
            ed_contact.setText(choseTelNum);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void prevPage() {
        Intent intent  = new Intent(mContext,StepOneActivity.class);
        startActivity(intent);
    }

    @Override
    public void nextPage() {
        String telNum = String.valueOf(ed_contact.getText());
        if(TextUtils.isEmpty(telNum)){
            Toast.makeText(mContext,"请选择安全号码",Toast.LENGTH_SHORT).show();
        }else{
            //写入文件
            mobileLostSPO.putString(Constant.SAFE_TEL_NUM,telNum);
            //跳转下一个界面
            Intent intent  = new Intent(mContext,StepThirdActivity.class);
            startActivity(intent);
            finish();
        }
    }
}


