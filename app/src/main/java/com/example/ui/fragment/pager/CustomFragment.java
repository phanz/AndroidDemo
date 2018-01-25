package com.example.ui.fragment.pager;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demo.R;
import com.example.ui.activity.PictureTextActivity;
import com.example.ui.activity.CustomViewActivity;
import com.example.ui.activity.HScrollViewActivity;
import com.example.ui.activity.SimpleViewActivity;
import com.example.ui.activity.SimpleGestureActivity;

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

    @OnClick({ R.id.picture_text_btn,R.id.custom_view_btn,R.id.h_scroll_view_btn,
            R.id.simple_view_btn, R.id.gesture_btn,R.id.touch_event_btn})
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.picture_text_btn:
                intent = new Intent(getActivity(), PictureTextActivity.class);
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

            case R.id.simple_view_btn:
                intent = new Intent(getActivity(), SimpleViewActivity.class);
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
