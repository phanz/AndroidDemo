package com.example.ui.fragment.communicate;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demo.R;
import com.example.ui.fragment.PresentFragment;
import com.example.ui.fragment.pager.BasePagerFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanzai.peng on 2017/3/21.
 */

public class ReplaceFragment extends BasePagerFragment {
    public static final String TAG = "ReplaceFragment";

    public static final int REQUEST_DIALOG = 0x11;
    public static final int REQUEST_ACTIVITY_FRAGMENT = 0x22;

    public static BasePagerFragment newInstance(String title) {
        BasePagerFragment fragment = new ReplaceFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if( getArguments() != null ){
            Bundle bundle = getArguments();
            String data = bundle.getString("DATA","undefined");
            Log.d(TAG,"Fragment从源Fragment获取数据：" + data);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_replace,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.back,R.id.fragment_dialog_btn,R.id.fragment_communicate_btn,R.id.fragment_stack_btn })
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.back:
                Toast.makeText(getActivity(),"返回",Toast.LENGTH_SHORT).show();
                /*getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_content,new PresentFragment())
                        .commit();*/

                getFragmentManager().popBackStack();
                break;

            case R.id.fragment_dialog_btn:
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.setTargetFragment(this,REQUEST_DIALOG);
                dialogFragment.show(getChildFragmentManager(),"MyDialogFragment");
                break;

            case R.id.fragment_communicate_btn:
                intent = new Intent(getActivity(),ResultFragmentActivity.class);
                startActivityForResult(intent,REQUEST_ACTIVITY_FRAGMENT);
                break;

            case R.id.fragment_stack_btn:
                intent = new Intent(getActivity(),StackFragmentActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_DIALOG){
            String dialogData = data.getStringExtra("DATA");
            Log.d(TAG,"从DialogFragment中获取到返回的内容：" + dialogData);

        }else if(requestCode == REQUEST_ACTIVITY_FRAGMENT){
            String fragmentData = data.getStringExtra("DATA");
            Log.d(TAG,"从目标Activity的Fragment中获取到返回的内容：" + fragmentData);
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }
}
