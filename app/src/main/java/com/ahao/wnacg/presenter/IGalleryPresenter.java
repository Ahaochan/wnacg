package com.ahao.wnacg.presenter;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.ahao.wnacg.R;
import com.ahao.wnacg.cache.ComicCache;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.net.OkHttpUtils;
import com.ahao.wnacg.net.callback.PicUrlCallback;
import com.ahao.wnacg.presenter.impl.ILoadData;
import com.ahao.wnacg.ui.fragment.GalleryFragment;
import com.ahao.wnacg.ui.impl.IGalleryView;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Avalon on 2016/6/8.
 */
public class IGalleryPresenter implements ILoadData {
    private final static String TAG = IGalleryPresenter.class.getSimpleName();

    private IGalleryView mView;
    private Context mContext;
    private ComicEntity mComicEntity;
    private ComicCache cache;

    public IGalleryPresenter(Context context, IGalleryView view, ComicEntity comicEntity) {
        mContext = context;
        mView = view;
        mComicEntity = comicEntity;
        cache = ComicCache.getInstance();
    }

    @Override
    public void loadData() {
        if (cache.isExpired(mComicEntity.getAId())) {//如果没有本地数据
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
                .addParams(mContext.getString(R.string.http_get_key_photos ), "")
                .addParams(mContext.getString(R.string.http_get_key_gallery), "")
                .addParams(mContext.getString(R.string.http_get_key_aid    ), mComicEntity.getAId())
                .tag((GalleryFragment) mView)
                .build()
                .execute(new PicUrlCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, mContext.getString(R.string.loading_fail), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<String> response, int id) {
                        Log.i(TAG, "onResponse: "+response);
                        mComicEntity.setPicList(response);

                        cache.save(mComicEntity);
                        mView.showNetPic(response);
                    }
                });
    }

    @Override
    public void loadDataFromLocal() {
        ComicEntity newData = cache.load(mComicEntity.getAId());
        mView.showNetPic(newData.getPicList());
    }

    public void shareUrl(Context context, String indexUrl) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, indexUrl);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "发送神♂秘链接"));
    }
}
