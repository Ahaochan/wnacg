package com.ahao.wnacg.presenter;

import android.content.Context;

import com.ahao.wnacg.R;
import com.ahao.wnacg.ui.impl.IAlbumsView;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Avalon on 2016/9/9.
 */
public class ISearchPresenter extends IAlbumsPresenter {

    private String mSearchStr;
    private String mMode;

    public ISearchPresenter(Context context, IAlbumsView view, String searchStr, String mode) {
        super(context, view);
        this.mSearchStr = searchStr;
        this.mMode = mode;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(mContext.getString(R.string.http_get_key_albums), "");
        params.put(mContext.getString(R.string.http_get_key_index), "");
        params.put(mContext.getString(R.string.http_get_key_page), mPage+"");
        if(mMode.equals(mContext.getString(R.string.search_mode_tag))){
            params.put(mContext.getString(R.string.http_get_key_tag), mSearchStr);
        } else {
            params.put(mContext.getString(R.string.http_get_key_sname), mSearchStr);
        }
        return params;
    }

    @Override
    protected boolean isExpired() {
        return true;
    }

    public String getSearchStr() {
        return mSearchStr;
    }

    public void setSearchStr(String searchStr) {
        this.mSearchStr = searchStr;
    }

    public void setMode(String mode) {
        this.mMode = mode;
    }

    public String getMode() {
        return mMode;
    }
}
