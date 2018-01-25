package com.example.service.aidl.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.demo.IBinderPool;
import com.example.service.aidl.service.BinderPoolService;

import java.util.concurrent.CountDownLatch;

/**
 * Created by phanz on 2017/4/27.
 */

public class BinderClient {

    private static BinderClient sInstance = null;
    private static Context mContext;
    private IBinderPool mBinderPool;
    private CountDownLatch mCountDownLatch;

    private BinderClient(Context context){
        mContext = context;
        connectBinderPoolService();
    }

    public static BinderClient getInstance(Context context){
        if(sInstance == null){
            synchronized (BinderClient.class){
                if(sInstance == null){
                    sInstance = new BinderClient(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService(){
        mCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext,BinderPoolService.class);
        mContext.bindService(intent,mConn,Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(iBinder);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {

        }
    };

    public IBinder queryBinder(int type){
        IBinder binder = null;
        try {
            binder = mBinderPool.query(type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }
}
