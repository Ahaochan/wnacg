package com.ahao.wnacg.net.callback;

import android.util.Log;

import com.ahao.wnacg.entity.ComicEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Avalon on 2016/9/3.
 */
public abstract class ComicCallback extends Callback<ComicEntity> {
    private static final String TAG = "ComicCallback";

    private ComicEntity mComicEntity;
    public ComicCallback(ComicEntity comicEntity){
        mComicEntity = comicEntity;
    }

    @Override
    public ComicEntity parseNetworkResponse(Response response, int id) throws Exception {
        Document doc = Jsoup.parse(response.body().string());

        getThumbList(doc, mComicEntity);
        getTag(doc, mComicEntity);
        getOther(doc, mComicEntity);

        return mComicEntity;
    }

    private static void getThumbList(Document doc, ComicEntity comic) {
        Elements thumbEles = doc.getElementsByClass("lazy");
        List<String> thumbList = new ArrayList<String>();
        for (int i = 2; i < thumbEles.size(); i++) {
            thumbList.add(thumbEles.get(i).attr("data-original"));
        }
        comic.setThumbUrlList(thumbList);
    }

    private static void getOther(Document doc, ComicEntity comicEntity) {
        Elements otherEles = doc.getElementsByTag("p");
        comicEntity.setIntroduce(otherEles.get(0).text().replace("簡介：", ""));//获取简介
        comicEntity.setUpdater(otherEles.get(1).text());//获取上传者
        for(int i = 0; i < otherEles.size(); i++){
//            填充简介和作者信息
            Log.i("DetailThread", otherEles.get(i).text());
        }
    }

    private static void getTag(Document doc, ComicEntity comicEntity) {
        List<String> tagList = new ArrayList<String>();
        Elements tagEles = doc.getElementsByClass("tagshow");
        for (int i = 0; i < tagEles.size(); i++) {
            tagList.add(tagEles.get(i).text());
        }
        comicEntity.setTag(tagList);//获取tag
    }
}
