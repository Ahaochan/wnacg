package com.ahao.wnacg.engine.jsoup;

import android.util.Log;

import com.ahao.wnacg.entity.ComicData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avalon on 2016/5/15. * <p/>
 * 根据传入的String类型的html,进行获取数据
 * <p/>
 * 已知信息:
 * DetailURL:详情页面,用来获取简介等详细信息
 * Id:漫画的id,用来唯一指定一部漫画,进行硬盘缓存
 * ThumbnailURL:缩略图的url,用来读取缩略图
 * Title:漫画的标题
 * TimeAndPic:获取创建时间和图片张数,用来分割获取子信息CreateTime和PicNum
 * CreateTime:获取创建时间
 * PicNum:获取图片张数,使用循环来获取所有漫画图片
 * Updater:上传者
 * Introduce:简介
 * tag:tag的List,用来进行查找搜索
 * feedUrl:存储所有图片的url地址,获取picUrlList
 * <p/>
 * 获取信息:
 * picUrlList:所有漫画图片的url集合,用来更新RecycleView
 * <p/>
 * 未知信息:
 */

public class ReadJsoupEngine {
    private final static String className = ReadJsoupEngine.class.getSimpleName();
    private static ComicData comicData;
    private ReadJsoupEngine() {}


    public static ComicData getPicList(String html, ComicData comic) {
        comicData = comic;
        Document doc = Jsoup.parse(html);
//        Log.i(className, doc.toString());
        List<String> picList = new ArrayList<String>();

        Elements pics = doc.getElementsByTag("description");
//        Log.i(className, pics.get(i).text());

        for(int i = 0 ; i < pics.size(); i++){
            String imgUrl = pics.get(i).text();
            if (imgUrl.startsWith("<img src=\"")){
                picList.add(imgUrl.replace("<img src=\"","").replace("\" /><br />",""));
            }
        }
        comicData.setPicUrlList(picList);
        return comicData;
    }

}
