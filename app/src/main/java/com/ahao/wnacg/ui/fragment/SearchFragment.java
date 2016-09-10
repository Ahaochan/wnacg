package com.ahao.wnacg.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import com.ahao.wnacg.R;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.presenter.IAlbumsPresenter;
import com.ahao.wnacg.presenter.ISearchPresenter;
import com.ahao.wnacg.ui.fragment.base.AlbumsBaseFragment;

/**
 * Created by Avalon on 2016/9/7.
 */
public class SearchFragment extends AlbumsBaseFragment {
    private static final String TAG = "SearchFragment";
    private ISearchPresenter mPresenter;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        int cate = getArguments().getInt(Common.ALBUMS_CATE, -1);
        mPresenter = new ISearchPresenter(context, this, "", getString(R.string.search_mode_name));
    }

    public boolean textSubmit(String searchStr){
        if(searchStr.trim().equals("")){
            return false;
        }

        mPresenter.init();
        mPresenter.setSearchStr(searchStr);
        loadDataFromNet(true);
        return true;
    }

    public boolean changeMode(String mode){
        if(mPresenter.getSearchStr().trim().equals("")){
            return false;
        }
        mPresenter.init();
        mPresenter.setMode(mode);
        loadDataFromNet(true);
        return true;
    }

    @Override
    protected IAlbumsPresenter presenter() {
        return mPresenter;
    }

    public String getMode() {
        return mPresenter.getMode();
    }

    public String getSearchStr(){
        return mPresenter.getSearchStr();
    }
}
