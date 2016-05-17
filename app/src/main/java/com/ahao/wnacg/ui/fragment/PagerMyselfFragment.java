package com.ahao.wnacg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahao.wnacg.R;

import butterknife.BindView;

/**
 * Created by Avalon on 2016/5/2.
 */
public class PagerMyselfFragment extends ComBaseFragment {
    public final static String className = "PagerMyselfFragment";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_pager_myself, container, false);

        initToolBar(rootView);

        return rootView;
    }

    @Override
    protected void initToolBar(View rootView) {
        try {
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

            toolbar.setTitle("");
            setSupportToolBar(toolbar);

            TextView toolBarTitle = (TextView) rootView.findViewById(R.id.toolbar_title);
            toolBarTitle.setText(getContext().getText(R.string.page_home));

            setHasOptionsMenu(true);//显示toolBarMenu
        } catch (Exception e) {
            Log.e(className, "初始化ToolBar失败");
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }
}
