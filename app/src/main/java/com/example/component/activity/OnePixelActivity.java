package com.example.component.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.demo.R;

public class OnePixelActivity extends Activity {

    public static final String TAG = "OnePixelActivity";

    public static OnePixelActivity sInstance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pixel);

        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
        sInstance = this;
        Log.d(TAG,"OnePixelActivity onCreate");
    }

    public static OnePixelActivity getInstance(){
        return sInstance;
    }

    public static void startOnePixelActivity(Context context){
        Context appContext = context.getApplicationContext();
        Intent onePixelIntent = new Intent(appContext,OnePixelActivity.class);
        onePixelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(onePixelIntent);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"OnePixelActivity onDestroy");
        super.onDestroy();
    }

    public static class KeepLiveReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_SCREEN_OFF)){
                OnePixelActivity.startOnePixelActivity(context);
            } else if(action.equals(Intent.ACTION_USER_PRESENT)){
                if(OnePixelActivity.getInstance() != null){
                    OnePixelActivity.getInstance().finish();
                }
            }
            //OnePixelActivity.startOnePixelActivity(context);
        }
    }

}
