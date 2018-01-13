package com.example.task;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by phanz on 2018/1/13.
 */

public class MyIntentService extends IntentService {
    public static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
        Log.d(TAG, "MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        SystemClock.sleep(2000);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
