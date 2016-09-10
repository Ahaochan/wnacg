package com.ahao.wnacg.entity;

import android.support.annotation.DrawableRes;

/**
 * Created by Avalon on 2016/9/7.
 */
public class CateEntity {
    private int mImageResId;
    private String mCateName;
    private int mCateNum;

    public CateEntity(@DrawableRes int imageResId, String cateName, int cateNum) {
        this.mImageResId = imageResId;
        this.mCateName = cateName;
        this.mCateNum = cateNum;
    }

    public String getCateName() {
        return mCateName;
    }

    public void setCateName(String cateName) {
        this.mCateName = cateName;
    }

    public int getCateNum() {
        return mCateNum;
    }

    public void setCateNum(int cateNum) {
        this.mCateNum = cateNum;
    }

    public int getmImageResId() {
        return mImageResId;
    }

    public void setmImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }
}
