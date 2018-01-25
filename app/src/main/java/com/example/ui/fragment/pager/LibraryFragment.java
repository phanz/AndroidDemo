package com.example.ui.fragment.pager;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.ui.activity.ChartActivity;
import com.example.ui.activity.MapActivity;
import com.example.qrcode.QrCodeActivity;
import com.example.ui.activity.RxJavaActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LibraryFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance(String title) {
        LibraryFragment fragment = new LibraryFragment();
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
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.qr_code_btn,R.id.map_btn,R.id.chart_btn,R.id.rx_java_btn })
    public void onOpenLibraryClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.qr_code_btn:
                intent = new Intent(getActivity(), QrCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.map_btn:
                intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
                break;
            case R.id.chart_btn:
                intent = new Intent(getActivity(), ChartActivity.class);
                startActivity(intent);
                break;
            case R.id.rx_java_btn:
                intent = new Intent(getActivity(), RxJavaActivity.class);
                startActivity(intent);
                break;

            case R.id.glide_btn:
                //intent = new Intent(getActivity(), .class);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }
}
