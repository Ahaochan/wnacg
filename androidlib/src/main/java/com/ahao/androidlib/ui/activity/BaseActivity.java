package com.ahao.androidlib.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.androidlib.util.OSUtils;

/**
 * Created by Avalon on 2016/5/12.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = BaseActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        beforeCreate();
        super.onCreate(savedInstanceState);
        setContentView(actLayoutId());
        /** 避免重复添加Fragment */
        if (getSupportFragmentManager().getFragments() == null) {
            BaseFragment firstFragment = firstFragment();
            if (firstFragment != null) {
                addFragment(firstFragment);
            }
        }
        initView(this);
        initToolbar(this);
        setListener(this);
    }

    protected void beforeCreate(){};

    /** 获取第一个fragment */
    protected abstract BaseFragment firstFragment();

    /** 获得Activity的布局id */
    protected abstract int actLayoutId();

    /** 布局中被Fragment填充的Layout的ID */
    protected abstract int fragContentLayoutId();


    /** 获得FragmentManager */
    public FragmentManager getBaseFragmentManager(){
        return getSupportFragmentManager();
    }

    /** 添加fragment */
    public boolean addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getBaseFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(fragContentLayoutId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
            return true;
        }
        return false;
    }

    /** 移除fragment */
    public void removeFragment() {
        if (getFragmentCount() > 1) {
            getBaseFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /** 获得最顶层的Fragment */
    public BaseFragment getTopFragment(){
        return (BaseFragment) getBaseFragmentManager().getFragments().get(getFragmentCount()-1);
    }

    /** 获得当前Fragment的数量 */
    public int getFragmentCount(){
        return getBaseFragmentManager().getBackStackEntryCount();
    }

    /** 监听返回键 */
    @Override
    public void onBackPressed() {
        if (getFragmentCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    protected void initView(Context context){}
    protected void initToolbar(Context context){
        Toolbar mToolbar = (Toolbar) findViewById(toolbarId());
        if(mToolbar == null){
            Log.e(TAG, "toolbar id is null !!!!");
        }

        if (mToolbar!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = OSUtils.getAppBarHeight(context);
            mToolbar.setPadding(mToolbar.getPaddingLeft(),
                    OSUtils.getStatusBarHeight(context),
                    mToolbar.getPaddingRight(),
                    mToolbar.getPaddingBottom());
        }
    }
    protected void setListener(Context context){}

    protected abstract int toolbarId();
}
