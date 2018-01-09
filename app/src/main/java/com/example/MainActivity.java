package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.component.StudyActivity;
import com.example.data.local.ormlite.OrmLiteActivity;
import com.example.demo.R;
import com.example.system.alarmmanager.AlarmManagerActivity;
import com.example.system.notification.NotificationActivity;
import com.example.widgets.common.CommonWidgetActivity;
import com.example.widgets.custom.WidgetActivity;
import com.example.widgets.picturewidget.video.BackgroundActivity;
import com.example.widgets.picturewidget.video.SurfaceVideoActivity;
import com.example.widgets.picturewidget.video.VideoViewActivity;
import com.example.widgets.scroll.ScrollActivity;
import com.example.widgets.scroll.animation.AnimationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.activity_btn,R.id.service_btn,R.id.provider_btn,R.id.broadcast_btn })
    public void onComponentClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.activity_btn:
                intent = new Intent(this,StudyActivity.class);
                startActivity(intent);
                break;
            case R.id.service_btn:

                break;
            case R.id.provider_btn:

                break;
            case R.id.broadcast_btn:

                break;
            default:
                break;
        }

    }

    @OnClick({ R.id.common_widget_btn,R.id.material_design_btn})
    public void onBuildInWidgetClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.common_widget_btn:
                intent = new Intent(this, CommonWidgetActivity.class);
                startActivity(intent);
                break;
            case R.id.material_design_btn:

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

    @OnClick({ R.id.bitmap_btn,R.id.sound_btn,R.id.video_btn })
    public void onPictureWidgetClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
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

    @OnClick({ R.id.http_btn,R.id.database_btn,R.id.thread_btn,R.id.schedule_btn })
    public void onDataManagerClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.http_btn:

                break;
            case R.id.database_btn:
                intent = new Intent(this, OrmLiteActivity.class);
                startActivity(intent);
                break;
            case R.id.thread_btn:

                break;
            case R.id.schedule_btn:
                intent = new Intent(this, AlarmManagerActivity.class);
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

                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.qr_code_btn,R.id.map_btn,R.id.chart_btn })
    public void onOpenLibraryClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.qr_code_btn:

                break;
            case R.id.map_btn:

                break;
            case R.id.chart_btn:

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
