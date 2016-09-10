package com.ahao.androidlib.adapter.recyclerView.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ahao.androidlib.adapter.recyclerView.ViewHolder;

/**
 * Created by Avalon on 2016/8/31.
 */
public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;


    public EmptyWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (isEmpty()) {
            if (mEmptyView != null) {
                holder = ViewHolder.get(parent.getContext(), mEmptyView);
            } else {
                holder = ViewHolder.get(parent.getContext(), parent, mEmptyLayoutId);
            }
        } else {
            holder = mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
        return holder;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isEmpty()) {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });


    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty()) {
            WrapperUtils.setFullSpan(holder);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        } else {
            return mInnerAdapter.getItemViewType(position);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isEmpty()) {
            mInnerAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return isEmpty()?1:mInnerAdapter.getItemCount();
    }


    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }

}