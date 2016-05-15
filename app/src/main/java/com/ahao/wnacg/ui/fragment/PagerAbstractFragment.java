package com.ahao.wnacg.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.wnacgnet.net.RequestManager;

/**
 * Created by Avalon on 2016/5/3.
 *
 * 填充ViewPager的Fragment的抽象类
 * 用来初始化toolbar
 */
public abstract class PagerAbstractFragment extends BaseFragment {
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mAppCompatActivity = (AppCompatActivity) mActivity;

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 初始化ToolBar
     */
    protected abstract void initToolBar(View rootView);

    protected boolean setSupportToolBar(Toolbar toolbar){
        try {
            mAppCompatActivity.setSupportActionBar(toolbar);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    protected void loadDataFromNet(){}//读取网络数据

    @Override
    public void onDetach() {
        super.onDetach();
        RequestManager.getInstance().cancelRequest();
    }
}
