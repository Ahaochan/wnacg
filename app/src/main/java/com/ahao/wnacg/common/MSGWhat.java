package com.ahao.wnacg.common;

/**
 * Created by Avalon on 2016/5/10.
 */
public class MSGWhat {
    public final static String EXCEPTION_FROM = "exceptionFrom";


    public final static int RELOAD_HOME = 0x000;//通知UI线程刷新homePager
    public final static int RELOAD_DETAIL = 0x001;//去除DetailActivity中的processDialog


    public final static int EXCEPTION_MALFORMEDURL = 0x100;//MalformedURL异常
    public final static int EXCEPTION_IO = 0x101;//IO异常
}
