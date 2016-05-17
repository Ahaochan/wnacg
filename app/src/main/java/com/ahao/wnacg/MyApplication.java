package com.ahao.wnacg;

import android.app.Application;

import com.ahao.wnacg.util.ComicDataManager;
import com.ahao.wnacg.engine.GlideEngine;

/**
 * Created by Avalon on 2016/5/9.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ComicDataManager.getInstance();
        GlideEngine.getInstance();
    }
}
