package com.example.ui.fragment.pager;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.system.DownloadTask;
import com.example.system.notification.NotificationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    public SystemFragment() {
        // Required empty public constructor
    }

    public static SystemFragment newInstance(String title) {
        SystemFragment fragment = new SystemFragment();
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
        View view = inflater.inflate(R.layout.fragment_system, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.notification_system_btn,R.id.download_system_btn })
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.notification_system_btn:
                intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.download_system_btn:
                String url = "http://ucdl.25pp.com/fs08/2017/01/20/2/2_87a290b5f041a8b512f0bc51595f839a.apk";
                DownloadTask.downloadTest(getActivity(),url);
                break;
            default:
                break;
        }
    }
}
