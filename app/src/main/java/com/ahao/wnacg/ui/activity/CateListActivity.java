package com.ahao.wnacg.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.wnacg.R;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.ui.activity.base.ComBaseActivity;
import com.ahao.wnacg.ui.fragment.CateListFragment;

import butterknife.BindView;

/**
 * Created by Avalon on 2016/9/7.
 */
public class CateListActivity extends ComBaseActivity {

    @BindView(R.id.act_toolbar) Toolbar mToolbar;

    private int mCate;

    @Override
    protected int actLayoutId() {
        return R.layout.act_toolbar;
    }

    @Override
    protected int toolbarId() {
        return R.id.act_toolbar;
    }

    @Override
    protected BaseFragment firstFragment() {
        mCate = getIntent().getIntExtra(Common.ALBUMS_CATE, -1);
        return CateListFragment.newInstance(mCate);
    }

    @Override
    protected void initToolbar(Context context) {
        super.initToolbar(context);
        String[] cateName = getResources().getStringArray(R.array.cate_array);
        mToolbar.setTitle(cateName[mCate]);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_dark_24dp);
        setSupportActionBar(mToolbar);
        /** setSupportActionBar会覆盖listener */
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void startAction(Context context, int cate){
        Intent intent = new Intent(context, CateListActivity.class);
        intent.putExtra(Common.ALBUMS_CATE, cate);
        context.startActivity(intent);
    }
}
