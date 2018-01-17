package com.example.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.component.ComponentActivity;
import com.example.demo.R;
import com.example.ui.fragment.communicate.ReplaceFragment;
import com.example.ui.fragment.pager.BasePagerFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanzai.peng on 2017/3/17.
 */

public class PresentFragment extends Fragment {
    public static final String TAG = PresentFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        Bundle bundle = getArguments();
        if(bundle != null){   //接收从Activity传来的消息
            String data = bundle.getString("key");
            Log.d(TAG, "Fragment从源Activity中获取数据: "+data);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_show,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @OnClick({ R.id.component_btn,R.id.replace })
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.component_btn:
                intent = new Intent(getActivity(), ComponentActivity.class);
                startActivity(intent);
                break;

            case R.id.replace:
                Toast.makeText(getActivity(),"replace",Toast.LENGTH_SHORT).show();
                BasePagerFragment replaceFragment = new ReplaceFragment();
                Bundle bundle = new Bundle();
                bundle.putString("DATA","Hello");
                replaceFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_content,replaceFragment)
                        .commit();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}
