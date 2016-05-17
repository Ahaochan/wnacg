package com.ahao.wnacg.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.ahao.wnacg.R;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Created by Avalon on 2016/5/6.
 */
public class GlideEngine {
    private static GlideEngine instance;

    private GlideEngine() {
    }

    public static GlideEngine getInstance() {
        if (instance == null) {
            synchronized (GlideEngine.class) {
                if (instance == null) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }

    public static void init(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .error(R.drawable.ic_load_fail)//load失敗的Drawable
                .placeholder(R.drawable.ic_loading)//loading時候的Drawable
                .fitCenter()
                .crossFade()//增加图片显示时候的淡入淡出动画
                .into(imageView);
    }
}
