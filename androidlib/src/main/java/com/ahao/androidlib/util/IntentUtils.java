package com.ahao.androidlib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringRes;

/**
 * Created by Avalon on 2016/8/14.
 */
public class IntentUtils {
    private IntentUtils(){}

    /** 用默认浏览器打开uri */
    public static Intent openWebDefault(String uri){
        Uri content_url = Uri.parse(uri);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(content_url);
        return intent;
    }

    /** 分享String */
    public static void shareString(Context context, String message){
        shareString(context, null, message);
    }

    public static void shareString(Context context, @StringRes int intentTitleId, String message){
        String intentTitle = context.getString(intentTitleId);
        shareString(context, intentTitle, message);
    }

    public static void shareString(Context context, String intentTitle, String message){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        if(intentTitle!=null && !intentTitle.isEmpty()){
            sendIntent = Intent.createChooser(sendIntent, intentTitle);
        }
        context.startActivity(sendIntent);
    }
}
