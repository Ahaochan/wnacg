package com.ahao.androidlib.ui.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Avalon on 2016/8/31.
 */
public class CircleDrawable extends Drawable{
    private Paint mPaint;
    private int mDiameter;
    private Bitmap mBitmap;


    public CircleDrawable(Bitmap bitmap){
        this.mBitmap = bitmap;
        BitmapShader bs = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(bs);
        mDiameter = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mDiameter/2, mDiameter/2, mDiameter/2, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return mDiameter;
    }

    @Override
    public int getIntrinsicWidth() {
        return mDiameter;
    }
}
