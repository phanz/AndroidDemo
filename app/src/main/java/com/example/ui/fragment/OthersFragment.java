package com.example.ui.fragment;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo.R;
import com.example.ui.fragment.pager.BasePagerFragment;
import com.example.ui.fragment.pager.CommonWidgetFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanzai.peng on 2017/3/17.
 */

public class OthersFragment extends BasePagerFragment {
    private static final String TAG = "OthersFragment";

    public static BasePagerFragment newInstance(String title) {
        BasePagerFragment fragment = new OthersFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others,null);
        ButterKnife.bind(this,view);
        return view;
    }

}
