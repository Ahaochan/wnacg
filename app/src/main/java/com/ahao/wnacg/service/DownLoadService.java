package com.ahao.wnacg.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by Avalon on 2016/9/1.
 */
public class DownLoadService extends Service {


    String download_url;
    private int requestCode = (int) SystemClock.uptimeMillis();
//    private NotifyUtil currentNotify;
    File mFile;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        mFile=new File(savePath);
//        download_url=intent.getStringExtra("download_url");
//        Log.e("test","执行onStartCommand");
//        //设置想要展示的数据内容
//        Intent intent_noti = new Intent();
//        intent_noti.setAction(Intent.ACTION_VIEW);
//        //文件的类型，从tomcat里面找
//        intent_noti.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
//        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
//                requestCode, intent_noti, PendingIntent.FLAG_UPDATE_CURRENT);
//        int smallIcon = R.drawable.xc_smaillicon;
//        String ticker = "正在更新快图浏览";
//        //实例化工具类，并且调用接口
//        NotifyUtil notify7 = new NotifyUtil(this, 7);
//        notify7.notify_progress(rightPendIntent, smallIcon, ticker, "快图浏览升级程序", "正在下载中",
//                false, false, false, download_url, savePath, new NotifyUtil.DownLoadListener() {
//                    @Override
//                    public void OnSuccess(File file) {
//                        mFile=file;
//                        DownLoadService.this.stopSelf();
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//
//                    }
//                });
//        currentNotify = notify7;
//        return super.onStartCommand(intent, flags, startId);
        return 0;
    }

}
