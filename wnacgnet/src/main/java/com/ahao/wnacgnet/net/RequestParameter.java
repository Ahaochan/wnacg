package com.ahao.wnacgnet.net;

import java.io.Serializable;

/**
 * Created by Avalon on 2016/5/13.
 */
public class RequestParameter implements Serializable, Comparable<Object> {

    private String key;
    private String value;

    public RequestParameter(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "key:"+key+",value:"+value;
    }

    @Override
    public int compareTo(Object another) {
        int compared;
        final RequestParameter parameter = (RequestParameter) another;
        compared = key.compareTo(parameter.key);
        if(compared == 0) {
            compared = value.compareTo(parameter.value);
        }
        return compared;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if(this == o){
            return true;
        }
        if(o instanceof RequestParameter){
            final RequestParameter parameter = (RequestParameter) o;
            return key.equals(parameter.key) && value.equals(parameter.value);
        }
        return false;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
    public boolean valueIsEmpty() {
        if(getValue() ==null || getValue().trim().equals("")){
            return true;
        } else {
            return false;
        }
    }
    public void setValue(String value) {
        this.value = value;
    }
}
