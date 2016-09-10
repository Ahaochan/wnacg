package com.ahao.androidlib.ui.widght.scrollView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.ahao.androidlib.R;

/**
 * Created by Avalon on 2016/9/6.
 */
public class IndicatingScrollView  extends ScrollView {

    private int mIndicatorHeight;

    private Paint mPaint;
    private Rect mIndicatorBounds;

    public IndicatingScrollView(Context context) {
        this(context, null);
    }

    public IndicatingScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        obtainStyledAttributes(context, attrs);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mIndicatorBounds = new Rect();
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Indicating);
        mIndicatorHeight = ta.getDimensionPixelOffset(R.styleable.Indicating_indicatorHeight, 1);
        mPaint.setColor(ta.getColor(R.styleable.Indicating_indicatorColor, Color.BLACK));
        mPaint.setStyle(Paint.Style.FILL);
        ta.recycle();
    }


    /** 检测是否能往下滚动 */
    private void fillTopIndicatorDrawRect() {
        mIndicatorBounds.set(0, 0, getWidth(), mIndicatorHeight);
    }

    private void fillBottomIndicatorDrawRect() {
        mIndicatorBounds.set(0, getHeight() - mIndicatorHeight, getWidth(), getHeight());
    }

    /** 检测是否能往上滚动 */
    private boolean needShowTopIndicator() {
        return canScrollVertically(-1);
    }

    /** 检测是否能往下滚动 */
    private boolean needShowBottomIndicator() {
        return canScrollVertically(1);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);

        final int restoreCount = canvas.save();
        canvas.translate(getScrollX(), getScrollY());

        /** 能往上滚动则显示顶部指示器 */
        if (needShowTopIndicator()) {
            fillTopIndicatorDrawRect();
            canvas.drawRect(mIndicatorBounds, mPaint);
        }
        /** 能往下滚动则显示底部指示器 */
        if (needShowBottomIndicator()) {
            fillBottomIndicatorDrawRect();
            canvas.drawRect(mIndicatorBounds, mPaint);
        }

        canvas.restoreToCount(restoreCount);
    }
}