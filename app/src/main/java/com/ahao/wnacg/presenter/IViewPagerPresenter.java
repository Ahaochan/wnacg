package com.ahao.wnacg.presenter;


import android.support.v4.app.Fragment;

import com.ahao.wnacg.ui.impl.IViewPagerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Avalon on 2016/6/9.
 */
public class IViewPagerPresenter {
    private IViewPagerView mView;

    public IViewPagerPresenter(IViewPagerView view){
        mView = view;
    }

    public void createPager(Fragment... fragment){
        List<Fragment> pager = new ArrayList<Fragment>();
        Collections.addAll(pager, fragment);
        mView.showPager(pager);
    }
}
