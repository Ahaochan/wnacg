package com.ahao.wnacgnet.net;

import android.util.Log;

import com.ahao.wnacgnet.entity.URLData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avalon on 2016/5/13.
 */
public class RequestManager {
    private static String className = RequestManager.class.getSimpleName();
    private static RequestManager requestManager;

    private RequestManager() {
    }

    private static List<HttpRequest> requestList = new ArrayList<HttpRequest>();

    public static RequestManager getInstance() {
        if (requestManager == null) {
            synchronized(RequestManager.class) {
                if (requestManager == null) {
                    requestManager = new RequestManager();
                }
            }
        }
        return requestManager;
    }

    public boolean addRequest(HttpRequest request) {
        return requestList.add(request);
    }

    public void cancelRequest() {
        if (requestList != null && requestList.size() > 0) {
            for (final HttpRequest request : requestList) {
                if (request.getCall() != null) {
                    try {
                        request.getCall().cancel();
                    } catch (Exception e) {
                        Log.e(className, "Exception");
                        e.printStackTrace();
                    }
                }
            }
            requestList.clear();
        }
    }

    /**
     * @param urlData
     * @param callback
     * @return
     */
    public HttpRequest createRequest(final URLData urlData, final RequestCallback callback) {
        return createRequest(urlData, null, callback);
    }

    /**
     * @param urlData
     * @param params
     * @param callback
     * @return
     */
    public HttpRequest createRequest(final URLData urlData,
                                     final List<RequestParameter> params,
                                     final RequestCallback callback) {
        final HttpRequest request = new HttpRequest(urlData, params, callback);
        addRequest(request);
        return request;
    }

}
