package com.ahao.androidlib.adapter.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ahao.androidlib.adapter.listView.base.ItemViewDelegate;
import com.ahao.androidlib.adapter.listView.base.ItemViewDelegateManager;

import java.util.List;

/**
 * Created by Avalon on 2016/8/31.
 */
public abstract class MultiItemTypeAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected int mLayoutId;
    private ItemViewDelegateManager mItemViewDelegateManager;


    public MultiItemTypeAdapter(Context context, List<T> datas, int layoutId){
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder viewHolder = null ;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = ViewHolder.get(mContext, itemView, parent, layoutId, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }

        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    protected void convert(ViewHolder holder, T item, int position){
        mItemViewDelegateManager.convert(holder, item, position);
    }

    protected void onViewHolderCreated(ViewHolder holder , View itemView ){}

    /**
     * 多种数据支持
     */

    /** 添加itemType种类 */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
        }
        return super.getItemViewType(position);
    }
}
