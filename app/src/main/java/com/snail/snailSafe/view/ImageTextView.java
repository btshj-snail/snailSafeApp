package com.snail.snailSafe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.snail.snailSafe.R;

/**
 * Created by snail on 2016/9/27.
 * 由于原来的TextView 使用drawable时不能设置图片带下
 * 因此自定义该view ，使其能够自定义图片
 */

public class ImageTextView extends TextView {

    public static final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;

    private Context mContext ;
    private int mDrawableHeight;
    private int mDrawableWidth;
    private Drawable mDrawableSrc;
    private int mDrawableLocation;

    public ImageTextView(Context context) {
       this(context,null);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initCustomAttr(attrs);


    }

    private void initCustomAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        mDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.ImageTextView_drawable_height,0);
        mDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.ImageTextView_drawable_width,0);
        mDrawableSrc = typedArray.getDrawable(R.styleable.ImageTextView_drawable_src);
        mDrawableLocation = typedArray.getInt(R.styleable.ImageTextView_drawable_location,LEFT);
        typedArray.recycle();
        //绘制Drawable宽高,位置
        drawDrawable();
    }

    /**
     * 绘制
     */
    private void drawDrawable() {
        if (mDrawableSrc != null) {
            Bitmap bitmap = ((BitmapDrawable) mDrawableSrc).getBitmap();
            Drawable drawable;
            if (mDrawableWidth != 0 && mDrawableHeight != 0) {
                drawable = new BitmapDrawable(getResources(), getBitmap(bitmap,
                        mDrawableWidth, mDrawableHeight));
            } else {
                drawable = new BitmapDrawable(getResources(),
                        Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
                                bitmap.getHeight(), true));
            }

            switch (mDrawableLocation) {
                case LEFT:
                    this.setCompoundDrawablesWithIntrinsicBounds(drawable, null,
                            null, null);
                    break;
                case TOP:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, drawable,
                            null, null);
                    break;
                case RIGHT:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            drawable, null);
                    break;
                case BOTTOM:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                            drawable);
                    break;
            }
        }
    }

    /**
     * 缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public Bitmap getBitmap(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = (float) newWidth / width;
        float scaleHeight = (float) newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

}
