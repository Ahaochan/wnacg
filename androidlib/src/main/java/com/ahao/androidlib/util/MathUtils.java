package com.ahao.androidlib.util;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.util.Random;

/**
 * Created by Avalon on 2016/8/9.
 */
public class MathUtils {
    private MathUtils(){}
    private static final int BIGGER =  1;
    private static final int EQUAL  =  0;
    private static final int SMALL  = -1;



    /** 返回n个int数的最大数 */
    @CheckResult
    public static int max (@NonNull int... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        int max = args[0];
        for (int i = 1; i < length; i++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    /** 返回n个long数的最大数 */
    @CheckResult
    public static long max (@NonNull long... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        long max = args[0];
        for (int i = 1; i < length; i++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    /** 返回n个float数的最大数 */
    @CheckResult
    public static float max (@NonNull float... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        float max = args[0];
        for (int i = 1; i < length; i++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    /** 返回n个double数的最大数 */
    @CheckResult
    public static double max (@NonNull double... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        double max = args[0];
        for (int i = 1; i < length; i++) {
            max = Math.max(max, args[i]);
        }
        return max;
    }

    /** 返回n个int数的最小数 */
    @CheckResult
    public static int min (@NonNull int... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        int min = args[0];
        for (int i = 1; i < length; i++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    /** 返回n个long数的最小数 */
    @CheckResult
    public static long min (@NonNull long... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        long min = args[0];
        for (int i = 1; i < length; i++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    /** 返回n个float数的最小数 */
    @CheckResult
    public static float min (@NonNull float... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        float min = args[0];
        for (int i = 1; i < length; i++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    /** 返回n个double数的最小数 */
    @CheckResult
    public static double min (@NonNull double... args){
        int length = args.length;
        if(length<=0){
            throw new IllegalArgumentException("array must be not empty");
        }
        double min = args[0];
        for (int i = 1; i < length; i++) {
            min = Math.min(min, args[i]);
        }
        return min;
    }

    @CheckResult
    public static int dist(int x, int y) {
        return Math.abs(x-y);
    }



    /** 返回二维两点(x1,y1)(x2,y2)float距离 dist*/
    /** 返回二维两点(x1,y1)(x2,y2)double距离 dist*/
    /** 返回三维两点(x1,y1,z1)(x2,y2,z2)float距离 dist*/
    /** 返回三维两点(x1,y1,z1)(x2,y2,z2)double距离 dist*/
    /** 二维两点float距离小于slop */
    /** 二维两点double距离小于slop */
    /** 三维两点float距离小于slop */
    /** 三维两点double距离小于slop */
    /** 将角度转化为弧度 float radians*/
    /** 将弧度转化为度数 float degrees*/
    /** 返回x不超过[min,max]的值 clamp int long float double*/


    /** x大于y返回正数,x等于y返回0,x小于y返回负数 */
    public static int compare(int x, int y, boolean abs){
        int result;
        if(abs){
            if(Math.abs(x)>Math.abs(y))       result = BIGGER;
            else if(Math.abs(x)<Math.abs(y))  result = SMALL;
            else                              result = EQUAL;
        } else {
            if(x>y)       result = BIGGER;
            else if(x<y)  result = SMALL;
            else          result = EQUAL;
        }
        return result;
    }

    /** x大于y返回正数,x等于y返回0,x小于y返回负数 */
    public static long compare(long x, long y, boolean abs){
        int result;
        if(abs){
            if(Math.abs(x)>Math.abs(y))       result = BIGGER;
            else if(Math.abs(x)<Math.abs(y))  result = SMALL;
            else                              result = EQUAL;
        } else {
            if(x>y)       result = BIGGER;
            else if(x<y)  result = SMALL;
            else          result = EQUAL;
        }
        return result;
    }

    /** x大于y返回正数,x等于y返回0,x小于y返回负数 */
    public static float compare(float x, float y, boolean abs){
        int result;
        if(abs){
            if(Math.abs(x)>Math.abs(y))       result = BIGGER;
            else if(Math.abs(x)<Math.abs(y))  result = SMALL;
            else                              result = EQUAL;
        } else {
            if(x>y)       result = BIGGER;
            else if(x<y)  result = SMALL;
            else          result = EQUAL;
        }
        return result;
    }

    /** x大于y返回正数,x等于y返回0,x小于y返回负数 */
    public static double compare(double x, double y, boolean abs){
        int result;
        if(abs){
            if(Math.abs(x)>Math.abs(y))       result = BIGGER;
            else if(Math.abs(x)<Math.abs(y))  result = SMALL;
            else                              result = EQUAL;
        } else {
            if(x>y)       result = BIGGER;
            else if(x<y)  result = SMALL;
            else          result = EQUAL;
        }
        return result;
    }

    /**
     * 获得[min,max)之间的一个int类型的随机数
     * @param min 左区间
     * @param max 右区间
     * @return
     */
    public static int randomInt(int min, int max){
        if(min>max){
            throw new IllegalStateException("min must be bigger than max");
        }
        if(min==max){
            return min;
        }
        return new Random().nextInt(max)%(max-min)+min;
    }


    /** 返回 x 限定在 [min, max]之间 */
    @CheckResult
    public static int clamp(int x, int min, int max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    /** 返回 x 限定在 [min, max]之间 */
    @CheckResult
    public static long clamp(long x, long min, long max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    /** 返回 x 限定在 [min, max]之间 */
    @CheckResult
    public static float clamp(float x, float min, float max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    /** 返回 x 限定在 [min, max]之间 */
    @CheckResult
    public static double clamp(double x, double min, double max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }
}
