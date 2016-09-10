package com.ahao.wnacg.cache;

import android.content.Context;

import com.ahao.androidlib.util.FileUtils;
import com.ahao.androidlib.util.SDUtils;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.util.AppUtils;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Avalon on 2016/8/26.
 */
public class ComicCache {
    private static final String TAG = "ComicCache";
    private static final long MAX_CACHE_SIZE = 10*1024*1024;
    private static final long EXPIRED = 24*60*60*1000;



    private ComicCache(){}
    private static ComicCache instance;
    public static ComicCache getInstance() {
        if(instance == null) {
            synchronized(HomeCache.class){
                if(instance == null){
                    instance = new ComicCache();
                }
            }
        }
        return instance;
    }

    private static File cachePath;
    private static DiskLruCache diskLruCache;

    public static void init(Context context){
        try {
            cachePath = SDUtils.getDiskCacheDir(context, TAG);
            diskLruCache = DiskLruCache.open(cachePath, AppUtils.getAppVersion(context), 1, MAX_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void save(ComicEntity data) {
        Observable.just(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ComicEntity>() {
                    @Override
                    public void call(ComicEntity comicEntity) {
                        try {
                            DiskLruCache.Editor editor = diskLruCache.edit(comicEntity.getAId());
                            if(editor!=null){
                                OutputStream os = editor.newOutputStream(0);
                                FileUtils.writeSerializable(os, comicEntity);
                                editor.commit();
                            }
                            diskLruCache.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public ComicEntity load(String aId) {
        ComicEntity comicEntity = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(aId);
            if(snapshot!=null){
                InputStream is = snapshot.getInputStream(0);
                comicEntity = (ComicEntity) FileUtils.readSerializable(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comicEntity;
    }


    public boolean isExpired(String aId) {
        try {
            return diskLruCache.get(aId)==null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
