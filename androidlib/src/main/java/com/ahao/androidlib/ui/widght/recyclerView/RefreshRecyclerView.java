package com.ahao.androidlib.ui.widght.recyclerView;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Avalon on 2016/8/31.
 */
public class RefreshRecyclerView extends SwipeRefreshLayout {
    private static final String TAG = "RefreshRecyclerView";
    private Context mContext;
    private FrameLayout mRootView;
    private RecyclerView innerRecyclerView;
    private View mEmptyView;
    private OnLoadMoreListener listener;

    public interface OnLoadMoreListener{
        void onLoadMore();
        void onPullToRefersh();
    }

    public static class SimpleOnLoadMoreListener implements OnLoadMoreListener{
        @Override
        public void onLoadMore() {}
        @Override
        public void onPullToRefersh() {}
    }


    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
        setListener();
    }

    private void initView() {
        this.setProgressBackgroundColorSchemeResource(android.R.color.white);
        this.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        this.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24, getResources().getDisplayMetrics()));


        mRootView = new FrameLayout(mContext);
        this.addView(mRootView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        innerRecyclerView = new RecyclerView(mContext);
        innerRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        innerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
        mRootView.addView(innerRecyclerView, lp);

        this.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                /** 下拉刷新加载中 */
                if(listener!=null){
                    listener.onPullToRefersh();
                }
            }
        });
    }

    private void setListener() {
        innerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private  int lastVisibleItem;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1 == getItemCount()) {
                    /** 滑动到底部,加载数据 */
                    if(listener!=null){
                        listener.onLoadMore();
                    }
                }
            }
        });
    }


    /**
     * recyclerView自带的方法
     */

    public int findFirstVisibleItemPosition(){
        int position = -1;
        RecyclerView.LayoutManager lm = innerRecyclerView.getLayoutManager();
        if(lm instanceof LinearLayoutManager){
            position = ((LinearLayoutManager) lm).findFirstVisibleItemPosition();
        } else if(lm instanceof GridLayoutManager){
            position = ((GridLayoutManager) lm).findFirstVisibleItemPosition();
        } else if(lm instanceof StaggeredGridLayoutManager){
            int[] positions = ((StaggeredGridLayoutManager) lm).findFirstVisibleItemPositions(null);
            position = positions[0];
        }
        return position;
    }

    public int findFirstCompletelyVisibleItemPosition(){
        int position = -1;
        RecyclerView.LayoutManager lm = innerRecyclerView.getLayoutManager();
        if(lm instanceof LinearLayoutManager){
            position = ((LinearLayoutManager) lm).findFirstCompletelyVisibleItemPosition();
        } else if(lm instanceof GridLayoutManager){
            position = ((GridLayoutManager) lm).findFirstCompletelyVisibleItemPosition();
        } else if(lm instanceof StaggeredGridLayoutManager){
            int[] positions = ((StaggeredGridLayoutManager) lm).findFirstCompletelyVisibleItemPositions(null);
            position = positions[0];
        }
        return position;
    }

    public int findLastVisibleItemPosition(){
        int position = -1;
        RecyclerView.LayoutManager lm = innerRecyclerView.getLayoutManager();
        if(lm instanceof LinearLayoutManager){
            position = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
        } else if(lm instanceof GridLayoutManager){
            position = ((GridLayoutManager) lm).findLastVisibleItemPosition();
        } else if(lm instanceof StaggeredGridLayoutManager){
            int[] positions = ((StaggeredGridLayoutManager) lm).findLastVisibleItemPositions(null);
            position = positions[0];
        }
        return position;
    }

    public int findLastCompletlyVisibleItemPosition(){
        int position = -1;
        RecyclerView.LayoutManager lm = innerRecyclerView.getLayoutManager();
        if(lm instanceof LinearLayoutManager){
            position = ((LinearLayoutManager) lm).findLastCompletelyVisibleItemPosition();
        } else if(lm instanceof GridLayoutManager){
            position = ((GridLayoutManager) lm).findLastCompletelyVisibleItemPosition();
        } else if(lm instanceof StaggeredGridLayoutManager){
            int[] positions = ((StaggeredGridLayoutManager) lm).findLastCompletelyVisibleItemPositions(null);
            position = positions[0];
        }
        return position;
    }

    public int getItemCount(){
        if(innerRecyclerView!=null && innerRecyclerView.getAdapter()!=null) {
            return innerRecyclerView.getAdapter().getItemCount();
        } else {
            return 0;
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator){
        innerRecyclerView.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor){
        innerRecyclerView.addItemDecoration(decor);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index){
        innerRecyclerView.addItemDecoration(decor, index);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        innerRecyclerView.setAdapter(adapter);
    }

    /**
     * setter And getter
     */

    public OnLoadMoreListener getOnLoadMoreListener() {
        return listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }

    public void setEmptyView(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        setEmptyView(view);
    }

    public void setEmptyView(View emptyView) {
        if(mEmptyView!=null){
            this.removeView(mEmptyView);
        }
        this.mEmptyView = emptyView;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
        mRootView.addView(mEmptyView, lp);
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void showEmptyView(){
        if(mEmptyView!=null){
            Log.i(TAG, "showEmptyView: ");
            mEmptyView.setVisibility(VISIBLE);
            innerRecyclerView.setVisibility(GONE);
            setRefreshing(false);
        }
    }

    public void hideEmptyView(){
        if(mEmptyView!=null){
            Log.i(TAG, "hideEmptyView: ");
            mEmptyView.setVisibility(GONE);
            innerRecyclerView.setVisibility(VISIBLE);
            setRefreshing(false);
        }
    }
}
