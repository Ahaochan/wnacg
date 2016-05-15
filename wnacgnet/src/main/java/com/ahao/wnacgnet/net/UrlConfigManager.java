package com.ahao.wnacgnet.net;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.ahao.wnacgnet.R;
import com.ahao.wnacgnet.entity.URLData;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Avalon on 2016/5/12.
 */
public class UrlConfigManager {
    private static String className = UrlConfigManager.class.getSimpleName();

    public static URLData findURL(final Activity activity, final String findkey) {
        final XmlResourceParser xmlParser = activity.getApplication().getResources().getXml(R.xml.url);
        int eventCode;
        try {
            eventCode = xmlParser.getEventType();
            while (eventCode != XmlResourceParser.END_DOCUMENT) {
                switch (eventCode){
                    case XmlResourceParser.START_DOCUMENT:break;
                    case XmlResourceParser.START_TAG:
                        if(xmlParser.getName().equals("Node")){
                            final String key = xmlParser.getAttributeValue(null, "Key");
                            if(key.trim().equals(findkey)){
                                final URLData urlData = new URLData();
                                urlData.setKey(key);
                                urlData.setReadTime(xmlParser.getAttributeIntValue(null, "ReadTime", 5000));
                                urlData.setUserAgent(xmlParser.getAttributeValue(null, "User_Agent"));
                                urlData.setNetType(xmlParser.getAttributeValue(null, "Net_Type"));
                                urlData.setUrl(xmlParser.getAttributeValue(null, "Url"));
                                urlData.setExpires(Long.parseLong(xmlParser.getAttributeValue(null, "Expires")));
                                return urlData;
                            }
                        }
                        break;
                    case XmlResourceParser.END_TAG:break;
                    default:break;
                }
                eventCode = xmlParser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(className, "XmlPullParserException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(className, "IOException");
            e.printStackTrace();
        }
        return null;
    }
}
