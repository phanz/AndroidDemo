package com.example.ui.fragment.pager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

public class OtherFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    public OtherFragment() {
        // Required empty public constructor
    }

    public static OtherFragment newInstance(String title) {
        OtherFragment fragment = new OtherFragment();
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
        return inflater.inflate(R.layout.fragment_common_widget, container, false);
    }
}
