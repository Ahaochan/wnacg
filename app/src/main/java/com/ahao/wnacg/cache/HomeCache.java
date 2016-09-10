package com.ahao.wnacg.cache;

import android.content.Context;
import android.util.Log;

import com.ahao.androidlib.util.FileUtils;
import com.ahao.androidlib.util.SDUtils;
import com.ahao.wnacg.entity.ComicEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Avalon on 2016/8/26.
 */
public class HomeCache{
    private static final String TAG = "HomeCache";
    private static final long EXPIRED = 12*60*60*1000;

    private HomeCache(){}
    private static HomeCache instance;
    public static HomeCache getInstance() {
        if(instance == null) {
            synchronized(HomeCache.class){
                if(instance == null){
                    instance = new HomeCache();
                }
            }
        }
        return instance;
    }

    private static File cachePath;
    public static void init(Context context){
        cachePath = SDUtils.getDiskCacheDir(context, TAG);
    }


    public void save(ArrayList<ComicEntity> plants){
        Observable.just(plants)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ArrayList<ComicEntity>>() {
                    @Override
                    public void call(ArrayList<ComicEntity> comicEntity) {
                        FileUtils.writeSerializable(new File(cachePath, TAG), comicEntity, true);
                    }
                });
    }


    public List<ComicEntity> load(){
        List<ComicEntity> list = null;
        if(cachePath.exists()){
            list = (List<ComicEntity>) FileUtils.readSerializable(new File(cachePath, TAG));
        }
        return list;
    }


    public boolean isExpired(){
        File file = new File(cachePath, TAG);
        long nowTime = System.currentTimeMillis();
        long lastTime = file.lastModified();
        if(file.exists() && !file.isFile()){
            lastTime = 0;
        }
        Log.i(TAG, "isExpired: "+nowTime+"-"+lastTime+"="+(nowTime-lastTime)+">"+EXPIRED+":"+(nowTime-lastTime>EXPIRED));
        return (nowTime-lastTime)>EXPIRED;

    }

}
