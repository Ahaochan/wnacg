package com.ahao.androidlib.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Avalon on 2016/8/3.
 */
    public abstract class AppCompatPreferenceActivity extends PreferenceActivity {
    private static final String TAG = AppCompatPreferenceActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private AppCompatDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        setStatusBar();
        /** 获取DecorView下的ContentView*/
        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
        /** 获取ContentView的子View */
        View content = contentView.getChildAt(0);
        /** 加载自定义布局文件 */
        LinearLayout toolbarLayout = (LinearLayout) LayoutInflater.from(this).inflate(getActLayoutId(), null);
        /** 移除根布局所有子view */
        contentView.removeAllViews();

        /** 注意这里一要将前面移除的子View添加到我们自定义布局文件中，否则PreferenceActivity中的Header将不会显示 */
        toolbarLayout.addView(content);
        /** 将包含Toolbar的自定义布局添加到根布局中 */
        contentView.addView(toolbarLayout);

        /** 设置toolbar */
        mToolbar = (Toolbar) toolbarLayout.findViewById(getActToolbarId());
        initToolbar(getToolbar());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    @Override
    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    public AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }


    protected void setStatusBar() {
    }

    protected abstract int getActLayoutId();

    protected abstract int getActToolbarId();

    protected abstract void initToolbar(Toolbar toolbar);

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
