package com.example.demo.utils;

import android.content.Context;

/**
 * Created by phanz on 2017/5/5.
 */

public class Utils {

    public static int dipToPx(Context context,float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
