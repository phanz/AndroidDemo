package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.demo.aidl.client.BinderClientActivity;
import com.example.demo.alarmmanager.AlarmManagerActivity;
import com.example.demo.animation.AnimationActivity;
import com.example.demo.background.BackgroundActivity;
import com.example.demo.canvas.CanvasActivity;
import com.example.demo.compoundbutton.CompoundButtonActivity;
import com.example.demo.customview.CustomViewActivity;
import com.example.demo.dialog.AlertDialogActivity;
import com.example.demo.expandablelistview.ExpandableListViewActivity;
import com.example.demo.fragment.FragmentActivity;
import com.example.demo.gesture.SimpleGestureActivity;
import com.example.demo.listview.ListViewActivity;
import com.example.demo.ormlite.OrmLiteActivity;
import com.example.demo.permission.PermissionActivity;
import com.example.demo.popup.PopupActivity;
import com.example.demo.recyclerview.activity.ItemDecorationActivity;
import com.example.demo.recyclerview.activity.RecyclerViewActivity;
import com.example.demo.rxjava.RxJavaActivity;
import com.example.demo.simpleHScrollView.HScrollViewActivity;
import com.example.demo.simpleHScrollView2.HScrollView2Activity;
import com.example.demo.snackbar.SnackBarActivity;
import com.example.demo.surfaceview.SurfaceActivity;
import com.example.demo.swiperefresh.SwipeRefreshActivity;
import com.example.demo.video.SurfaceVideoActivity;
import com.example.demo.video.VideoViewActivity;
import com.example.demo.viewpager.ViewPagerActivity;
import com.example.demo.widget.WidgetActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
            R.id.surface_video_btn,R.id.video_view_btn,R.id.animation_btn})
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.aidl_btn:
                Intent aidlIntent = new Intent(MainActivity.this, BinderClientActivity.class);
                startActivity(aidlIntent);
                break;

            case R.id.recycler_view_btn:
                Intent recyclerViewIntent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(recyclerViewIntent);
                break;

            case R.id.item_decoration_btn:
                Intent itemDecorationIntent = new Intent(MainActivity.this, ItemDecorationActivity.class);
                startActivity(itemDecorationIntent);
                break;

            case R.id.fragment_btn:
                Intent fragmentIntent = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(fragmentIntent);
                break;

            case R.id.gesture_btn:
                Intent gestureIntent = new Intent(MainActivity.this, SimpleGestureActivity.class);
                startActivity(gestureIntent);
                break;

            case R.id.custom_view_btn:
                Intent customViewIntent = new Intent(MainActivity.this, CustomViewActivity.class);
                startActivity(customViewIntent);
                break;

            case R.id.expandable_list_view_btn:
                Intent expandableListViewIntent = new Intent(MainActivity.this, ExpandableListViewActivity.class);
                startActivity(expandableListViewIntent);
                break;

            case R.id.widget_btn:
                Intent widgetIntent = new Intent(MainActivity.this, WidgetActivity.class);
                startActivity(widgetIntent);

                break;

            case R.id.rxjava_btn:
                Intent rxJavaIntent = new Intent(MainActivity.this, RxJavaActivity.class);
                startActivity(rxJavaIntent);
                break;

            case R.id.permission_btn:
                Intent permissionIntent = new Intent(MainActivity.this, PermissionActivity.class);
                startActivity(permissionIntent);
                break;

            case R.id.snack_bar_btn:
                Intent snackBarIntent = new Intent(MainActivity.this, SnackBarActivity.class);
                startActivity(snackBarIntent);
                break;

            case R.id.compound_view_btn:
                Intent compoundIntent = new Intent(MainActivity.this, CompoundButtonActivity.class);
                startActivity(compoundIntent);
                break;

            case R.id.h_scroll_view_btn:
                Intent hScrollViewIntent = new Intent(MainActivity.this, HScrollViewActivity.class);
                startActivity(hScrollViewIntent);
                break;

            case R.id.h_scroll_view_btn_2:
                Intent hScrollViewIntent2 = new Intent(MainActivity.this, HScrollView2Activity.class);
                startActivity(hScrollViewIntent2);
                break;

            case R.id.list_view_btn:
                Intent listViewIntent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(listViewIntent);
                break;

            case R.id.alarm_manager_btn:
                Intent alarmManagerIntent = new Intent(MainActivity.this, AlarmManagerActivity.class);
                startActivity(alarmManagerIntent);
                break;

            case R.id.orm_lite_btn:
                Intent ormLiteIntent = new Intent(MainActivity.this, OrmLiteActivity.class);
                startActivity(ormLiteIntent);
                break;

            case R.id.swipe_refresh_btn:
                Intent swipeRefreshIntent = new Intent(MainActivity.this, SwipeRefreshActivity.class);
                startActivity(swipeRefreshIntent);
                break;

            case R.id.popup_btn:
                Intent popupIntent = new Intent(MainActivity.this, PopupActivity.class);
                startActivity(popupIntent);
                break;

            case R.id.dialog_btn:
                Intent dialogIntent = new Intent(MainActivity.this, AlertDialogActivity.class);
                startActivity(dialogIntent);
                break;

            case R.id.background_btn:
                Intent backgroundIntent = new Intent(MainActivity.this, BackgroundActivity.class);
                startActivity(backgroundIntent);
                break;

            case R.id.surface_view_btn:
                Intent surfaceIntent = new Intent(MainActivity.this, SurfaceActivity.class);
                startActivity(surfaceIntent);
                break;

            case R.id.canvas_btn:
                Intent canvasIntent = new Intent(MainActivity.this, CanvasActivity.class);
                startActivity(canvasIntent);
                break;

            case R.id.view_pager_btn:
                Intent viewPagerIntent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(viewPagerIntent);
                break;

            case R.id.surface_video_btn:
                Intent surfaceVideoIntent = new Intent(MainActivity.this, SurfaceVideoActivity.class);
                startActivity(surfaceVideoIntent);
                break;

            case R.id.video_view_btn:
                Intent videoViewIntent = new Intent(MainActivity.this, VideoViewActivity.class);
                startActivity(videoViewIntent);
                break;

            case R.id.animation_btn:
                Intent animationIntent = new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(animationIntent);
                break;

            default:
                break;
        }
    }

}
