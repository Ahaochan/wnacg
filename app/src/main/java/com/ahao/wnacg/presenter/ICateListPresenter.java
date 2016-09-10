package com.ahao.wnacg.presenter;

import android.content.Context;

import com.ahao.wnacg.R;
import com.ahao.wnacg.ui.impl.IAlbumsView;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Avalon on 2016/6/9.
 */
public class ICateListPresenter extends IAlbumsPresenter {

    protected int mCateNum;

    public ICateListPresenter(Context context, IAlbumsView view, int cateNum) {
        super(context, view);
        this.mCateNum = cateNum;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(mContext.getString(R.string.http_get_key_albums), "");
        params.put(mContext.getString(R.string.http_get_key_index), "");
        params.put(mContext.getString(R.string.http_get_key_page), mPage+"");
        params.put(mContext.getString(R.string.http_get_key_cate), mCateNum+"");
        return params;
    }

    @Override
    protected boolean isExpired() {
        return true;
    }
}
