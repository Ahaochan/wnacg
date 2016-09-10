package com.ahao.wnacg.presenter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.net.OkHttpUtils;
import com.ahao.wnacg.net.callback.AlbumsCallback;
import com.ahao.wnacg.presenter.impl.ILoadData;
import com.ahao.wnacg.ui.impl.IAlbumsView;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Avalon on 2016/9/7.
 */
public abstract class IAlbumsPresenter implements ILoadData {
    private static final String TAG = "IAlbumsPresenter";

    protected IAlbumsView mView;
    protected Context mContext;

    protected int mMaxPage;
    protected int mPage;

    public IAlbumsPresenter(Context context, IAlbumsView view){
        this.mContext = context;
        this.mView = view;
        init();
    }

    @Override
    public void loadData() {
        if(isExpired()){
            loadDataFromNet();
        } else {
            loadDataFromLocal();
        }
    }

    @Override
    public void loadDataFromNet() {
        Log.i(TAG, "loadDataFromNet: ");
        if (mPage > mMaxPage) {
            mView.noMoreDataTip();
        } else {
            String homeUrl = "http://" + PreferenceManager.getDefaultSharedPreferences(mContext).getString(Common.KEY_WEB_SITE, "www.wnacg.com");
            OkHttpUtils.get()
                    .url(homeUrl)
                    .tag(mView)
                    .params(getParams())
                    .build()
                    .execute(new AlbumsCallback(mContext) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mView.loadFailTip();
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(List<ComicEntity> response, int id) {
                            mMaxPage = getAlbumsMaxPage();
                            if (response.size() > 0) {
                                if (mPage <= 1) {
                                    saveCache(response);
                                }
                                mView.onSuccessLoad(response);
                                mPage++;
                            } else {
                                mView.noMoreDataTip();
                            }
                        }
                    });
        }
    }

    @Override
    public void loadDataFromLocal() {}

    protected void saveCache(List<ComicEntity> response) {}

    public void init(){
        mPage = 1;
        mMaxPage = Integer.MAX_VALUE;
    }

    protected abstract Map<String,String> getParams();

    protected abstract boolean isExpired();
}
