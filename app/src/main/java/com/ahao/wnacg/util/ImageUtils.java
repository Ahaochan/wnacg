package com.ahao.wnacg.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by Avalon on 2016/9/10.
 */
public class ImageUtils {
    private ImageUtils(){}

    public static void loadCircleImage(final Context context, @DrawableRes int resId, ImageView view){
        Glide.with(context)
                .load(resId)
                .asBitmap()
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
}
