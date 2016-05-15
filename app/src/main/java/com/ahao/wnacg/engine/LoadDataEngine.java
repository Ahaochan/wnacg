package com.ahao.wnacg.engine;

import android.app.Activity;
import android.util.Log;

import com.ahao.wnacg.common.Common;
import com.ahao.wnacgnet.entity.URLData;
import com.ahao.wnacgnet.net.DefaultThreadPool;
import com.ahao.wnacgnet.net.HttpRequest;
import com.ahao.wnacgnet.net.RequestCallback;
import com.ahao.wnacgnet.net.RequestManager;
import com.ahao.wnacgnet.net.RequestParameter;
import com.ahao.wnacgnet.net.UrlConfigManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avalon on 2016/5/13.
 */
public class LoadDataEngine {
    private static String className = LoadDataEngine.class.getSimpleName();
    private static LoadDataEngine engine;
    private LoadDataEngine(){}

    private static RequestManager requestManager = RequestManager.getInstance();;

    public static LoadDataEngine getInstance(){
        if(engine == null) {
            synchronized (LoadDataEngine.class) {
                if (engine == null) {
                    engine = new LoadDataEngine();
                }
            }
        }
        return engine;
    }


    public void loadData(final Activity activity, final String nodeKey, final RequestCallback callback){
        this.loadData(activity, nodeKey, null, callback);
    }

    public void loadData(final Activity activity, final String nodeKey,
                         final List<RequestParameter> params, final RequestCallback callback){
        URLData urlData = UrlConfigManager.findURL(activity, nodeKey);
        HttpRequest httpRequest = requestManager.createRequest(urlData, params, callback);
        DefaultThreadPool.getInstance().execute(httpRequest);
    }



    final public static String ALBUMS = "albums";
    final public static String INDEX = "index";
    final public static String PAGE = "page";
    final public static String SNAME = "sname";
    final public static String TAG = "tag";
    final public static String CATE = "cate";
    final public static String PHOTOS = "photos";
    final public static String AID = "aid";
    final public static String FEED = "feed";


//  /albums-index-page-1-sname-123.html//名字查找
//  /albums-index-page-1-tag-123.html//tag查找
//  /albums-index-page-1-cate-5.html//种类
//  /photos-index-aid-28019.html//单个漫画详情页面
//  /feed-index-aid-28019.html//单个漫画所有图片
    public static List<RequestParameter> getParamsOfSearchSname(int page, String name){
        List<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter(ALBUMS, null));
        params.add(new RequestParameter(INDEX, null));
        params.add(new RequestParameter(PAGE, String.valueOf(page)));
        params.add(new RequestParameter(SNAME, name));
        return params;
    }
    public static List<RequestParameter> getParamsOfSearchTag(int page, String tag){
        List<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter(ALBUMS, null));
        params.add(new RequestParameter(INDEX, null));
        params.add(new RequestParameter(PAGE, String.valueOf(page)));
        params.add(new RequestParameter(TAG, tag));
        return params;
    }
    public static List<RequestParameter> getParamsOfSearchCate(int page, int cate){
        List<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter(ALBUMS, null));
        params.add(new RequestParameter(INDEX, null));
        params.add(new RequestParameter(PAGE, String.valueOf(page)));
        params.add(new RequestParameter(CATE, String.valueOf(cate)));
        return params;
    }
    public static List<RequestParameter> getParamsOfPhotos(String comicID){
        List<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter(PHOTOS, null));
        params.add(new RequestParameter(INDEX, null));
        params.add(new RequestParameter(AID, comicID));
        return params;
    }
    public static List<RequestParameter> getParamsOfFeed(String comicID){
        List<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter(FEED, null));
        params.add(new RequestParameter(INDEX, null));
        params.add(new RequestParameter(AID, comicID));
        return params;
    }
}
