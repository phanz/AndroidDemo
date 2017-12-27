package com.example.demo;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hanzai.peng on 2017/11/2.
 */

public class DemoApp extends Application {
    public static final String TAG = "DemoApp";

    private static DemoApp sIntance = null;
    private Context mContext;
    private Handler mHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        sIntance = this;
        mHandler = new Handler();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        hookNotificationManager(base);
    }

    /*通过钩子拦截本APP的所有通知*/
    private void hookNotificationManager(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 得到系统的 sService
            Method getService = NotificationManager.class.getDeclaredMethod("getService");
            getService.setAccessible(true);
            final Object sService = getService.invoke(notificationManager);

            // 生成另一个INotificationManager类型的对象预备替换 sService
            Class iNotiMngClz = Class.forName("android.app.INotificationManager");
            Object proxyNotiMng = Proxy.newProxyInstance(sService.getClass().getClassLoader(),
                    new Class[]{iNotiMngClz},
                    new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d("DemoApp", "Method:" + method.getName());
                    if(method.getName().equals("enqueueNotificationWithTag")){
                        Notification notification = (Notification)args[4];
                        Bundle bundle = notification.extras;

                        String title = bundle.getString("android.title");
                        String content = bundle.getString("android.text");
                        Log.d(TAG,String.format("拦截到应用内通知，标题：%s, 内容：%s",title,content));
                    }
                    return method.invoke(sService, args);// 不拦截通知
                    //return null;// 拦截通知，什么也不做,或者是根据通知的 Tag 和 ID 进行筛选
                }
            });
            // 替换 sService
            Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyNotiMng);
        } catch (Exception e) {
            Log.w("DemoApp","Hook NotificationManager failed!");
        }
    }

    public static DemoApp getInstance(){
        return sIntance;
    }

    public Context getContext(){
        return mContext;
    }

    public void runOnMainThead(Runnable r){
        mHandler.post(r);
    }

    public void showToast(final String text){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DemoApp.this,text,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
