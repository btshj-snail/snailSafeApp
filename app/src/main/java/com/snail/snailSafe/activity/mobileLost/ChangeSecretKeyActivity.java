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
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.activity.frame.HomeActivity;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;
import com.snail.snailSafe.utils.MD5Util;

/**
 * Created by snail on 2016/10/8.
 */

public class ChangeSecretKeyActivity extends AppCompatActivity {
    private Context mContext;
    private EditText et_old_secret_key;
    private EditText et_new_secret_key;
    private EditText et_confirm_secret_key;
    private Button btn_update_secret_key;
    private MobileLostSPO mobileLostSPO;
    private TextView tv_message_area;
    private ImageView iv_go_back;
    private boolean updatedPsdFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_change_secret_key);
        mobileLostSPO = new MobileLostSPO(mContext);
        initUI();

    }

    private void initUI() {
        et_old_secret_key = (EditText) findViewById(R.id.et_old_secret_key);
        et_new_secret_key = (EditText) findViewById(R.id.et_new_secret_key);
        iv_go_back = (ImageView) findViewById(R.id.iv_go_back);
        tv_message_area = (TextView) findViewById(R.id.tv_message_area);
        et_confirm_secret_key = (EditText) findViewById(R.id.et_confirm_secret_key);
        btn_update_secret_key = (Button) findViewById(R.id.btn_update_secret_key);

        iv_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(updatedPsdFlag){
                    intent = new Intent(mContext, HomeActivity.class);
                }else{
                    intent = new Intent(mContext, MobileLostActivity.class);
                }
                startActivity(intent);
            }
        });

        btn_update_secret_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = validate();
                if(TextUtils.isEmpty(message)){
                    String newPsd =  MD5Util.encoder(String.valueOf(et_new_secret_key.getText()));
                    mobileLostSPO.putString(Constant.MOBILE_LOST_PSD,newPsd);
                    updatedPsdFlag = true;
                    showMessage(R.color.lightgreen,"密钥更改成功，请牢记您的密钥！");
                }else{
                    showMessage(R.color.lightsalmon,message);
                }
            }
        });
        et_old_secret_key.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    clearMessage();
            }
        });
        et_new_secret_key.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    clearMessage();
            }
        });
        et_confirm_secret_key.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    clearMessage();
            }
        });
    }

    private void showMessage(int color,String message){
        tv_message_area.setTextColor(getResources().getColor(color));
        tv_message_area.setText(message);
    }

    private void clearMessage(){
        tv_message_area.setTextColor(getResources().getColor(R.color.white));
        tv_message_area.setText("");
    }
    private String validate() {
        String et_old = String.valueOf(et_old_secret_key.getText());
        if (TextUtils.isEmpty(et_old)) {
            return "旧密钥不能为空";
        }
        String et_new = String.valueOf(et_new_secret_key.getText());
        if (TextUtils.isEmpty(et_new)) {
            return "新密钥不能为空";
        }
        String et_confirm = String.valueOf(et_confirm_secret_key.getText());
        if (TextUtils.isEmpty(et_new)) {
            return "确认密钥不能为空";
        }
        String oldPsd = mobileLostSPO.getString(Constant.MOBILE_LOST_PSD, "");
        if (!oldPsd.equals(MD5Util.encoder(et_old))) {
            return "输入的旧密钥不正确";
        }
        if (et_old.equals(et_new)) {
            return "新老密钥不能一样";
        }
        if(!et_confirm.equals(et_new)){
            return "确认密钥和新密钥不一致";
        }
        return "";
    }
}
