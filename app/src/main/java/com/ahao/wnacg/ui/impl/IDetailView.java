package com.ahao.wnacg.ui.impl;

import com.ahao.wnacg.entity.ComicEntity;

/**
 * Created by Avalon on 2016/6/9.
 */
public interface IDetailView {
    void showLoading();
    void hideLoading();

    void showDetail(ComicEntity comicEntity);

}
