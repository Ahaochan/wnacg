package com.ahao.androidlib.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahao.androidlib.ui.activity.BaseActivity;

/**
 * Created by Avalon on 2016/5/12.
 */
public abstract class BaseFragment extends Fragment {
    private Activity mActivity;
    private BaseActivity mBaseActivity;
    private AppCompatActivity mAppCompatActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        mBaseActivity = (BaseActivity) mActivity;
        mAppCompatActivity = (AppCompatActivity) mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(setLayoutId(), container, false);
        initView(rootView);
        initToolbar(rootView);
        setListener(rootView);
        return rootView;
    }

    /** 设置toolbar */
    protected boolean setSupportToolBar(Toolbar toolbar){
        try {
            mAppCompatActivity.setSupportActionBar(toolbar);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /** 获取布局文件ID */
    protected abstract int setLayoutId();

    /** 获取Activity的FragmentManager */
    public FragmentManager getBaseFragmentManager(){
        return mBaseActivity.getBaseFragmentManager();
    }

    /** 添加fragment */
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            mBaseActivity.addFragment(fragment);
        }
    }

    /** 移除fragment */
    protected void removeFragment() {
        mBaseActivity.removeFragment();
    }

    /** 初始化View */
    protected abstract void initView(View rootView);
    /** 初始化toolbar */
    protected void initToolbar(View rootView){}
    /** 初始化监听器 */
    protected void setListener(View rootView) {}
}
