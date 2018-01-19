package com.example.ui.fragment.pager;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demo.R;
import com.example.ui.widgets.custom.PictureTextActivity;
import com.example.ui.widgets.custom.canvas.CanvasActivity;
import com.example.ui.widgets.custom.compoundbutton.CompoundButtonActivity;
import com.example.ui.widgets.custom.customview.CustomViewActivity;
import com.example.ui.widgets.custom.simpleHScrollView.HScrollViewActivity;
import com.example.ui.widgets.custom.simpleHScrollView2.HScrollView2Activity;
import com.example.ui.widgets.custom.surfaceview.SurfaceActivity;
import com.example.ui.widgets.custom.animation.AnimationActivity;
import com.example.ui.widgets.custom.gesture.SimpleGestureActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    public static CustomFragment newInstance(String title) {
        CustomFragment fragment = new CustomFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widget, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.picture_text_btn,R.id.canvas_btn,R.id.compound_btn,R.id.custom_view_btn,
            R.id.h_scroll_view_btn,R.id.h_scroll_view2_btn,R.id.surface_view_btn,
            R.id.animation_btn,R.id.gesture_btn,R.id.touch_event_btn})
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.picture_text_btn:
                intent = new Intent(getActivity(), PictureTextActivity.class);
                startActivity(intent);
                break;

            case R.id.canvas_btn:
                intent = new Intent(getActivity(), CanvasActivity.class);
                startActivity(intent);
                break;

            case R.id.compound_btn:
                intent = new Intent(getActivity(), CompoundButtonActivity.class);
                startActivity(intent);
                break;

            case R.id.custom_view_btn:
                intent = new Intent(getActivity(), CustomViewActivity.class);
                startActivity(intent);
                break;

            case R.id.h_scroll_view_btn:
                intent = new Intent(getActivity(), HScrollViewActivity.class);
                startActivity(intent);
                break;

            case R.id.h_scroll_view2_btn:
                intent = new Intent(getActivity(), HScrollView2Activity.class);
                startActivity(intent);
                break;

            case R.id.surface_view_btn:
                intent = new Intent(getActivity(), SurfaceActivity.class);
                startActivity(intent);
                break;

            case R.id.animation_btn:
                intent = new Intent(getActivity(), AnimationActivity.class);
                startActivity(intent);
                break;

            case R.id.gesture_btn:
                intent = new Intent(getActivity(), SimpleGestureActivity.class);
                startActivity(intent);
                break;

            case R.id.touch_event_btn:
                Toast.makeText(getActivity(),"TouchEvent尚未实现",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

}
