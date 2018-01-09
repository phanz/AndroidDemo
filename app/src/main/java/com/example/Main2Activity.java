package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.demo.R;
import com.example.component.service.aidl.client.BinderClientActivity;
import com.example.system.alarmmanager.AlarmManagerActivity;
import com.example.widgets.scroll.animation.AnimationActivity;
import com.example.widgets.picturewidget.video.BackgroundActivity;
import com.example.widgets.custom.canvas.CanvasActivity;
import com.example.widgets.custom.compoundbutton.CompoundButtonActivity;
import com.example.widgets.custom.customview.CustomViewActivity;
import com.example.widgets.common.CommonWidgetActivity;
import com.example.widgets.common.expandablelistview.ExpandableListViewActivity;
import com.example.widgets.common.fragment.FragmentActivity;
import com.example.widgets.scroll.gesture.SimpleGestureActivity;
import com.example.widgets.common.listview.ListViewActivity;
import com.example.system.notification.NotificationActivity;
import com.example.data.local.ormlite.OrmLiteActivity;
import com.example.security.permission.PermissionActivity;
import com.example.widgets.common.recyclerview.activity.ItemDecorationActivity;
import com.example.widgets.common.recyclerview.activity.RecyclerViewActivity;
import com.example.library.rxjava.RxJavaActivity;
import com.example.widgets.custom.simpleHScrollView.HScrollViewActivity;
import com.example.widgets.custom.simpleHScrollView2.HScrollView2Activity;
import com.example.widgets.material.SnackBarActivity;
import com.example.widgets.custom.surfaceview.SurfaceActivity;
import com.example.widgets.common.swiperefresh.SwipeRefreshActivity;
import com.example.widgets.picturewidget.video.SurfaceVideoActivity;
import com.example.widgets.picturewidget.video.VideoViewActivity;
import com.example.widgets.common.viewpager.ViewPagerActivity;
import com.example.widgets.custom.WidgetActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.aidl_btn,R.id.recycler_view_btn,R.id.item_decoration_btn,
            R.id.fragment_btn,R.id.gesture_btn,R.id.custom_view_btn,
            R.id.expandable_list_view_btn,R.id.widget_btn,R.id.rxjava_btn,
            R.id.permission_btn,R.id.snack_bar_btn,R.id.compound_view_btn,
            R.id.h_scroll_view_btn,R.id.h_scroll_view_btn_2,R.id.list_view_btn,
            R.id.alarm_manager_btn,R.id.orm_lite_btn,R.id.swipe_refresh_btn,
            R.id.popup_btn,R.id.dialog_btn,R.id.background_btn,
            R.id.surface_view_btn,R.id.canvas_btn,R.id.view_pager_btn,
            R.id.surface_video_btn,R.id.video_view_btn,R.id.animation_btn,
            R.id.notification_service_btn})
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.aidl_btn:
                Intent aidlIntent = new Intent(Main2Activity.this, BinderClientActivity.class);
                startActivity(aidlIntent);
                break;

            case R.id.recycler_view_btn:
                Intent recyclerViewIntent = new Intent(Main2Activity.this, RecyclerViewActivity.class);
                startActivity(recyclerViewIntent);
                break;

            case R.id.item_decoration_btn:
                Intent itemDecorationIntent = new Intent(Main2Activity.this, ItemDecorationActivity.class);
                startActivity(itemDecorationIntent);
                break;

            case R.id.fragment_btn:
                Intent fragmentIntent = new Intent(Main2Activity.this, FragmentActivity.class);
                startActivity(fragmentIntent);
                break;

            case R.id.gesture_btn:
                Intent gestureIntent = new Intent(Main2Activity.this, SimpleGestureActivity.class);
                startActivity(gestureIntent);
                break;

            case R.id.custom_view_btn:
                Intent customViewIntent = new Intent(Main2Activity.this, CustomViewActivity.class);
                startActivity(customViewIntent);
                break;

            case R.id.expandable_list_view_btn:
                Intent expandableListViewIntent = new Intent(Main2Activity.this, ExpandableListViewActivity.class);
                startActivity(expandableListViewIntent);
                break;

            case R.id.widget_btn:
                Intent widgetIntent = new Intent(Main2Activity.this, WidgetActivity.class);
                startActivity(widgetIntent);

                break;

            case R.id.rxjava_btn:
                Intent rxJavaIntent = new Intent(Main2Activity.this, RxJavaActivity.class);
                startActivity(rxJavaIntent);
                break;

            case R.id.permission_btn:
                Intent permissionIntent = new Intent(Main2Activity.this, PermissionActivity.class);
                startActivity(permissionIntent);
                break;

            case R.id.snack_bar_btn:
                Intent snackBarIntent = new Intent(Main2Activity.this, SnackBarActivity.class);
                startActivity(snackBarIntent);
                break;

            case R.id.compound_view_btn:
                Intent compoundIntent = new Intent(Main2Activity.this, CompoundButtonActivity.class);
                startActivity(compoundIntent);
                break;

            case R.id.h_scroll_view_btn:
                Intent hScrollViewIntent = new Intent(Main2Activity.this, HScrollViewActivity.class);
                startActivity(hScrollViewIntent);
                break;

            case R.id.h_scroll_view_btn_2:
                Intent hScrollViewIntent2 = new Intent(Main2Activity.this, HScrollView2Activity.class);
                startActivity(hScrollViewIntent2);
                break;

            case R.id.list_view_btn:
                Intent listViewIntent = new Intent(Main2Activity.this, ListViewActivity.class);
                startActivity(listViewIntent);
                break;

            case R.id.alarm_manager_btn:
                Intent alarmManagerIntent = new Intent(Main2Activity.this, AlarmManagerActivity.class);
                startActivity(alarmManagerIntent);
                break;

            case R.id.orm_lite_btn:
                Intent ormLiteIntent = new Intent(Main2Activity.this, OrmLiteActivity.class);
                startActivity(ormLiteIntent);
                break;

            case R.id.swipe_refresh_btn:
                Intent swipeRefreshIntent = new Intent(Main2Activity.this, SwipeRefreshActivity.class);
                startActivity(swipeRefreshIntent);
                break;

            case R.id.dialog_btn:
                Intent dialogIntent = new Intent(Main2Activity.this, CommonWidgetActivity.class);
                startActivity(dialogIntent);
                break;

            case R.id.background_btn:
                Intent backgroundIntent = new Intent(Main2Activity.this, BackgroundActivity.class);
                startActivity(backgroundIntent);
                break;

            case R.id.surface_view_btn:
                Intent surfaceIntent = new Intent(Main2Activity.this, SurfaceActivity.class);
                startActivity(surfaceIntent);
                break;

            case R.id.canvas_btn:
                Intent canvasIntent = new Intent(Main2Activity.this, CanvasActivity.class);
                startActivity(canvasIntent);
                break;

            case R.id.view_pager_btn:
                Intent viewPagerIntent = new Intent(Main2Activity.this, ViewPagerActivity.class);
                startActivity(viewPagerIntent);
                break;

            case R.id.surface_video_btn:
                Intent surfaceVideoIntent = new Intent(Main2Activity.this, SurfaceVideoActivity.class);
                startActivity(surfaceVideoIntent);
                break;

            case R.id.video_view_btn:
                Intent videoViewIntent = new Intent(Main2Activity.this, VideoViewActivity.class);
                startActivity(videoViewIntent);
                break;

            case R.id.animation_btn:
                Intent animationIntent = new Intent(Main2Activity.this, AnimationActivity.class);
                startActivity(animationIntent);
                break;

            case R.id.notification_service_btn:
                Intent notificationIntent = new Intent(Main2Activity.this, NotificationActivity.class);
                startActivity(notificationIntent);
                break;

            default:
                break;
        }
    }

}
