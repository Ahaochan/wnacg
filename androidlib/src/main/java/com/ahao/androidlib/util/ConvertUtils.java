package com.ahao.androidlib.util;

import android.content.Context;
import android.support.annotation.CheckResult;

/**
 * Created by Avalon on 2016/8/9.
 */
public class ConvertUtils {
    private ConvertUtils(){}

    /** int转boolean */
    @CheckResult
    public static boolean int2boolean(int integer) {
        return integer != 0;
    }

    /** boolean转int */
    @CheckResult
    public static int boolean2int(boolean bool) {
        return bool ? 1 : 0;
    }

    /** 安全地转换int类型,失败返回defaultValue */
    @CheckResult
    public static int parseIntSafely(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    /** 安全地转换int类型,失败返回defaultValue */
    @CheckResult
    public static int parseIntSafely(CharSequence str, int defaultValue) {
        try {
            return Integer.parseInt(String.valueOf(str));
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    /** 安全地转换long类型,失败返回defaultValue */
    @CheckResult
    public static long parseLongSafely(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    /** 安全地转换float类型,失败返回defaultValue */
    @CheckResult
    public static float parseFloatSafely(String str, float defaultValue) {
        try {
            return Float.parseFloat(str);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    /** px转dip */
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }
    /** dip转px */
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue*scale+0.5f);
    }
    /** px转sp */
    public static int px2sp(Context context, float pxValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }
    /** sp转px */
    public static int sp2px(Context context, float spValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }

}
