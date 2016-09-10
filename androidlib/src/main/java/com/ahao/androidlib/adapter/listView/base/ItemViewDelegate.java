package com.ahao.androidlib.adapter.listView.base;

import com.ahao.androidlib.adapter.listView.ViewHolder;

/**
 * Created by Avalon on 2016/8/31.
 * 多种ItemViewType接口
 */
public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();
    boolean isForViewType(T item, int position);
    void convert(ViewHolder holder, T t, int position);
}
