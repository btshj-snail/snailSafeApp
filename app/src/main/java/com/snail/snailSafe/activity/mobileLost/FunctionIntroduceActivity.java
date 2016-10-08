package com.snail.snailSafe.activity.mobileLost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.snail.snailSafe.R;

/**
 * Created by snail on 2016/10/8.
 */

public class FunctionIntroduceActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext  = this;
        setContentView(R.layout.activity_function_introduce);
        initUI();
    }

    private void initUI() {
        TextView tv_how_get_position = (TextView) findViewById(R.id.tv_how_get_position);
        TextView tv_how_del_constant = (TextView) findViewById(R.id.tv_how_del_constant);
        TextView tv_how_del_picture = (TextView) findViewById(R.id.tv_how_del_picture);
        TextView tv_how_obtain_status = (TextView) findViewById(R.id.tv_how_obtain_status);

        tv_how_get_position.setOnClickListener(this);
        tv_how_del_constant.setOnClickListener(this);
        tv_how_del_picture.setOnClickListener(this);
        tv_how_obtain_status.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_how_get_position:skipPage(Introduce_HowGetPositionActivity.class);break;
            case R.id.tv_how_del_constant:skipPage(Introduce_HowDelConstantActivity.class);break;
            case R.id.tv_how_del_picture:skipPage(Introduce_HowDelPictureActivity.class);break;
            case R.id.tv_how_obtain_status:skipPage(Introduce_HowObtainStatusActivity.class);break;
        }
    }

    private void skipPage(Class clazz){
        Intent intent = new Intent(mContext,clazz);
        startActivity(intent);
    }
}
