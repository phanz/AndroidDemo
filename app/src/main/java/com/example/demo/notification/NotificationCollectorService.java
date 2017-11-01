package com.example.demo.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.example.demo.DemoApp;

/**
 * Created by phanz on 2017/11/1.
 */

public class NotificationCollectorService extends NotificationListenerService {
    public static final String TAG = "Notification";

    private static HandlerThread handlerThread;
    private static Handler mHandler;

    static {
        handlerThread = new HandlerThread("ToastThread");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        DemoApp.getInstance().showToast("拦截到通知：" + notificationText);
        Log.d(TAG, "Notification posted " + notificationTitle + " & " + notificationText);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        DemoApp.getInstance().showToast("拦截到通知删除：" + notificationText);
        Log.i(TAG, "Notification removed " + notificationTitle + " & " + notificationText);

    }


}
