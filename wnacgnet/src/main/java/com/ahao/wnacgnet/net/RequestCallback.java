package com.ahao.wnacgnet.net;

/**
 * Created by Avalon on 2016/5/13.
 */
public interface RequestCallback {
    public void onSuccess(String content);
    public void onFail(String errorMessage);
}
