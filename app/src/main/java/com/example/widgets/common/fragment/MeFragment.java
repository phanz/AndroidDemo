package com.example.widgets.common.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo.R;

/**
 * Created by hanzai.peng on 2017/3/17.
 */

public class MeFragment extends Fragment {
    private static final String TAG = MeFragment.class.getSimpleName();

    private Button replaceBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,null);
        replaceBtn = (Button)view.findViewById(R.id.replace);
        replaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"replace",Toast.LENGTH_SHORT).show();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_content,new ReplaceFragment())
                        .commit();
            }
        });
        Bundle bundle = getArguments();
        if(bundle != null){   //接收从Activity传来的消息
            String data = bundle.getString("key");
            Log.d(TAG, "onActivityCreated: "+data);
        }
        return view;
    }

}
