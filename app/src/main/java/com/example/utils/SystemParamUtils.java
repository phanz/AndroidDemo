package com.example.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by phanz on 2017/4/29.
 */

public class SystemParamUtils {
    public int screenWidth;// 屏幕宽度，单位为px
    public int screenHeight;// 屏幕高度，单位为px
    public int densityDpi;// 屏幕密度，单位为dpi
    public float scale;// 缩放系数，值为 densityDpi/160
    public float fontScale;// 文字缩放系数，同scale

    public final static int SCREEN_ORIENTATION_VERTICAL = 1; // 屏幕状态：横屏
    public final static int SCREEN_ORIENTATION_HORIZONTAL = 2; // 屏幕状态：竖屏
    public int screenOrientation = SCREEN_ORIENTATION_VERTICAL;// 当前屏幕状态，默认为竖屏

    public SystemParamUtils(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        densityDpi = dm.densityDpi;
        scale = dm.density;
        fontScale = dm.scaledDensity;

        screenOrientation = screenHeight > screenWidth ? SCREEN_ORIENTATION_VERTICAL
                : SCREEN_ORIENTATION_HORIZONTAL;
    }
}
