package com.snail.snailSafe.activity.mobileLost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.snail.snailSafe.R;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;
import com.snail.snailSafe.utils.LogcatUtils;
import com.snail.snailSafe.view.SettingItemView;

/**
 * Created by snail on 2016/9/28.
 */

public class StepOneActivity extends BaseStepActivity {
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(StepOneActivity.class);
    private Context mContext;
    private SettingItemView siv_bindSim;
    private MobileLostSPO mobileLostSPO;
    private Button btn_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mobileLostSPO = new MobileLostSPO(mContext);
        setContentView(R.layout.activity_step_one);
        initUI();
        initData();
    }



    private void initUI() {
        siv_bindSim = (SettingItemView) findViewById(R.id.siv_bindSim);
        btn_next = (Button) findViewById(R.id.btn_nextPage);
        siv_bindSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = siv_bindSim.isChecked();
                siv_bindSim.setChecked(!checked);
                if(!checked){
                    TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String simCode = telManager.getSimSerialNumber();
                    mobileLostSPO.putString(Constant.SIM_CODE,simCode);
                }else{
                    mobileLostSPO.remove(Constant.SIM_CODE);
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
    }

    private void initData() {
        boolean isHasSimCode = TextUtils.isEmpty(mobileLostSPO.getString(Constant.SIM_CODE,""))?false:true;
        siv_bindSim.setChecked(isHasSimCode);
    }


    @Override
    public void prevPage() {

    }

    @Override
    public void nextPage() {
        String simCode = mobileLostSPO.getString(Constant.SIM_CODE, "");
        if(TextUtils.isEmpty(simCode)){
            Toast.makeText(mContext,"请绑定SIM卡",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(mContext,StepSecondActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
