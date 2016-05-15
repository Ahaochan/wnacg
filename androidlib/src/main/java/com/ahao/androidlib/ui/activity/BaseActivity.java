package com.ahao.androidlib.ui.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Avalon on 2016/5/12.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected void initView(){}
    protected void initView(boolean flag){}
    protected abstract void setListener();

    protected void setHandler(){}
}
