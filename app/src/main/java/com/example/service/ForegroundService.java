package com.example.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ForegroundService extends Service {
    private static final String TAG = "ForegroundService";

    private static ForegroundService sInstance = null;
    public ForegroundService() {
    }

    @Override
    public void onCreate() {
        sInstance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context appContext = this.getApplicationContext();
        Intent innerIntent = new Intent(appContext,InnerService.class);
        appContext.startService(innerIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static ForegroundService getInstance(){
        return sInstance;
    }

    /**
     * 提优先级，思路是利用startForeground将优先级从 5 提到 2；
     * 并利用同一pushId，开启、停止内部Service消除需要关联的Notification，此时BluetoothService优先级将仍然为2(前台Service)
     * @param foreService
     * @param innerService
     */
    public void setForeground(Service foreService,Service innerService){
        Log.d(TAG,"尝试将ForegroundService优先级提值2");
        int foregroundPushId = 1;
        if(foreService != null){
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2){
                foreService.startForeground(foregroundPushId,new Notification());
            }else{
                foreService.startForeground(foregroundPushId,new Notification());
                if(innerService != null){
                    innerService.startForeground(foregroundPushId,new Notification());
                    innerService.stopSelf();
                }
            }
        }
    }

    public static class InnerService extends Service{

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            ForegroundService.getInstance().setForeground(sInstance,this);
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
