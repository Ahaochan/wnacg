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
import com.ahao.wnacgnet.net.RequestManager;

/**
 * Created by Avalon on 2016/5/3.
 *
 * 填充ViewPager的Fragment的抽象类
 * 用来初始化toolbar
 */
public abstract class NetBaseFragment extends BaseFragment {
    public abstract class AbstractRequestCallback implements RequestCallback {
        public abstract void onSuccess(String content);
        public void onFail(String errorMessage){
            Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {}

    protected abstract void loadDataFromNet();
    @Override
    public void onDetach() {
        super.onDetach();
        RequestManager.getInstance().cancelRequest();
    }
}
