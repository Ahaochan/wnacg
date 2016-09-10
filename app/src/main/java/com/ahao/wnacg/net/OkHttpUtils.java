package com.ahao.wnacg.net;

import com.ahao.wnacg.net.builder.GetBuilder;
import com.ahao.wnacg.net.callback.Callback;
import com.ahao.wnacg.net.request.RequestCall;
import com.ahao.wnacg.net.utils.Platform;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    /** 默认超时时间 */
    public static final long DEFAULT_MILLISECONDS = 1_000_000L;

    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    private volatile static OkHttpUtils mInstance;
    private OkHttpUtils(OkHttpClient okHttpClient) {
        mOkHttpClient = (okHttpClient==null ? new OkHttpClient() : okHttpClient);
        mPlatform = Platform.get();
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

//    public static GetBuilder post() {}


    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null) {
            callback = Callback.CALLBACK_DEFAULT;
        }
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }
                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}

