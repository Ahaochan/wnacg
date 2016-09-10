package com.ahao.wnacg.cache;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;

/**
 * Created by Avalon on 2016/8/27.
 */
public class GlideModule implements com.bumptech.glide.module.GlideModule {
    private static final String TAG = "GlideModule";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "GlideCache",  DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
