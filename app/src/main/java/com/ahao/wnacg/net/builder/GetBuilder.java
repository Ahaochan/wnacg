package com.ahao.wnacg.net.builder;

import android.util.Log;

import com.ahao.wnacg.net.common.Header;
import com.ahao.wnacg.net.request.GetRequest;
import com.ahao.wnacg.net.request.RequestCall;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Avalon on 2016/9/3.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> {
    private static final String TAG = "GetBuilder";

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url).append("/");

        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            sb.append(key);
            if(!params.get(key).trim().equals("")){
                sb.append("-").append(params.get(key));
            }
            if(iterator.hasNext()){
                sb.append("-");
            }
        }
        sb.append(".html");
        return sb.toString();
    }

    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        if(headers == null || !headers.containsKey(Header.User_Agent)){
            addHeader(Header.User_Agent, Header.User_Agent_Type.Mobile_UC);
        }
        Log.i(TAG, "build: "+url);
        return new GetRequest(url, tag, params, headers, id).build();
    }
}
