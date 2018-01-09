package com.example.system.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hanzai.peng on 2016/11/7.
 */
public class AlertBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.example.datamanager.alarmmanager.receiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ACTION)){
            System.out.println("Receiver Alert Broadcast");
        }
    }
}
