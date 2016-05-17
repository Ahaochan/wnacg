package com.ahao.wnacg.ui.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.ahao.androidlib.ui.activity.BaseActivity;
import com.ahao.wnacg.R;
import com.ahao.wnacg.adapter.MainPagerAdapter;
import com.ahao.wnacg.ui.fragment.PagerFindFragment;
import com.ahao.wnacg.ui.fragment.PagerHomeFragment;
import com.ahao.wnacg.ui.fragment.PagerLibraryFragment;
import com.ahao.wnacg.ui.fragment.PagerMyselfFragment;
import com.ahao.wnacg.ui.viewpager.BanScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ComBaseActivity {
    final static private String className = MainActivity.class.getSimpleName();
    List<Fragment> pageFragments;
    MainPagerAdapter mainPagerAdapter;

    @BindView(R.id.viewPager) BanScrollViewPager viewPager;
    @BindView(R.id.pageRadioGroup)  RadioGroup pageRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.i(className,"进入成功");
        initView();
        setListener();
    }

    @Override
    protected void initView() {
        pageFragments = new ArrayList<Fragment>();

        Fragment pageHomeFragment = new PagerHomeFragment();
        Fragment pageFindFragment = new PagerFindFragment();
        Fragment pageLibraryFragment = new PagerLibraryFragment();
        Fragment pageMyselfFragment = new PagerMyselfFragment();
        pageFragments.add(pageHomeFragment);
        pageFragments.add(pageFindFragment);
        pageFragments.add(pageLibraryFragment);
        pageFragments.add(pageMyselfFragment);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), pageFragments);
        viewPager.setAdapter(mainPagerAdapter);
    }

    @Override
    protected void setListener() {
        pageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int check = 0;
                switch (checkedId){
                    case R.id.btn_home:check = 0;break;
                    case R.id.btn_find:check = 1;break;
                    case R.id.btn_library:check = 2;break;
                    case R.id.btn_myself:check = 3;break;
                }
                Log.i(className, "选择"+check);
                viewPager.setCurrentItem(check, true);
            }
        });
    }
}
