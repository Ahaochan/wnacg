package com.ahao.androidlib.ui.fragment;


import android.support.v4.app.Fragment;

/**
 * Created by Avalon on 2016/5/12.
 */
public abstract class BaseFragment extends Fragment {
    protected void initView(){}
    protected void initView(boolean flag){}
    protected abstract void setListener();

    protected void setHandler(){}
}
