package com.example.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ui.activity.LifeCycleActivity;
import com.example.ui.activity.NightModeActivity;
import com.example.ui.activity.OnePixelActivity;
import com.example.demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComponentActivity extends AppCompatActivity {
    public static final String TAG = "ComponentActivity";

    private ScreenBroadcastReceiver mScreenReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
        ButterKnife.bind(this);

        mScreenReceiver = new ScreenBroadcastReceiver();
        startScreenBroadcastReceiver();
    }

    @OnClick({ R.id.activity_cycle_btn,R.id.one_pixel_activity_btn,R.id.night_mode_btn })
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.activity_cycle_btn:
                intent = new Intent(this,LifeCycleActivity.class);
                startActivity(intent);
                break;

            case R.id.one_pixel_activity_btn:
                intent = new Intent(this,OnePixelActivity.class);
                startActivity(intent);
                break;

            case R.id.night_mode_btn:
                intent = new Intent(this,NightModeActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        stopScreenStateUpdate();
        super.onDestroy();
    }

    /**
     * 停止screen状态更新
     */
    public void stopScreenStateUpdate() {
        this.unregisterReceiver(mScreenReceiver);
    }

    /**
     * 启动screen状态广播接收器
     */
    private void startScreenBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        this.registerReceiver(mScreenReceiver, filter);
    }

    /**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
                Log.d(TAG,"SCREEN_ON");
            }else if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                Log.d(TAG,"ACTION_SCREEN_OFF");
                //OnePixelActivity.startOnePixelActivity(context);
            }else if(Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
                Log.d(TAG,"ACTION_USER_PRESENT");
                if(OnePixelActivity.getInstance() != null){
                    //OnePixelActivity.getInstance().finish();
                }
            }
        }
    }
}
