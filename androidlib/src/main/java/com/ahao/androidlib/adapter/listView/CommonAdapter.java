package com.ahao.androidlib.adapter.listView;

import android.content.Context;

import com.ahao.androidlib.adapter.listView.base.ItemViewDelegate;

import java.util.List;

/**
 * Created by Avalon on 2016/8/31.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    public CommonAdapter(Context context, List<T> datas, final int layoutId) {
        super(context, datas, layoutId);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);
}
