package com.ahao.wnacg.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Avalon on 2016/5/6.
 */
public class GlideEngine {
    private static GlideEngine instance;

    private GlideEngine(){}
    public static GlideEngine getInstance(){
        if(instance == null){
            synchronized(GlideEngine.class) {
                if(instance == null) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }

    public static void init(Context context, String path, ImageView imageView){
        Glide.with(context).load(path).thumbnail(0.1f).into(imageView);
    }

}
