package com.ahao.wnacg.ui.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ahao.wnacg.common.MSGWhat;

/**
 * Created by Avalon on 2016/5/10.
 */
public abstract class ExceptionHandler extends Handler {
    Context context;

    public ExceptionHandler(Context context){
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case MSGWhat.EXCEPTION_MALFORMEDURL:
                Toast.makeText(context, "url错误", Toast.LENGTH_SHORT).show();
                exceptionFrom(msg);
                break;
        }
    }

    private void exceptionFrom(Message msg){
        Log.i("ExceptionHandler","调用异常handler,来自:"+msg.getData().getString(MSGWhat.EXCEPTION_FROM));
        Log.i("ExceptionHandler","调用异常handler,信息:"+msg.what);
    }
}
