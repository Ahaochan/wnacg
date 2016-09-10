package com.ahao.wnacg.presenter;

import android.content.Context;

import com.ahao.wnacg.R;
import com.ahao.wnacg.cache.HomeCache;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.ui.impl.IAlbumsView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Avalon on 2016/6/8.
 */
public class IHomePresenter extends IAlbumsPresenter {
    private static final String TAG = "IHomePresenter";

    private HomeCache cache;

    public IHomePresenter(Context context, IAlbumsView view){
        super(context, view);
        this.cache = HomeCache.getInstance();
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(mContext.getString(R.string.http_get_key_albums), "");
        params.put(mContext.getString(R.string.http_get_key_index), "");
        params.put(mContext.getString(R.string.http_get_key_page), mPage+"");
        return params;
    }

    @Override
    protected boolean isExpired() {
        return cache.isExpired();
    }

    @Override
    public void loadDataFromLocal() {
        List<ComicEntity> plants = cache.load();
        mPage++;
        mView.onSuccessLoad(plants);
    }
}
