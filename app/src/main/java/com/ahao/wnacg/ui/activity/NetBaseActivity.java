package com.ahao.wnacg.ui.activity;

import android.widget.Toast;

import com.ahao.androidlib.ui.activity.BaseActivity;
import com.ahao.wnacgnet.net.RequestCallback;

/**
 * Created by Avalon on 2016/5/13.
 */
public abstract class NetBaseActivity extends BaseActivity {

    public abstract class AbstractRequestCallback implements RequestCallback {
        public abstract void onSuccess(String content);
        public void onFail(String errorMessage){
            Toast.makeText(NetBaseActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {}

    protected abstract void loadDataFromNet();
}
