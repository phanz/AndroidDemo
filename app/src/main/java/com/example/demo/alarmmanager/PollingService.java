package com.example.demo.alarmmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by hanzai.peng on 2016/11/7.
 */
public class PollingService extends Service{
    public static final String ACTION = "com.example.demo.alarmmanager.PollingService";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("Service Create!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service Start!");
        return super.onStartCommand(intent, flags, startId);

    }
}
