package com.ahao.wnacg.net.request;


import com.ahao.wnacg.net.callback.Callback;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/11/6.
 */
public abstract class OkHttpRequest {
    private static final String TAG = "OkHttpRequest";
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected int id;

    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers, int id) {
        if (url == null) {
            throw new IllegalArgumentException("url can not be null.");
        }

        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;

        initBuilder();
    }


    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder() {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    protected void appendHeaders() {
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            builder.header(key, headers.get(key));
        }
    }


    public RequestCall build() {
        return new RequestCall(this);
    }


    public Request generateRequest(Callback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);



    public int getId() {
        return id;
    }

}
