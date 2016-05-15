package com.ahao.wnacgnet.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Avalon on 2016/5/5.
 * 漫画数据
 */
public class ComicData implements Serializable{
    private String id;//漫画的id,如:28083
    private String title;//漫画标题,如:[いちこ] ビンカン中毒 (ガールズフォーム Vol.8)[沒有漢化]
    private String timeAndPic;//漫画创建时间和照片张数,如:2016-05-10, 20張照片
    private String createTime;//创建时间,如:2016-05-10
    private int picNum;//漫画张数,如:20
    private String updater;//上传者
    private String introduce;//简介
    private String thumbnailURL;//缩略图url,如:http://img.wnacg.us/data/t/0280/83/14628388668089.jpg
    private String detailURL;//详情url,如:http://m.wnacg.org/photos-index-aid-28083.html
    private String firstPicURL;//第一张图的url
    private List<String> tag;//tag的list
    private List<String> picUrlList;//漫画的urlList

    @Override
    public String toString() {
        return "漫画标题:"+title+",漫画详情:"+timeAndPic+",\n创建时间:"+createTime+",漫画张数:"+picNum
                +",缩略图url:"+thumbnailURL+",\n详情url:"+detailURL;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setTimeAndPic(String timeAndPic) {
        this.timeAndPic = timeAndPic;
    }
    public String getTimeAndPic() {
        return timeAndPic;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }
    public int getPicNum() {
        return picNum;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
    public String getUpdater() {
        return updater;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getIntroduce() {
        return introduce;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setDetailURL(String detailURL) {
        this.detailURL = detailURL;
    }
    public String getDetailURL() {
        return detailURL;
    }

    public void setFirstPicURL(String firstPicURL) {
        this.firstPicURL = firstPicURL;
    }
    public String getFirstPicURL() {
        return firstPicURL;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
    public List<String> getTag() {
        return tag;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }
    public List<String> getPicUrlList() {
        return picUrlList;
    }
}
