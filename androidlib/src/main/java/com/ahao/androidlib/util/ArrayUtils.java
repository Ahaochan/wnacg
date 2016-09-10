package com.ahao.androidlib.util;

import android.util.Log;

/**
 * Created by Avalon on 2016/8/11.
 */
public class ArrayUtils {
    private static final String TAG = "ArrayUtils";
    private ArrayUtils(){}

    /** 打印数组 */
    public static void log(int[] arr, int col){
        if(arr==null || arr.length<=0){
            throw new ArrayIndexOutOfBoundsException("arr must be have elements");
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.length; i++){
            sb.append(arr[i]).append(" ");
            if(i%col==0){
                sb.append("\n");
            }
        }
        Log.i(TAG, sb.toString());
    }

    /** array在(start, array.length)中最后一个非other的数的index位置 */
    public static int lastOutOfNum(int[]arr, int start, int other){
        if(arr==null || arr.length<=0){
            throw new ArrayIndexOutOfBoundsException("arr must be have elements");
        }
        if(start>=arr.length){
            throw new ArrayIndexOutOfBoundsException("pos must be smaller than arr.length");
        }
        int index = -1;
        for(int i = start+1; i<arr.length; i++){
            if(arr[i]!=other){
                index =  i;
            }
        }
        return index;
    }
}
