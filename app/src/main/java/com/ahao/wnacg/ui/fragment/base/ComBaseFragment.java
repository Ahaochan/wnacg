package com.ahao.wnacg.ui.fragment.base;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.androidlib.util.OSUtils;
import com.ahao.wnacg.net.OkHttpUtils;

import butterknife.ButterKnife;

/**
 * Created by Avalon on 2016/5/15.
 */
public abstract class ComBaseFragment extends BaseFragment {

    @Override
    protected void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void initToolbar(View rootView) {
        Toolbar mToolbar = (Toolbar) rootView.findViewById(toolbarId());
        if (mToolbar!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = OSUtils.getAppBarHeight(getContext());
            mToolbar.setPadding(mToolbar.getPaddingLeft(),
                    OSUtils.getStatusBarHeight(getContext()),
                    mToolbar.getPaddingRight(),
                    mToolbar.getPaddingBottom());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    protected int toolbarId(){
        return 0;
    }
}
