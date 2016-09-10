package com.ahao.androidlib.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Avalon on 2016/8/28.
 */
public class OSUtils {
    private static final String TAG = "OSUtils";

    private OSUtils() {
    }


    /**
     *  屏幕亮度相关
     */
    /**
     * 自动调节模式
     */
    public static final int SCREEN_BRIGHTNESS_MODE_AUTOMATIC = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    /**
     * 手动调节模式
     */
    public static final int SCREEN_BRIGHTNESS_MODE_MANUAL = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;

    /**
     * 获取当前系统亮度调节模式
     */
    public static boolean isAutoBrightMode(Activity activity) {
        ContentResolver cr = activity.getContentResolver();
        return Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) == SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }

    /**
     * 设置当前系统亮度调节模式
     */
    public static void setSystemBrightMode(Activity activity, boolean isAuto) {
        ContentResolver cr = activity.getContentResolver();
        if (!isAuto) {
            Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS_MODE, SCREEN_BRIGHTNESS_MODE_MANUAL);
        } else {
            Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS_MODE, SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        }
    }

    /**
     * 获取当前系统屏幕亮度[0,255]
     */
    public static int getSystemBrightness(Activity activity) {
        ContentResolver contentResolver = activity.getContentResolver();
        int defVal = 125;
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, defVal);
    }

    /**
     * 设置当前系统屏幕亮度
     */
    public static void setSystemBrightness(Activity activity, int brightness) {
        setSystemBrightMode(activity, false);
        ContentResolver cr = activity.getContentResolver();
        Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS, MathUtils.clamp(brightness, 0, 255));
    }

    /**
     * 设置当前窗口屏幕亮度[0,1]
     */
    public static void setWindowBrightness(Activity activity, float brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = MathUtils.clamp(brightness, 0.0f, 1.0f);
        window.setAttributes(lp);
    }


    /**
     *  屏幕方向相关
     */
    /**
     * 未指定,此为默认值,由Android系统自己选择适当的方向,选择策略视具体设备的配置情况而定,因此不同的设备会有不同的方向选择
     */
    public static final int UNSPECIFIED = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    /**
     * 横屏(风景照),显示时宽度大于高度
     */
    public static final int LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    /**
     * 竖屏(肖像照),显示时高度大于宽度
     */
    public static final int PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    /**
     * 用户当前的首选方向
     */
    public static final int USER = ActivityInfo.SCREEN_ORIENTATION_USER;
    /**
     * 继承Activity堆栈中当前Activity下面的那个Activity的方向
     */
    public static final int BEHIND = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
    /**
     * 由物理感应器决定显示方向,它取决于用户如何持有设备,当设备被旋转时方向会随之变化——在横屏与竖屏之间
     */
    public static final int SENSOR = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    /**
     * 忽略物理感应器,即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变("unspecified"设置除外)
     */
    public static final int NOSENSOR = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR;

    /**
     * 是否竖屏
     */
    public static boolean isPortraitScreen(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return height > width;
    }

    /**
     * 默认的横、竖屏切换, true为竖屏
     */
    public static boolean defaultSwitchOrientation(Activity activity) {
        if (isPortraitScreen(activity)) {
            setScreenOrientation(activity, LANDSCAPE);
            return false;
        } else {
            setScreenOrientation(activity, PORTRAIT);
            return true;
        }
    }

    /**
     * 设置屏幕Orientation属性
     */
    public static void setScreenOrientation(Activity activity, int orientation) {
        activity.setRequestedOrientation(orientation);
    }


    /**
     * 状态栏相关
     */
    public static int getAppBarHeight(Context context) {
        return ConvertUtils.dip2px(context, 56) + getStatusBarHeight(context);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
