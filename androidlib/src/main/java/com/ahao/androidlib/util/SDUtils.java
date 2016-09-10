package com.ahao.androidlib.util;

import android.content.Context;

import java.io.File;

/**
 * Created by Avalon on 2016/8/27.
 * SD卡工具类
 */
public class SDUtils {
    private SDUtils(){}

    /**
     * sd卡存在,获得/sdcard/Android/data/<application package>/cache/<fileName>
     * sd卡不存在,获得/data/data/<application package>/cache/<fileName>
     */
    public static File getDiskCacheDir(Context context, String fileName) {
        File cachePath;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            cachePath = new File(context.getExternalCacheDir(), fileName);
        } else {
            cachePath = new File(context.getCacheDir(), fileName);
        }
        /** 确保cachePath创建成功 */
        FileUtils.ensureDirectory(cachePath);
        return cachePath;
    }

    /**
     * sd卡存在,获得/sdcard/Android/data/<application package>/file/<fileName>
     * sd卡不存在,获得/data/data/<application package>/file/<fileName>
     */
    public static File getDiskFilesDir(Context context, String fileName) {
        File cachePath;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            cachePath = new File(context.getExternalFilesDir(null), fileName);
        } else {
            cachePath = new File(context.getFilesDir(), fileName);
        }
        /** 确保cachePath创建成功 */
        FileUtils.ensureDirectory(cachePath);
        return cachePath;
    }
}
