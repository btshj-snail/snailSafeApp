package com.snail.snailSafe.activity.mobileLost;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.snail.snailSafe.R;

/**
 * Created by snail on 2016/9/28.
 */

public class StepThirdActivity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_step_third);
    }
}
