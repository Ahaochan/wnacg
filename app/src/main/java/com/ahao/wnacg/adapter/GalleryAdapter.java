package com.ahao.wnacg.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.ahao.androidlib.adapter.recyclerView.CommonAdapter;
import com.ahao.androidlib.adapter.recyclerView.ViewHolder;
import com.ahao.wnacg.R;
import com.ahao.wnacg.entity.ComicEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Avalon on 2016/6/12.
 */
public class GalleryAdapter<T> extends CommonAdapter<T> {
    private static final String TAG = "GalleryAdapter";
    private static final String[] menuItems = new String[]{"刷新", "分享", "保存"};

    private ComicEntity mComicEntity;
    private Context mContext;
    private OnAdapterListener listener;

    public interface OnAdapterListener{
        void onChangePosition(int position);
        void onSharePic(String s);
    }
    public static abstract class SimpleOnAdapterListener implements OnAdapterListener{
        @Override
        public void onChangePosition(int position) {

        }
        @Override
        public void onSharePic(String s) {

        }
    }

    public GalleryAdapter(Context context, int layoutId, List<T> datas, ComicEntity comicEntity) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mComicEntity = comicEntity;
    }

    public void setOnAdapterListener(OnAdapterListener listener){
        this.listener = listener;
    }

    @Override
    protected void convert(final ViewHolder holder, final T data, final int position) {
        /** 监听当前页数 */
        if(listener!=null){
            listener.onChangePosition(position);
        }

        /** 加载当前页数 */
        holder.setText(R.id.item_text_num, position+"");

        /** 加载图片 */
        Log.i(TAG, "convert: "+data);
        loadImage(holder, String.valueOf(data));

        /** 长按监听 */
        holder.setOnLongClickListener(R.id.item_root, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("第"+position+"页")
                        .setItems(menuItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case 0:/** 刷新 */
                                        holder.setVisible(R.id.item_text_error, false);
                                        holder.setVisible(R.id.item_progress, true);
                                        loadImage(holder, String.valueOf(data));
                                        break;
                                    case 1:/** 分享 */
                                        sharePic(mContext, String.valueOf(data));
                                        if(listener!=null){
                                            listener.onSharePic(String.valueOf(data));
                                        }
                                        break;
                                    case 2:/** 保存 */
                                        break;
                                }
                            }
                        })
                        .create()
                        .show();

                return true;
            }
        });
    }

    /** 加载图片 */
    private ViewHolder loadImage(final ViewHolder holder, String data){
        holder.setImage(R.id.item_img, data, new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                holder.setVisible(R.id.item_text_error, true);
                holder.setVisible(R.id.item_progress, false);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.setMargin(R.id.item_layout_tip, 0);
                holder.setVisible(R.id.item_text_error, false);
                return false;
            }
        });
        return holder;
    }


    public void sharePic(final Context context, final String picUrl) {
        Observable.just(picUrl)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Uri>() {
                    @Override
                    public Uri call(String url) {
                        FutureTarget<File> future = Glide.with(mContext)
                                .load(url)
                                .downloadOnly(500, 500);
                        File cacheFile = null;
                        try {
                            cacheFile = future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        return Uri.fromFile(cacheFile);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        sendIntent.setType("image/*");
                        context.startActivity(Intent.createChooser(sendIntent, "发送神♂秘图片"));
                    }
                });


    }
}
