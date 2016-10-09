package com.snail.snailSafe.activity.help;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snail.snailSafe.R;
import com.snail.snailSafe.businessLogic.lostMobile.SendMessage_lost;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by snail on 2016/10/9.
 */
public class LostMobilePositionActivity extends AppCompatActivity{

    private Context mContext;
    private EditText et_lost_mobile_secret_key;
    private EditText et_lost_mobile_num;
    private Button btn_obtain_lost_mobile_position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_lost_mobile_position);
        initUI();
    }

    private void initUI() {
        et_lost_mobile_secret_key = (EditText) findViewById(R.id.et_lost_mobile_secret_key);
        et_lost_mobile_num = (EditText) findViewById(R.id.et_lost_mobile_num);
        btn_obtain_lost_mobile_position = (Button) findViewById(R.id.btn_obtain_lost_mobile_position);
        btn_obtain_lost_mobile_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = validate();
                if(TextUtils.isEmpty(message)){
                    String phone = String.valueOf(et_lost_mobile_num.getText());
                    String secretKey = String.valueOf(et_lost_mobile_secret_key.getText());
                    sendMessage(phone,secretKey);
                    MobileLostSPO mobileLostSPO = new MobileLostSPO(mContext);
                    mobileLostSPO.putString(Constant.HELP_OBJECT_TEL_NUM,phone);
                }else{
                    Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 发送短信
     * @param phone
     * @param secretKey
     */
    private void sendMessage(String phone, String secretKey) {
        SendMessage_lost sendMessage = new SendMessage_lost(mContext);
        sendMessage.sendSmsByMode(phone,secretKey,SendMessage_lost.BODY_OBTAIN_POSITION);
    }

    private String validate() {
        String phone = String.valueOf(et_lost_mobile_num.getText());
        String secretKey = String.valueOf(et_lost_mobile_secret_key.getText());
        if(TextUtils.isEmpty(phone)){
            return "请输入丢失手机的号码";
        }
        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        if(!m.find()){
            return "请输入一个正确的电话号码";
        }
        if(TextUtils.isEmpty(secretKey)){
            return "请输入丢失手机的安全密钥";
        }
        return null;
    }
}
