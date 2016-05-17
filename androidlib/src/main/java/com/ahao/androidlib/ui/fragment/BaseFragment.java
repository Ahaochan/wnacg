package com.ahao.androidlib.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Avalon on 2016/5/12.
 */
public abstract class BaseFragment extends Fragment {
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mAppCompatActivity = (AppCompatActivity) mActivity;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public AppCompatActivity getAppCompatActivity() {
        return mAppCompatActivity;
    }

    protected boolean setSupportToolBar(Toolbar toolbar){
        try {
            mAppCompatActivity.setSupportActionBar(toolbar);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    protected abstract void initToolBar(View rootView);
    protected abstract void initView();
    protected abstract void initView(boolean flag);
    protected abstract void setListener();



}
