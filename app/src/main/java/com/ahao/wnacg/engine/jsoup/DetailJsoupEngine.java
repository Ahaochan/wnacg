package com.ahao.wnacg.engine.jsoup;

import android.util.Log;

import com.ahao.androidlib.util.StringUtil;
import com.ahao.wnacg.common.HttpProperty;
import com.ahao.wnacg.entity.ComicData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avalon on 2016/5/14.
 * <p/>
 * <p/>
 * <p/>
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
 * <p/>
 * 获取信息:
 * Updater:上传者
 * Introduce:简介
 * tag:tag的List,用来进行查找搜索
 * feedUrl:存储所有图片的url地址,获取picUrlList
 * <p/>
 * 未知信息:
 * picUrlList:所有漫画图片的url集合,用来更新RecycleView
 */
public class DetailJsoupEngine {
    private final static String className = DetailJsoupEngine.class.getSimpleName();

    private DetailJsoupEngine() {
    }

    private static ComicData comicData;

    public static ComicData UpdateComicData(String homeUrl, String html, ComicData comic) {
        comicData = comic;

        Log.i(className, "开始爬");
        Document doc = Jsoup.parse(html);
//        Log.i(className, doc.toString());

        List<String> tagList = new ArrayList<String>();
        Elements tags = doc.getElementsByClass("tagshow");
//        Log.i("DetailThread", tags.get(i).text());
        Elements introduces = doc.getElementsByClass("intro");
//        Log.i("DetailThread", tags.get(i).text());
        Elements others = doc.getElementsByTag("p");
//        Log.i("DetailThread", others.get(i).text());

        for (int i = 0; i < tags.size(); i++) {
            tagList.add(tags.get(i).text());
        }

        comicData.setTag(tagList);
        comicData.setIntroduce(introduces.get(0).text().replace("簡介：",""));//获取简介
        comicData.setUpdater(others.get(0).text().replace("作者：",""));//获取上传者
//        for(int i = 0; i < others.size(); i++){
//            填充简介和作者信息
//            Log.i("DetailThread", others.get(i).text());
//        }

        Log.i(className, comicData.getTag().toString());
        Log.i(className, comicData.getIntroduce());
        Log.i(className, comicData.getUpdater());
        Log.i(className, "爬完了");
        return comicData;
    }

    private void getDetailMSG(String homeUrl, String html) {
        Document doc = Jsoup.parse(html);

        List<String> tagList = new ArrayList<String>();
        Elements tags = doc.getElementsByClass("tagshow");
//        Log.i("DetailThread", tags.get(i).text());
        Elements others = doc.getElementsByTag("p");
//        Log.i("DetailThread", others.get(i).text());
        for (int i = 0; i < tags.size(); i++) {
            tagList.add(tags.get(i).text());
        }

        comicData.setTag(tagList);
        comicData.setIntroduce(others.get(0).text());//获取简介
        comicData.setUpdater(others.get(1).text());//获取上传者
//        for(int i = 0; i < others.size(); i++){
//            填充简介和作者信息
//            Log.i("DetailThread", others.get(i).text());
//        }


        Elements firsts = doc.getElementsByClass("pic_box");
//        getPicListMSG(getHomeUrl()+firsts.get(0).getElementsByTag("a").attr("href"));
    }

    private void getPicListMSG(String firctUrl) throws IOException {
        Document doc = Jsoup.connect(firctUrl)
                .timeout(5000)
                .userAgent(HttpProperty.DESKTOP_USERAGENT)
                .get();

        Elements firstEles = doc.getElementsByClass("photo");
        String url = firstEles.get(0).attr("src");
        int first = Integer.parseInt(StringUtil.getNumOfString(url, 3));
        List<String> list = new ArrayList<String>();
        String pre = StringUtil.getPreChildsAtSplit(url, "/", 6);
        String next = StringUtil.getNextChildsAtSplit(url, "/", 6);
        String noNumNext = StringUtil.getNotNumOfString(next, 0);
        for (int i = 0; i < comicData.getPicNum(); i++) {
            list.add(pre + String.format("%0" + StringUtil.getNumLengthOfString(next, 1) + "d", first + i) + noNumNext);
        }

//        comicData.setFirstPicURL(list.get(0));
        comicData.setPicUrlList(list);
    }
}
