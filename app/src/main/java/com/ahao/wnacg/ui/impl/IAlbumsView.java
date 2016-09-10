package com.ahao.wnacg.ui.impl;

import com.ahao.wnacg.entity.ComicEntity;

import java.util.List;

/**
 * Created by Avalon on 2016/6/9.
 */
public interface IAlbumsView {
    void onSuccessLoad(List<ComicEntity> datas);
    void noMoreDataTip();
    void loadFailTip();

    void refreshed();
}
