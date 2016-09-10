package com.ahao.wnacg.net.callback;

import android.content.Context;
import android.preference.PreferenceManager;

import com.ahao.androidlib.util.ConvertUtils;
import com.ahao.androidlib.util.StringUtil;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.entity.ComicEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Avalon on 2016/9/4.
 */
public abstract class AlbumsCallback extends Callback<List<ComicEntity>> {


    private Context mContext;
    private int albumsMaxPage = 0;

    public AlbumsCallback(Context context){
        this.mContext = context;
    }

    @Override
    public List<ComicEntity> parseNetworkResponse(Response response, int id) throws Exception {
        List<ComicEntity> list = new ArrayList<ComicEntity>();
        String homeUrl = "http://"+ PreferenceManager.getDefaultSharedPreferences(mContext).getString(Common.KEY_WEB_SITE, "www.wnacg.com");

        Document doc = Jsoup.parse(response.body().string());
        albumsMaxPage = getMaxPage(doc);

        Elements IndexUrls = doc.getElementsByClass("comic_list_view");
        Elements thumbUrls = doc.getElementsByClass("list_h");//
        Elements titles = doc.getElementsByClass("title_name");
        Elements others = doc.getElementsByClass("col_99");

        for(int i = 0; i < IndexUrls.size(); i++){
            ComicEntity comicEntity = new ComicEntity();
            /** 获取detail地址 */
            comicEntity.setDetailUrl(homeUrl + IndexUrls.get(i).attr("href"));
            /** 获取漫画id */
            comicEntity.setAId(StringUtil.getIntOfString(comicEntity.getDetailUrl(), 1));
            /** 获取缩略图地址 */
            comicEntity.setThumbUrl(thumbUrls.get(i).getElementsByTag("img").attr("src"));
            /** 获取漫画标题 */
            comicEntity.setTitle(titles.get(i).text());

            /** 获取上传时间 */
            String createTime = StringUtil.getIntOfString(others.get(2*i+1).text(), 1)+"-"+
                    StringUtil.getIntOfString(others.get(2*i+1).text(), 2)+"-"+
                    StringUtil.getIntOfString(others.get(2*i+1).text(), 3);
            comicEntity.setCreateTime(createTime);

            /** 获取页数 */
            int picNum = Integer.parseInt(StringUtil.getIntOfString(others.get(2*i).text(), 1));
            comicEntity.setPicNum(picNum);

            /** 获取上传时间和页数 */
            String timeAndPic = createTime+", "+picNum;
            comicEntity.setTimeAndPic(timeAndPic);

            list.add(comicEntity);
        }

        return list;
    }

    private static int getMaxPage(Document doc){
        Element div = doc.getElementsByClass("paginator").first();
        Elements a = div.getElementsByTag("a");
        int maxPage = -1;
        for (int i = 0; i < a.size(); i++) {
            int page = ConvertUtils.parseIntSafely(a.get(i).text(), -1);
            if(page>maxPage){
                maxPage = page;
            }
        }
        return maxPage;
    }

    public int getAlbumsMaxPage() {
        return albumsMaxPage;
    }
}
