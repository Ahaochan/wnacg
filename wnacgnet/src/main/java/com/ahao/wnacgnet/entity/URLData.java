package com.ahao.wnacgnet.entity;

/**
 * Created by Avalon on 2016/5/13.
 */
public class URLData {
    private String key;
    private int readTime;
    private String userAgent;
    private String netType;
    private String url;
    private long expires;

    @Override
    public String toString() {
        return "key:"+key+"\nreadTime:"+readTime+"\nuserAgent:"+userAgent+
                "\nnetType:"+netType+"\nurl:"+url+"\nexpires:"+expires;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }
    public int getReadTime() {
        return readTime;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getUserAgent() {
        return userAgent;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }
    public String getNetType() {
        return netType;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
    public long getExpires() {
        return expires;
    }
}
