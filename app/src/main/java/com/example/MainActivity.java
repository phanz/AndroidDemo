package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.component.ComponentActivity;
import com.example.data.NetDataActivity;
import com.example.demo.R;
import com.example.library.chart.ChartActivity;
import com.example.library.map.MapActivity;
import com.example.library.qrcode.CaptureActivity;
import com.example.library.qrcode.QrCodeActivity;
import com.example.library.rxjava.RxJavaActivity;
import com.example.system.DownloadTask;
import com.example.task.TaskActivity;
import com.example.system.notification.NotificationActivity;
import com.example.widgets.common.CommonWidgetActivity;
import com.example.widgets.custom.WidgetActivity;
import com.example.widgets.picturewidget.video.BackgroundActivity;
import com.example.widgets.picturewidget.video.SurfaceVideoActivity;
import com.example.widgets.picturewidget.video.VideoViewActivity;
import com.example.widgets.scroll.ScrollActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.activity_btn,R.id.common_widget_btn,R.id.material_design_btn,R.id.bitmap_btn,
            R.id.sound_btn,R.id.video_btn})
    public void onBuildInWidgetClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.activity_btn:
                intent = new Intent(this,ComponentActivity.class);
                startActivity(intent);
                break;
            case R.id.common_widget_btn:
                intent = new Intent(this, CommonWidgetActivity.class);
                startActivity(intent);
                break;
            case R.id.material_design_btn:

                break;
            case R.id.bitmap_btn:
                intent = new Intent(this, BackgroundActivity.class);
                startActivity(intent);
                break;
            case R.id.sound_btn:
                intent = new Intent(this, VideoViewActivity.class);
                startActivity(intent);
                break;
            case R.id.video_btn:
                intent = new Intent(this, SurfaceVideoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.scroll_animation_btn,R.id.custom_view_btn})
    public void onCustomWidgetClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.scroll_animation_btn:
                intent = new Intent(this, ScrollActivity.class);
                startActivity(intent);
                break;
            case R.id.custom_view_btn:
                intent = new Intent(this, WidgetActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.net_data_btn,R.id.task_btn })
    public void onDataManagerClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.net_data_btn:
                intent = new Intent(this, NetDataActivity.class);
                startActivity(intent);
                break;
            case R.id.task_btn:
                intent = new Intent(this, TaskActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @OnClick({ R.id.notification_btn,R.id.download_btn })
    public void onSystemServiceClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.notification_btn:
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;

            case R.id.download_btn:
                String url = "http://ucdl.25pp.com/fs08/2017/01/20/2/2_87a290b5f041a8b512f0bc51595f839a.apk";
                DownloadTask.downloadTest(this,url);
                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.qr_code_btn,R.id.map_btn,R.id.chart_btn,R.id.rx_java_btn })
    public void onOpenLibraryClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.qr_code_btn:
                intent = new Intent(this, QrCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.map_btn:
                intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.chart_btn:
                intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
                break;
            case R.id.rx_java_btn:
                intent = new Intent(this, RxJavaActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.permission_btn,R.id.encode_btn,R.id.wake_lock_btn })
    public void onSecurityClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.permission_btn:

                break;
            case R.id.encode_btn:

                break;
            case R.id.wake_lock_btn:

                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.dynamic_load_btn,R.id.ndk_btn,R.id.regex_btn })
    public void onOthersClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.dynamic_load_btn:

                break;
            case R.id.ndk_btn:

                break;
            case R.id.regex_btn:

                break;
            default:
                break;
        }
    }
}
