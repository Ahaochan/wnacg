package com.ahao.androidlib.adapter.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ahao.androidlib.adapter.recyclerView.base.ItemViewDelegate;
import com.ahao.androidlib.adapter.recyclerView.base.ItemViewDelegateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avalon on 2016/6/7.
 */
public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "MultiItemTypeAdapter";
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;

    public MultiItemTypeAdapter(Context context, List<T> datas){
        this.mContext = context;
        this.mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();

        ViewHolder holder = ViewHolder.get(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());

        setListener(parent, holder, viewType);
        return holder;
    }

    protected void onViewHolderCreated(ViewHolder holder,View itemView){}

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(view, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    public boolean addDatas(List<T> newDatas){
        if(mDatas==null){
            mDatas = new ArrayList<T>();
        }

        if(newDatas!=null && newDatas.size()>0) {
            mDatas.addAll(newDatas);
            notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void clearDatas() {
        if (mDatas != null) {
            mDatas.clear();
        }
    }

    public boolean setDatas(List<T> newDatas){
        clearDatas();
        return addDatas(newDatas);
    }

    public List<T> getDatas() {
        return mDatas;
    }





    /**
     * 多种数据支持
     */
    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder,  int position);
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
