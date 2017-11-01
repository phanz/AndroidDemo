package com.example.demo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by hanzai.peng on 2017/11/2.
 */

public class DemoApp extends Application {

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
