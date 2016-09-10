package com.ahao.androidlib.adapter.listView.base;

import android.support.v4.util.SparseArrayCompat;
import android.util.Log;

import com.ahao.androidlib.adapter.listView.ViewHolder;

/**
 * Created by Avalon on 2016/8/31.
 * 多种ItemViewType管理器
 */
public class ItemViewDelegateManager<T> {
    private static final String TAG = "ItemViewDelegateManager";
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat<ItemViewDelegate<T>>();

    public ItemViewDelegateManager(){}

    /** 获取itemViewType种类数量 */
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    /** 新增itemViewType种类 */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
        } else {
            throw new IllegalArgumentException("The SparseArrayCompat must be init");
        }
        return this;
    }

    /** 新增itemViewType种类 */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    /** 移除itemViewType种类 */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        } else{
            Log.e(TAG, "removeDelegate: " + false);
        }
        return this;
    }

    /** 移除itemViewType种类 */
    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        } else{
            Log.e(TAG, "removeDelegate: " + false);
        }
        return this;
    }

    /** 获取itemViewType种类的key */
    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public void convert(ViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    /** recyclerView无 */
    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }

    /** list无*/
    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    public ItemViewDelegate getItemViewDelegate(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }




}
