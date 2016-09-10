package com.ahao.wnacg.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.presenter.IAlbumsPresenter;
import com.ahao.wnacg.presenter.ICateListPresenter;
import com.ahao.wnacg.ui.fragment.base.AlbumsBaseFragment;

/**
 * Created by Avalon on 2016/9/7.
 */
public class CateListFragment extends AlbumsBaseFragment {

    private ICateListPresenter mPresenter;
    public static CateListFragment newInstance(int cate) {
        Bundle args = new Bundle();
        args.putInt(Common.ALBUMS_CATE, cate);
        CateListFragment fragment = new CateListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        int cate = getArguments().getInt(Common.ALBUMS_CATE, -1);
        mPresenter = new ICateListPresenter(context, this, cate);
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
