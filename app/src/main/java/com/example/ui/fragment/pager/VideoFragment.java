package com.example.ui.fragment.pager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

public class VideoFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance(String title) {
        VideoFragment fragment = new VideoFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_video_view, container, false);
    }
}
