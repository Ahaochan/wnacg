package com.ahao.wnacg.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.androidlib.util.OSUtils;
import com.ahao.wnacg.R;
import com.ahao.wnacg.ui.activity.base.ComBaseActivity;
import com.ahao.wnacg.ui.fragment.SearchFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;

/**
 * Created by Avalon on 2016/9/9.
 */
public class SearchActivity extends ComBaseActivity {
    private static final String TAG = "SearchActivity";

    private Context mContext;

    @BindView(R.id.search_toolbar)      Toolbar mToolbar;
    @BindView(R.id.search_view)         MaterialSearchView mSearchView;
    @BindView(R.id.search_card)         CardView mSearchCardView;
    @BindView(R.id.search_mode_group)   RadioGroup mSearchModeGroup;

    private SearchFragment fragment = SearchFragment.newInstance();


    @Override
    protected int actLayoutId() {
        return R.layout.act_search;
    }

    @Override
    protected int toolbarId() {
        return R.id.search_toolbar;
    }



    @Override
    protected void initView(Context context) {
        super.initView(context);
        this.mContext = context;

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mSearchView.getLayoutParams();
        lp.topMargin = OSUtils.getStatusBarHeight(mContext);
        mSearchView.setLayoutParams(lp);
        mSearchView.showSearch(false);
        mSearchView.setSuggestions(new String[]{"a", "ab","abc","b","bc"});
    }

    @Override
    protected void initToolbar(Context context) {
        super.initToolbar(context);
        mToolbar.setTitle(getString(R.string.search_mode_name_tip));
        mToolbar.setNavigationIcon(R.drawable.ic_search_back_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setListener(Context context) {
        super.setListener(context);
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                mSearchCardView.setVisibility(View.VISIBLE);
                mSearchView.setQuery(fragment.getSearchStr(), false);
            }

            @Override
            public void onSearchViewClosed() {
                mSearchCardView.setVisibility(View.GONE);
            }
        });

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!fragment.textSubmit(query)){
                    Toast.makeText(mContext, getString(R.string.search_null), Toast.LENGTH_SHORT).show();
                    String mode = fragment.getMode();
                    if(getString(R.string.search_mode_tag_tip).equals(mode)){
                        mToolbar.setTitle(getString(R.string.search_mode_tag_tip));
                    } else {
                        mToolbar.setTitle(getString(R.string.search_mode_name_tip));
                    }
                } else {
                    mToolbar.setTitle(query);
                }
                mSearchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.trim().equals("")){
                    mSearchCardView.setVisibility(View.VISIBLE);
                } else {
                    mSearchCardView.setVisibility(View.GONE);
                }
                return false;
            }
        });

        mSearchModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.search_mode_tag) {
                    fragment.changeMode(getString(R.string.search_mode_tag));
                } else {
                    fragment.changeMode(getString(R.string.search_mode_name));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    protected BaseFragment firstFragment() {
        return fragment;
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
