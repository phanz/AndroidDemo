package com.example.widgets.common.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

/**
 * Created by hanzai.peng on 2017/3/17.
 */

public class HealthFragment extends Fragment {
    private static final String TAG = HealthFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health,null);
    }

}
