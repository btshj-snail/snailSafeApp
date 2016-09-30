package com.snail.snailSafe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.snailSafe.R;

/**
 * Created by snail on 2016/9/27.
 * 系统设置中每个栏目的view
 */

public class SettingItemView extends RelativeLayout {
    private static final String NAME_SPACE = "http://schemas.android.com/apk/res-auto";
    private Context mContext;
    private String mTitle;
    private String mDescOn;
    private String mDescOff;

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initCustomAttrs(attrs);
        View view = View.inflate(context, R.layout.setting_item,this);
        TextView textView = (TextView) findViewById(R.id.tv_set_title);
        textView.setText(mTitle);
    }

    /**
     * 获取自定义属性
     * @param attrs
     */
    private void initCustomAttrs(AttributeSet attrs) {
        TypedArray typedArray =mContext.obtainStyledAttributes(attrs,R.styleable.SettingItemView);
        mTitle = typedArray.getString(R.styleable.SettingItemView_descTitle);
        mDescOn = typedArray.getString(R.styleable.SettingItemView_descOn);
        mDescOff = typedArray.getString(R.styleable.SettingItemView_descOff);

//        mTitle = attrs.getAttributeValue(NAME_SPACE,"siv_title");
//        mDescOn = attrs.getAttributeValue(NAME_SPACE,"siv_descOn");
//        mDescOff = attrs.getAttributeValue(NAME_SPACE,"siv_descOff");
    }

    /**
     * 条目是否被选中
     * @return
     */
    public boolean isChecked(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_check_box);
        return checkBox.isChecked();
    }

    /**
     * 设置条目的选中状态
     * @param checked
     */
    public void setChecked(boolean checked){
        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_check_box);
        TextView textView = (TextView) findViewById(R.id.tv_set_desc);
        String desc = checked?mDescOn:mDescOff;
        textView.setText(desc);
        checkBox.setChecked(checked);
    }

    @Override
    public boolean isInEditMode() {
        return super.isInEditMode();
    }
}
