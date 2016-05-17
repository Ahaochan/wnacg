package com.ahao.wnacg.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.wnacgnet.net.RequestCallback;

/**
 * Created by Avalon on 2016/5/15.
 */
public abstract class ComBaseFragment extends BaseFragment {

    @Override
    protected abstract void initView();

    @Override
    protected void initView(boolean flag) {}

    @Override
    protected abstract void setListener();



}
