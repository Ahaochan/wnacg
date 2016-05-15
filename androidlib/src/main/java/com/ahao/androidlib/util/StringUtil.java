package com.ahao.androidlib.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avalon on 2016/5/6.
 */
public class StringUtil {

    /**
     *
     * @param str       进行操作的字符串
     * @param position  第几块数字字符串,0为返回全部数字字符串
     * @return          返回第几块数字字符串,0为返回全部数字字符串
     */
    public static String getNumOfString(String str, int position){
        List<String> numList = new ArrayList<String>();
        StringBuffer AllNum = new StringBuffer();
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);

            if(ch>='0' && ch<='9'){
                AllNum.append(ch);
                buffer.append(ch);
            } else {
                if(!buffer.toString().equals("")) {
                    numList.add(buffer.toString());
                }
                buffer.delete(0, buffer.length());
            }
        }
        numList.add(buffer.toString());
        if(position == 0) {
            return AllNum.toString();
        } else {
            return numList.get(position-1);
        }
    }

    /**
     *
     * @param str       进行操作的字符串
     * @param position  第几块非数字字符串,0为返回全部数字字符串
     * @return          返回第几块非数字字符串,0为返回全部数字字符串
     */
    @NonNull
    public static String getNotNumOfString(String str, int position){
        List<String> numList = new ArrayList<String>();
        StringBuffer AllNum = new StringBuffer();
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);

            if(ch>='0' && ch<='9'){
                if(!buffer.toString().equals("")) {
                    numList.add(buffer.toString());
                }
                buffer.delete(0, buffer.length());
            } else {
                AllNum.append(ch);
                buffer.append(ch);
            }
        }
        numList.add(buffer.toString());
        if(position == 0) {
            return AllNum.toString();
        } else {
            return numList.get(position-1);
        }
    }


    /**
     *
     * @param str       进行操作的字符串
     * @param splitChar 以此对str进行分割
     * @param position  获取返回的部分
     * @return
     */
    public static String getChildAtSplit(String str, String splitChar, int position){
        String[] strs = str.split(splitChar);
        return strs[position];
    }

    /**
     *
     * @param str       进行操作的字符串
     * @param splitChar 以此对str进行分割
     * @param pre       获取pre之前的部分,不包括pre
     * @return
     */
    public static String getPreChildsAtSplit(String str, String splitChar, int pre){
        String[] strs = str.split(splitChar);
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < pre; i++){
            buffer.append(strs[i]+splitChar);
        }
        return buffer.toString();
    }
    /**
     *
     * @param str       进行操作的字符串
     * @param splitChar 以此对str进行分割
     * @param next       获取pre之后的部分,不包括next
     * @return
     */
    public static String getNextChildsAtSplit(String str, String splitChar, int next){
        String[] strs = str.split(splitChar);
        StringBuffer buffer = new StringBuffer();
        for(int i = next; i < strs.length; i++){
            if(i<strs.length-1){
                buffer.append(splitChar);
            }
            buffer.append(strs[i]);
        }
        return buffer.toString();
    }

    /**
     *
     * @param str       进行操作的字符串
     * @param position  想要获得数字字符串长度的位置
     * @return          返回第position块数字字符串的长度
     */
    public static int getNumLengthOfString(String str, int position){
        return getNumOfString(str,position).length();
    }
}
