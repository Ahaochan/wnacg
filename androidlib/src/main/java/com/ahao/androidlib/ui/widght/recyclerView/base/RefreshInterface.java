package com.ahao.androidlib.ui.widght.recyclerView.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Avalon on 2016/8/31.
 */
public interface RefreshInterface {
    void setAdapter(RecyclerView.Adapter adapter);
    void setLayoutManager(RecyclerView.LayoutManager layout);
    void addItemDecoration(RecyclerView.ItemDecoration decor);
    void addItemDecoration(RecyclerView.ItemDecoration decor, int index);
}
