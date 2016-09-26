package com.snail.snailSafe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by snail on 2016/9/26.
 * 获取焦点的TextView
 */

public class FocusTextView extends TextView {
    public FocusTextView(Context context) {
        this(context,null);
    }

    public FocusTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }


}
