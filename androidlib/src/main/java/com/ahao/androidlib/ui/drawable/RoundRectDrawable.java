package com.ahao.androidlib.ui.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Avalon on 2016/8/31.
 */
public class RoundRectDrawable extends Drawable{
    private Paint mPaint;
    private RectF mBounds;
    private float xRadius;
    private float yRadius;
    private Bitmap mBitmap;

    public RoundRectDrawable(Bitmap bitmap){
        this(bitmap, 0, 0);
    }

    public RoundRectDrawable(Bitmap bitmap, float radius){
        this(bitmap, radius, radius);
    }

    public RoundRectDrawable(Bitmap bitmap, float xRadius, float yRadius){
        this.mBitmap = bitmap;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        BitmapShader bs = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(bs);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mBounds, xRadius, yRadius, mPaint);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        mBounds = new RectF(left, top, right, bottom);
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
        return mBitmap.getHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmap.getWidth();
    }
}
