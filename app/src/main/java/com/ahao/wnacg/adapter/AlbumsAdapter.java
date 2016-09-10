package com.ahao.wnacg.adapter;

import android.content.Context;
import android.view.View;

import com.ahao.androidlib.adapter.recyclerView.CommonAdapter;
import com.ahao.androidlib.adapter.recyclerView.ViewHolder;
import com.ahao.wnacg.R;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.ui.activity.DetailActivity;

import java.util.List;

/**
 * Created by Avalon on 2016/6/5.
 */
public class AlbumsAdapter extends CommonAdapter<ComicEntity> {
    private Context mContext;

    public AlbumsAdapter(Context context, int layoutId, List<ComicEntity> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(final ViewHolder holder, final ComicEntity data, int position) {
        holder.setImage(R.id.albums_item_head, data.getThumbUrl());
        holder.setText(R.id.albums_item_name, data.getTitle());
        holder.setText(R.id.albums_item_pager, data.getPicNum()+"页");
        holder.setText(R.id.albums_item_time, data.getCreateTime());

        holder.setOnClickListener(R.id.albums_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.startAction(mContext, data, holder.getView(R.id.albums_item_head));
            }
        });
    }
}
