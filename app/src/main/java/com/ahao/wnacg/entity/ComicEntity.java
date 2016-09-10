package com.ahao.wnacg.entity;

import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Avalon on 2016/5/5.
 * 漫画数据
 */
public class ComicEntity implements Serializable{
    @Id
    private String aId;//漫画的id,如:28083
    private String title;//漫画标题,如:[いちこ] ビンカン中毒 (ガールズフォーム Vol.8)[沒有漢化]
    private String timeAndPic;//漫画创建时间和照片张数,如:2016-05-10, 20張照片
    private String createTime;//创建时间,如:2016-05-10
    private Integer picNum;//漫画张数,如:20
    private String thumbUrl;//缩略图url,如:http://img.wnacg.us/data/t/0280/83/14628388668089.jpg
    private String detailUrl;//详情url,如:http://m.wnacg.org/photos-index-aid-28083.html

    private String updater;//上传者
    private String introduce;//简介
    private List<String> tag;//tag的list
    private List<String> thumbUrlList;//第一页的thumb预览

    private List<String> picUrlList;//漫画的piclList

    private Integer currentPic;

    @Override
    public String toString() {
        return "漫画id:"+aId+"\n漫画标题:"+title+"\n漫画创建时间和张数:"+timeAndPic+
                "\n创建时间:"+createTime+"\n漫画张数:"+picNum+ "\n缩略图url:"+thumbUrl+
                "\n详情url:"+detailUrl+"\n上传者:"+updater+"\n简介:"+introduce+
                "\ntag:"+tag+"\n漫画的picList:"+picUrlList;
    }

    public void setAId(String aId) {
        this.aId = aId;
    }
    public String getAId() {
        return aId;
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
    public Integer getPicNum() {
        return picNum==null?0:picNum;
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

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setDetailUrl(String indexUrl) {
        this.detailUrl = indexUrl;
    }
    public String getDetailUrl() {
        return detailUrl;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
    public List<String> getTag() {
        return tag;
    }

    public void setPicList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }
    public List<String> getPicList() {
        return picUrlList;
    }

    public void setThumbUrlList(List<String> thumbUrlList) {
        this.thumbUrlList = thumbUrlList;
    }
    public List<String> getThumbUrlList() {
        return thumbUrlList;
    }


    public Integer getCurrentPic() {
        return currentPic==null?0:currentPic;
    }

    public void setCurrentPic(Integer currentPic) {
        this.currentPic = currentPic;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        sb.append("漫画标题:").append(title).append("\n");
        sb.append("漫画页数:").append(picNum).append("页\n");
        sb.append("上传时间:").append(createTime).append("\n");
        sb.append("上传者:").append(updater).append("\n");
        return sb.toString();
    }
}
