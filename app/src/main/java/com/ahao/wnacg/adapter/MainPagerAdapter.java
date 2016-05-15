package com.ahao.wnacg.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by Avalon on 2016/5/2.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter  {
    final static public String className = "MainPagerAdapter";
    List<Fragment> fragments;

    public MainPagerAdapter(FragmentManager fm, List fragments){
        super(fm);
        this.fragments = fragments;

    }
    @Override
    public Fragment getItem(int position) {
        Log.i(className, position+","+fragments.get(position));
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
