package com.ahao.wnacgnet.net;

import android.os.Handler;
import android.util.Log;

import com.ahao.wnacgnet.entity.URLData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Avalon on 2016/5/13.
 */
public class HttpRequest implements Runnable {
    private static String className = HttpRequest.class.getSimpleName();

    private String url;
    private URLData urlData = null;
    private RequestCallback requestCallback = null;
    private List<RequestParameter> parameter = null;


    private OkHttpClient httpClient;
    private Request request;
    private Response response;


    private Call call;
    private Handler handler;

    public HttpRequest(final URLData urlData, final RequestCallback callback){
        this(urlData,null, callback);
    }

    public HttpRequest(final URLData urlData, final List<RequestParameter> params, final RequestCallback callback){
        this.urlData = urlData;
        this.url = urlData.getUrl();
        this.parameter = params;
        requestCallback = callback;
        if(httpClient == null) {
            httpClient = new OkHttpClient();
        }
        handler = new Handler();
    }

    /**
     *
     * @return 获取OKHttp的Call
     */
    public Call getCall(){
        return call;
    }

//  /albums-index-page-1-sname-123.html//名字查找
//  /albums-index-page-1-tag-123.html//tag查找
//  /albums-index-cate-5.html//种类
//  /photos-index-aid-28019.html//单个漫画详情页面
//  /feed-index-aid-28019.html//单个漫画所有图片
    @Override
    public void run() {
        try {
            request = new Request.Builder().url(getUrl())
                    .header("User-Agent", urlData.getUserAgent())
                    .build();
            call = httpClient.newCall(request);
            response = call.execute();

            if(requestCallback!=null) {
                if (response.isSuccessful()) {
                    Log.i("HomeJsoup", urlData.toString());
                    final String html = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallback.onSuccess(html);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallback.onFail("网络异常");
                        }
                    });
                }
            }

        } catch (IOException e) {
            Log.e(className, "IOException");
            e.printStackTrace();
        }

    }

    private String getUrl(){
        final StringBuffer paramBuffer = new StringBuffer();
        if(urlData.getNetType().equals("GET")){
            if(parameter!=null && parameter.size()>0){
                for(final RequestParameter p:parameter){
                    if(paramBuffer.length() == 0){
                        paramBuffer.append(p.getKey());
                    } else{
                        paramBuffer.append("-"+p.getKey());
                        if(!p.valueIsEmpty()){
                            paramBuffer.append("-"+p.getValue());
                        }
                    }
                }
            }
        } else if (urlData.getNetType().equals("POST")){

        } else {
            return null;
        }
        return url + "/" + paramBuffer.toString()+".html";
    }
}
