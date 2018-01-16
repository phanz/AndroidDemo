package com.example.ui.fragment.pager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

public class DrawableFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    public DrawableFragment() {
        // Required empty public constructor
    }

    public static DrawableFragment newInstance(String title) {
        DrawableFragment fragment = new DrawableFragment();
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
        return inflater.inflate(R.layout.activity_background, container, false);
    }
}
