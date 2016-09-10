package com.ahao.wnacg.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ahao.wnacg.presenter.IAlbumsPresenter;
import com.ahao.wnacg.presenter.IHomePresenter;
import com.ahao.wnacg.ui.fragment.base.AlbumsBaseFragment;


/**
 * Created by Avalon on 2016/5/2.
 */
public class HomeFragment extends AlbumsBaseFragment {
    private static final String KEY_DATAS = "key_datas";

    private IHomePresenter mPresenter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mPresenter = new IHomePresenter(context, this);
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mPresenter.loadData();
    }

    @Override
    protected IAlbumsPresenter presenter() {
        return mPresenter;
    }
}
