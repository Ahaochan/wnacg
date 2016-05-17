package com.ahao.wnacg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ahao.wnacg.R;

/**
 * Created by Avalon on 2016/5/16.
 */
public class SearchActivity extends NetBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loadDataFromNet();
        initView();
        setListener();
    }

    @Override
    protected void loadDataFromNet() {

    }

    @Override
    protected void initView(boolean flag) {

    }

    @Override
    protected void setListener() {

    }
}
