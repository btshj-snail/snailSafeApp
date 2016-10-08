package com.snail.snailSafe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.snailSafe.R;

/**
 * Created by snail on 2016/10/8.
 */

public class SettingItemArrowView extends RelativeLayout{
    private Context mContext;
    private String mTitle;

    public SettingItemArrowView(Context context) {
        this(context,null);
    }

    public SettingItemArrowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemArrowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initCustomAttr(attrs);
        View view = View.inflate(mContext,R.layout.item_setting_arrow,this);
        TextView textView = (TextView) findViewById(R.id.tv_set_title);
        textView.setText(mTitle);
    }

    private void initCustomAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ItemSettingArrowView);
        mTitle = typedArray.getString(R.styleable.ItemSettingArrowView_descContent);

    }


}
