package com.ahao.wnacg.util;

import com.ahao.wnacg.entity.ComicData;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Avalon on 2016/5/11.
 */
public class ComicDataManager {
    private static ComicDataManager comicDataManager;
    private static Map<String, ComicData> comicMap;


    private ComicDataManager(){}

    public static ComicDataManager getInstance(){
        if(comicDataManager == null){
            comicDataManager = new ComicDataManager();
        }
        if(comicMap == null) {
            comicMap = new HashMap<String, ComicData>();
        }
        return comicDataManager;
    }

    public static ComicData getComicData(String key){
        if(comicMap.containsKey(key)){
            return comicMap.get(key);
        } else {
            return null;
        }
    }
    public static ComicData putComicData(String key, ComicData comicData){
        if(comicMap == null){
            return null;
        }
        return comicMap.put(key, comicData);
    }


}
