package com.example.component.service.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.component.service.aidl.binder.BinderPoolImpl;

public class BinderPoolService extends Service {

    IBinder binder = new BinderPoolImpl();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
