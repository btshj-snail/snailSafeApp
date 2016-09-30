package com.snail.snailSafe.activity.mobileLost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by snail on 2016/9/30.
 */

public abstract class BaseStepActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e1.getRawX()-e2.getRawX()>100){
                    //向右滑动 下一页
                    nextPage();

                }else if(e2.getRawX()-e1.getRawX()>100){
                    //向左滑动  上一页
                    prevPage();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);

    }

    /**
     * 上一页
     */
    public abstract void prevPage() ;

    /**
     * 下一页
     */
    public abstract void nextPage() ;
}
