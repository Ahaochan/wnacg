package com.ahao.wnacg.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahao.androidlib.adapter.recyclerView.wrapper.LoadMoreWrapper;
import com.ahao.androidlib.ui.widght.recyclerView.RefreshRecyclerView;
import com.ahao.wnacg.R;
import com.ahao.wnacg.adapter.AlbumsAdapter;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.presenter.IAlbumsPresenter;
import com.ahao.wnacg.ui.impl.IAlbumsView;
import com.ahao.wnacg.util.ImageUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Avalon on 2016/9/7.
 */
public abstract class AlbumsBaseFragment extends ComBaseFragment implements IAlbumsView {
    private static final String TAG = "AlbumsBaseFragment";
    private static final String KEY_DATAS = "key_datas";

    @BindView(R.id.home_refresh) RefreshRecyclerView refreshLayout;

    protected View loadingLayout;
    protected ImageView loadingIcon;
    protected TextView loadingText;

    protected Context mContext;
    protected AlbumsAdapter mAdapter;
    protected List<ComicEntity> mDatas;
    protected LoadMoreWrapper mLoadMoreWrapper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mAdapter = new AlbumsAdapter(mContext, ItemLayoutId(), null);
        this.mLoadMoreWrapper = new LoadMoreWrapper(mContext, mAdapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.frag_albums;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        mLoadMoreWrapper.setLoadMoreView(R.layout.view_load_more);

        refreshLayout.setAdapter(mLoadMoreWrapper);
        refreshLayout.setEmptyView(R.layout.view_loading);
        refreshLayout.showEmptyView();

        loadingLayout = refreshLayout.getEmptyView();
        loadingIcon = (ImageView) loadingLayout.findViewById(R.id.loading_icon);
        loadingText = (TextView) loadingLayout.findViewById(R.id.loading_text_tip);

        ImageUtils.loadCircleImage(mContext, R.drawable.img_reimu_anxious, loadingIcon);
    }

    @Override
    protected void setListener(View rootView) {
        refreshLayout.setOnLoadMoreListener(new RefreshRecyclerView.SimpleOnLoadMoreListener(){
            @Override
            public void onPullToRefersh() {
                loadDataFromNet(true);
            }
        });

        loadingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setRefreshing(true);

                loadingText.setText(mContext.getString(R.string.loading));
                ImageUtils.loadCircleImage(mContext, R.drawable.img_reimu_anxious, loadingIcon);

                loadDataFromNet(true);
//                mPresenter.loadDataFromNet();
            }
        });

        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if(mAdapter.getItemCount()>0){
                    loadDataFromNet(false);
//                    mPresenter.loadDataFromNet();
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mDatas = (List<ComicEntity>) savedInstanceState.getSerializable(KEY_DATAS);
            refreshLayout.hideEmptyView();
        }
        mAdapter.setDatas(mDatas);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_DATAS, (Serializable) mDatas);
        super.onSaveInstanceState(outState);
    }


    /** 加载成功后的处理 */
    @Override
    public void onSuccessLoad(List<ComicEntity> datas) {
        if(mDatas==null){
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(datas);
        if(mAdapter.addDatas(datas)) {
            mAdapter.notifyDataSetChanged();
            mLoadMoreWrapper.notifyDataSetChanged();
        }
        refreshed();
    }

    @Override
    public void loadFailTip() {
        Snackbar.make(refreshLayout, mContext.getString(R.string.loading_fail), Snackbar.LENGTH_SHORT).show();
        loadingText.setText(R.string.loading_fail);
        ImageUtils.loadCircleImage(mContext, R.drawable.img_reimu_sad, loadingIcon);
        refreshed();
    }

    @Override
    public void noMoreDataTip() {
        Snackbar.make(refreshLayout, mContext.getString(R.string.loading_no_more), Snackbar.LENGTH_SHORT).show();
        loadingText.setText(R.string.loading_no_more);
        ImageUtils.loadCircleImage(mContext, R.drawable.img_reimu_sad, loadingIcon);
        refreshed();
    }

    @Override
    public void refreshed() {
        if(mAdapter.getItemCount()>0){
            refreshLayout.hideEmptyView();
        } else{
            refreshLayout.showEmptyView();
        }
        refreshLayout.setRefreshing(false);
    }


    protected void loadDataFromNet(boolean reload){
        if(reload){
            presenter().init();
            mAdapter.clearDatas();
            mAdapter.notifyDataSetChanged();
            mLoadMoreWrapper.notifyDataSetChanged();

            refreshLayout.showEmptyView();
        }
        presenter().loadDataFromNet();
    }

    protected abstract IAlbumsPresenter presenter();

    protected int ItemLayoutId(){
        return R.layout.list_item_albums;
    }
}
