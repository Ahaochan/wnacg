package com.ahao.wnacg.net.callback;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

/**
 * Created by Avalon on 2016/9/4.
 */
public abstract class PicUrlCallback extends Callback<List<String>> {
    @Override
    public List<String> parseNetworkResponse(Response response, int id) throws Exception {
        List<String> picList = new ArrayList<String>();

        String regular = "(http://[\\s\\S]+?)\\\"";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(response.body().string());
        while(matcher.find()){
            String group = matcher.group(1);
            picList.add(group.substring(0, group.lastIndexOf("\\")));
        }
        return picList;
    }
}
