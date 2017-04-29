package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.aidl.client.BinderClientActivity;
import com.example.demo.customview.CustomViewActivity;
import com.example.demo.expandablelistview.ExpandableListViewActivity;
import com.example.demo.fragment.FragmentActivity;
import com.example.demo.gesture.SimpleGestureActivity;
import com.example.demo.recyclerview.activity.ItemDecorationActivity;
import com.example.demo.recyclerview.activity.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mAidlBtn;
    private Button mRecyclerBtn;
    private Button mItemDecoration;
    private Button mFragmentBtn;
    private Button mGestureBtn;
    private Button mCustomViewBtn;
    private Button mExpandableListViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAidlBtn = (Button)findViewById(R.id.aidl_btn);
        mAidlBtn.setOnClickListener(this);

        mRecyclerBtn = (Button)findViewById(R.id.recycler_view_btn);
        mRecyclerBtn.setOnClickListener(this);

        mItemDecoration = (Button)findViewById(R.id.item_decoration_btn);
        mItemDecoration.setOnClickListener(this);

        mFragmentBtn = (Button)findViewById(R.id.fragment_btn);
        mFragmentBtn.setOnClickListener(this);

        mGestureBtn = (Button)findViewById(R.id.gesture_btn);
        mGestureBtn.setOnClickListener(this);

        mCustomViewBtn = (Button)findViewById(R.id.custom_view_btn);
        mCustomViewBtn.setOnClickListener(this);

        mExpandableListViewBtn = (Button)findViewById(R.id.expandable_list_view_btn);
        mExpandableListViewBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.aidl_btn:
                Intent aidlIntent = new Intent(MainActivity.this, BinderClientActivity.class);
                startActivity(aidlIntent);
                break;

            case R.id.recycler_view_btn:
                Intent recyclerViewIntent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(recyclerViewIntent);
                break;

            case R.id.item_decoration_btn:
                Intent itemDecorationIntent = new Intent(MainActivity.this, ItemDecorationActivity.class);
                startActivity(itemDecorationIntent);
                break;

            case R.id.fragment_btn:
                Intent fragmentIntent = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(fragmentIntent);
                break;

            case R.id.gesture_btn:
                Intent gestureIntent = new Intent(MainActivity.this, SimpleGestureActivity.class);
                startActivity(gestureIntent);
                break;

            case R.id.custom_view_btn:
                Intent customViewIntent = new Intent(MainActivity.this, CustomViewActivity.class);
                startActivity(customViewIntent);
                break;

            case R.id.expandable_list_view_btn:
                Intent expandableListViewIntent = new Intent(MainActivity.this, ExpandableListViewActivity.class);
                startActivity(expandableListViewIntent);
                break;
            default:
                break;
        }
    }
}
