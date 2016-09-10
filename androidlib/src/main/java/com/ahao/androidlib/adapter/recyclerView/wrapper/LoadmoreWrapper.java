package com.ahao.androidlib.adapter.recyclerView.wrapper;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahao.androidlib.adapter.recyclerView.ViewHolder;

/**
 * Created by Avalon on 2016/8/31.
 */
public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "LoadMoreWrapper";
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private Context mContext;
    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;

    public LoadMoreWrapper(Context context, RecyclerView.Adapter adapter) {
        mContext = context;
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null;
    }


    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        } else {
            return mInnerAdapter.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            holder = ViewHolder.get(parent.getContext(), mLoadMoreView);
        } else {
            holder = mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position) && mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMoreRequested();
        } else {
            mInnerAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
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
        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(@LayoutRes int layoutId) {
        mLoadMoreView = LayoutInflater.from(mContext).inflate(layoutId, null, false);
        return this;
    }

    public View getLoadMoreView() {
        return mLoadMoreView;
    }

}