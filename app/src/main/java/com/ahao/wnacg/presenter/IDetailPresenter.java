package com.ahao.wnacg.presenter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.ahao.wnacg.R;
import com.ahao.wnacg.cache.ComicCache;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.net.OkHttpUtils;
import com.ahao.wnacg.net.callback.ComicCallback;
import com.ahao.wnacg.net.common.Header;
import com.ahao.wnacg.presenter.impl.ILoadData;
import com.ahao.wnacg.ui.impl.IDetailView;

import okhttp3.Call;

/**
 * Created by Avalon on 2016/6/9.
 */
public class IDetailPresenter implements ILoadData{
    final static private String TAG = IDetailPresenter.class.getSimpleName();

    private Context mContext;
    private IDetailView mView;
    private ComicEntity mComicEntity;
    private ComicCache cache;

    public IDetailPresenter(Context context, IDetailView view, ComicEntity comicEntity){
        mContext = context;
        mView = view;
        mComicEntity = comicEntity;
        cache = ComicCache.getInstance();
    }


    @Override
    public void loadData() {
        if(cache.isExpired(mComicEntity.getAId())){
            loadDataFromNet();
        } else {
            loadDataFromLocal();
        }
    }

    @Override
    public void loadDataFromNet() {
        String homeUrl = "http://"+ PreferenceManager.getDefaultSharedPreferences(mContext).getString(Common.KEY_WEB_SITE, "www.wnacg.com");
        OkHttpUtils.get()
                .url(homeUrl)
                .addHeader(Header.User_Agent, Header.User_Agent_Type.Desktop_UC)
                .addParams(mContext.getString(R.string.http_get_key_photos), "")
                .addParams(mContext.getString(R.string.http_get_key_index ), "")
                .addParams(mContext.getString(R.string.http_get_key_page  ), "1")
                .addParams(mContext.getString(R.string.http_get_key_aid), mComicEntity.getAId())
                .tag(mView)
                .build()
                .execute(new ComicCallback(mComicEntity) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, mContext.getString(R.string.loading_fail), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ComicEntity response, int id) {
                        mComicEntity = response;
                        mView.hideLoading();
                        mView.showDetail(mComicEntity);
                    }
                });
    }

    @Override
    public void loadDataFromLocal() {
        ComicEntity newData = cache.load(mComicEntity.getAId());
        mView.hideLoading();
        mView.showDetail(newData);
    }
}
