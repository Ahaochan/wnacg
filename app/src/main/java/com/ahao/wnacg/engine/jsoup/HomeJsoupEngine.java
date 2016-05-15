package com.ahao.wnacg.engine.jsoup;

import android.util.Log;

import com.ahao.androidlib.util.StringUtil;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.entity.ComicData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Avalon on 2016/5/13.
 * <p/>
 * 根据传入的String类型的html,进行获取数据
 * <p/>
 * 已知信息:
 * <p/>
 * 获取信息:
 * DetailURL:详情页面,用来获取简介等详细信息
 * Id:漫画的id,用来唯一指定一部漫画,进行硬盘缓存
 * ThumbnailURL:缩略图的url,用来读取缩略图
 * Title:漫画的标题
 * TimeAndPic:获取创建时间和图片张数,用来分割获取子信息CreateTime和PicNum
 * CreateTime:获取创建时间
 * PicNum:获取图片张数,使用循环来获取所有漫画图片
 * <p/>
 * 未知信息:
 * Updater:上传者
 * Introduce:简介
 * tag:tag的List,用来进行查找搜索
 * feedUrl:存储所有图片的url地址,获取picUrlList
 * picUrlList:所有漫画图片的url集合,用来更新RecycleView
 */
public class HomeJsoupEngine {
    private static String className = HomeJsoupEngine.class.getSimpleName();
    private static int PLANT_NUM = 4;//4个板块
    private static int CHILD_NUM = 3;//每个板块3个漫画

    private static ComicData[][] comicDatas = new ComicData[PLANT_NUM][CHILD_NUM];
    ;

    private HomeJsoupEngine() {
    }

    public static ComicData[][] getComicDatas(String homeUrl, String html) {
        Document doc = Jsoup.parse(html);

        Elements detailUrls = doc.getElementsByClass("txt_url");
//            Log.i("homeLoad", getHomeUrl()+detailUrls.get(i).attr("href"));
        Elements thumbnailURLs = doc.getElementsByClass("comic_img");
//            Log.i("homeLoad", thumbnailURLs.get(i).attr("src"));
        Elements titles = doc.getElementsByClass("comic_title");
//            Log.i("homeLoad", titles.get(i).text());
        Elements timeAndPics = doc.getElementsByClass("comic_author");
//            Log.i("homeLoad", timeAndPics.get(i).text());

        int k = 0;
        for (int i = 0; i < Common.HOME_PLANT_NUM; i++) {
            for (int j = 0; j < Common.PLANT_IMAGE_NUM; j++) {
                comicDatas[i][j] = new ComicData();

                comicDatas[i][j].setDetailURL(homeUrl + detailUrls.get(k).attr("href"));
                comicDatas[i][j].setId(StringUtil.getNumOfString(comicDatas[i][j].getDetailURL(), 1));
                comicDatas[i][j].setThumbnailURL(thumbnailURLs.get(k).attr("src"));
                comicDatas[i][j].setTitle(titles.get(k).text());

                String timeAndPic = timeAndPics.get(k).text();
                String[] split = timeAndPic.split(", ");
                comicDatas[i][j].setTimeAndPic(timeAndPic);
                comicDatas[i][j].setCreateTime(split[0]);
                comicDatas[i][j].setPicNum(Integer.parseInt(StringUtil.getNumOfString(split[1], 1)));
                k++;
            }
        }
        return comicDatas;
    }
}
