package com.ahao.wnacg.presenter.impl;

/**
 * Created by Avalon on 2016/6/8.
 * 读取数据的接口
 */
public interface ILoadData {

    void loadData();
    void loadDataFromNet();//从网络读取数据
    void loadDataFromLocal();//从本地读取数据
}
