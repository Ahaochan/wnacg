package com.ahao.wnacg.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Avalon on 2016/5/2.
 *
 * 禁止滑动的ViewPager,和RadioGroup配合使用
 */
public class BanScrollViewPager extends ViewPager {

    private boolean isScrollable = false;//是否允许滑动

    public BanScrollViewPager(Context context) {
        super(context);
    }

    public BanScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScrollable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScrollable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }

    }
}
