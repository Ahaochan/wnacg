package com.ahao.wnacg.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ahao.wnacg.R;
import com.ahao.wnacg.presenter.IViewPagerPresenter;
import com.ahao.wnacg.ui.fragment.base.ComBaseFragment;
import com.ahao.wnacg.ui.impl.IViewPagerView;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Avalon on 2016/5/2.
 */
public class LibraryFragment extends ComBaseFragment implements IViewPagerView {

    @BindView(R.id.library_viewPager) ViewPager mViewPager;
    @BindView(R.id.library_tabLayout) TabLayout mTabLayout;

    private Context mContext;
    private IViewPagerPresenter presenter;
    private int tabCount = 3;

    public static LibraryFragment newInstance() {
        Bundle args = new Bundle();
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        presenter = new IViewPagerPresenter(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.frag_library;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        presenter.createPager(
                FavoriteFragment.newInstance(),
                HistoryFragment.newInstance(),
                DownloadFragment.newInstance()
        );
        mViewPager.setOffscreenPageLimit(tabCount);

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
}
