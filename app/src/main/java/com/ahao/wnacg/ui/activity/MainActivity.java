package com.ahao.wnacg.ui.activity;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ahao.wnacg.R;
import com.ahao.wnacg.presenter.IViewPagerPresenter;
import com.ahao.wnacg.ui.activity.base.ComBaseActivity;
import com.ahao.wnacg.ui.fragment.CateHeaderFragment;
import com.ahao.wnacg.ui.fragment.HomeFragment;
import com.ahao.wnacg.ui.fragment.LibraryFragment;
import com.ahao.wnacg.ui.impl.IViewPagerView;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends ComBaseActivity implements IViewPagerView {

    @BindView(R.id.main_viewpager)  ViewPager mViewPager;
    @BindView(R.id.main_drawer)     DrawerLayout mDrawerLayout;
    @BindView(R.id.main_toolbar)    Toolbar mToolbar;
    @BindView(R.id.main_tabLayout)  TabLayout mTabLayout;
    @BindView(R.id.main_nav)        NavigationView mNavigationView;

    private Context mContext;
    private IViewPagerPresenter presenter;
    private int tabCount = 3;

    @Override
    protected int actLayoutId() {
        return R.layout.act_main;
    }

    @Override
    protected int toolbarId() {
        return R.id.main_toolbar;
    }

    @Override
    protected void initView(Context context) {
        super.initView(context);
        mContext = context;
        presenter = new IViewPagerPresenter(this);

        presenter.createPager(
                HomeFragment.newInstance(),
                CateHeaderFragment.newInstance(),
                LibraryFragment.newInstance()
        );
        mViewPager.setOffscreenPageLimit(tabCount);
    }

    @Override
    protected void initToolbar(Context context) {
        super.initToolbar(context);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void setListener(Context context) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,
                mDrawerLayout, mToolbar, R.string.nav_open, R.string.nav_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return false;
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_menu_message:
                        Toast.makeText(mContext,"点击了"+item.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_local  :
                        Toast.makeText(mContext,"点击了"+item.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_about  :
                        Toast.makeText(mContext,"点击了"+item.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_setting:
                        Toast.makeText(mContext,"点击了"+item.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        /** 绑定TabLayout和ViewPager */
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    /** 填充ViewPager */
    @Override
    public void showPager(final List<Fragment> fragments) {
        mViewPager.setAdapter(new FragmentPagerAdapter(getBaseFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                SearchActivity.startAction(mContext);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }
}
