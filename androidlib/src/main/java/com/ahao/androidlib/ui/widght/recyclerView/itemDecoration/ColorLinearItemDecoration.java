package com.ahao.androidlib.ui.widght.recyclerView.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Avalon on 2016/8/30.
 * 一个填充颜色的分割线ItemDecoration
 */
public class ColorLinearItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "ColorLinearItemDecoration";
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private static final int DEFAULT_DISTANCE = 30;

    private Drawable mDivider;
    private int mOrientation;
    private int mDist;

    public ColorLinearItemDecoration(Context context) {
        this(context, VERTICAL_LIST, Color.TRANSPARENT, DEFAULT_DISTANCE);
    }

    public ColorLinearItemDecoration(Context context, int orientation) {
        this(context, orientation, Color.TRANSPARENT, DEFAULT_DISTANCE);
    }

    public ColorLinearItemDecoration(Context context, int orientation, @ColorInt int color, int dist) {
        mDivider = new ColorDrawable(color);
        mDist = dist;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, mDist/2, 0, mDist/2);
        } else {
            outRect.set(mDist/2, 0, mDist/2, 0);
        }
    }
}
