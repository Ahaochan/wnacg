package com.ahao.wnacg.net.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class HtmlCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        return response.body().string();
    }
}
