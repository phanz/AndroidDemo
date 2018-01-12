package com.example;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by hanzai.peng on 2017/10/17.
 */

public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks{
    public static final String TAG = "ActivityLifecycle";

    private static int activityCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
        if(activityCount == 1){ //从0到1，app刚打开
            onForeground();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        if(activityCount == 0){ //从1到0，退出或进入后台
            onBackground();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public static boolean isAppInForeground(){
        return activityCount > 0;
    }

    public void onForeground(){
        Log.d(TAG,"App切换到前台");
    }

    public void onBackground(){
        Log.d(TAG,"App切换到后台");
    }
}
