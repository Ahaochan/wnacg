package com.ahao.wnacg.ui.fragment;

import android.os.Bundle;

import com.ahao.wnacg.presenter.IAlbumsPresenter;
import com.ahao.wnacg.ui.fragment.base.AlbumsBaseFragment;

/**
 * Created by Avalon on 2016/9/9.
 */
public class FavoriteFragment extends AlbumsBaseFragment {

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void loadDataFromNet(boolean reload) {

    }

    @Override
    protected IAlbumsPresenter presenter() {
        return null;
    }
}
