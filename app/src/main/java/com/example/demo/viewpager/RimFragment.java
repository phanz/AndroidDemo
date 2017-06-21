package com.example.demo.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.R;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class RimFragment  extends Fragment {
    private Activity mActivity;
    public static String FORM="RimFragment";
    private String actName;
    private String type;
    private TextView tv;

    public void setMsgName(String type,String actName) {
        this.type = type;
        this.actName = actName;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rim, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv=(TextView)view.findViewById(R.id.tv);
        tv.setText(type);
    }

}
