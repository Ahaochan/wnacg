package com.ahao.wnacg.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahao.wnacg.R;
import com.ahao.wnacg.engine.GlideEngine;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Avalon on 2016/5/16.
 */
public class PicRecyclerAdapter extends RecyclerView.Adapter<PicRecyclerAdapter.PicViewHolder> {
    private final static String className = PicRecyclerAdapter.class.getSimpleName();
    private Context context;
    private List<String> picUrlList;


    public PicRecyclerAdapter(Context context,List<String> picUrlList){
        this.context = context;
        this.picUrlList = picUrlList;

    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_read, parent, false));
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {
        holder.currentText.setText(String.valueOf(getItemCount()-position));

        GlideEngine.init(context, picUrlList.get(position), holder.currentPic);
    }

    @Override
    public int getItemCount() {
        return picUrlList==null?0:picUrlList.size();
    }



    public static class PicViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.content_image) ImageView currentPic;
        @BindView(R.id.content_text) TextView currentText;
        public PicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
