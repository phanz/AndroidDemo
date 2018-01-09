package com.example.system.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        Intent intent = new Intent(this,NotificationCollectorMonitorService.class);
        startService(intent);
    }

    @OnClick({R.id.notification_btn,R.id.send_notification_btn,R.id.crash_btn})
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.notification_btn:
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                break;
            case R.id.send_notification_btn:
                sendTestNotification();
                break;
            case R.id.crash_btn:
                Object o = null;
                o.hashCode();
                break;
        }
    }

    public void sendTestNotification(){
        /*NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.tickerText = "收到一个新通知";
        notificationManager.notify(1,notification);*/


        NotificationManager notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String title = "收到一个新通知";
        int NT_ID = R.string.app_name;
        Notification.Builder builder = new Notification.Builder(this);
        builder.setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(title)
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentText("我是内容");

        notificationManager.notify(NT_ID, builder.build());
    }
}
