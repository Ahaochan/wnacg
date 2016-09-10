package com.ahao.wnacg.util;

import com.ahao.wnacg.entity.ComicEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Avalon on 2016/5/11.
 */
public class ComicDataManager {
    private static ComicDataManager comicDataManager;
    private static Map<String, ComicEntity> comicMap;


    private ComicDataManager(){}

    public static ComicDataManager getInstance(){
        if(comicDataManager == null){
            comicDataManager = new ComicDataManager();
        }
        if(comicMap == null) {
            comicMap = new HashMap<String, ComicEntity>();
        }
        return comicDataManager;
    }

    public static ComicEntity getComicData(String key){
        if(comicMap.containsKey(key)){
            return comicMap.get(key);
        } else {
            return null;
        }
    }
    public static ComicEntity putComicData(String key, ComicEntity comicEntity){
        if(comicMap == null){
            return null;
        }
        return comicMap.put(key, comicEntity);
    }


}
