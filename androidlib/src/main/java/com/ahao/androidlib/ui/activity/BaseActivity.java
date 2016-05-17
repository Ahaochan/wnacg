package com.ahao.androidlib.ui.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Avalon on 2016/5/12.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void initView();
    protected abstract void initView(boolean flag);
    protected abstract void setListener();
}
