package com.ahao.wnacg.common;

/**
 * Created by Avalon on 2016/5/10.
 */
public class Regular {
    public final static String HOME_MSG = "<h2  class=\"[\\s\\S]+?</span>"
            + "[\\s\\S]+?<a  href=\"([\\s\\S]+?html)\""
            + "[\\s\\S]+?<img  src=\"+([\\s\\S]+?)\"  class=\"comic_img\">"
            + "[\\s\\S]+?comic_title\">([\\s\\S]+?)</span>"
            + "[\\s\\S]+?comic_author\">([\\s\\S]+?)</span>"

            + "[\\s\\S]+?<a  href=\"([\\s\\S]+?html)\""
            + "[\\s\\S]+?<img  src=\"+([\\s\\S]+?)\"  class=\"comic_img\">"
            + "[\\s\\S]+?comic_title\">([\\s\\S]+?)</span>"
            + "[\\s\\S]+?comic_author\">([\\s\\S]+?)</span>"

            + "[\\s\\S]+?<a  href=\"([\\s\\S]+?html)\""
            + "[\\s\\S]+?<img  src=\"+([\\s\\S]+?)\"  class=\"comic_img\">"
            + "[\\s\\S]+?comic_title\">([\\s\\S]+?)</span>"
            + "[\\s\\S]+?comic_author\">([\\s\\S]+?)</span>";


    public final static String TAG = "<a class=\"tagshow\"[\\s\\S]+?\">([\\s\\S]+?)</a>";
    public final static String INTRODUCE = "<p>簡介：([\\s\\S]+?)</p>"
                                        + "[\\s\\S]+?<p>([\\s\\S]+?)</p>"
 				                        + "[\\s\\S]+?<div class=\"pic_box\"><a href=\"([\\s\\S]+?html)\">";
    public final static String COMICPIC = "<img src=\"(http://img.wnacg.us/data[\\s\\S]+?jpg)\"";
}
