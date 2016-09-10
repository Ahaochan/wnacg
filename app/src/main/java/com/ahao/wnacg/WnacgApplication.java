package com.ahao.wnacg;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ahao.wnacg.cache.ComicCache;
import com.ahao.wnacg.cache.HomeCache;
import com.ahao.wnacg.net.OkHttpUtils;
import com.bumptech.glide.Glide;

import okhttp3.OkHttpClient;

/**
 * Created by Avalon on 2016/5/9.
 */
public class WnacgApplication extends Application {
    private static final String TAG = "WnacgApplication";
    private static String webSite;


    @Override
    public void onCreate() {
        super.onCreate();
//        Bmob.initialize(this, getResources().getString(R.string.bmob_id));
        Log.i(TAG, "onCreate: "+ Glide.getPhotoCacheDir(getApplicationContext()));
        HomeCache.init(getApplicationContext());
        ComicCache.init(getApplicationContext());

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static String getWebSite(Context context) {
        if(webSite==null || webSite.isEmpty()){
            webSite = PreferenceManager.getDefaultSharedPreferences(context).getString("web_site", "www.wnacg.com");
        }
        return webSite;
    }
}
