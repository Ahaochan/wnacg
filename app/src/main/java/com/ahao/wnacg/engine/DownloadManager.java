package com.ahao.wnacg.engine;

/**
 * Created by Avalon on 2016/8/7.
 */
public class DownloadManager {
    private int aId;
    private String name;
    public int progress = 0; //进度，一个0-100之前的整数，标识百分比
    public boolean isCancel = false; //是否已经取消，该开关用于取消下载
}
