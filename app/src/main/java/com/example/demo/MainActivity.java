package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.aidl.client.BinderClientActivity;
import com.example.demo.recyclerview.activity.ItemDecorationActivity;
import com.example.demo.recyclerview.activity.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {

    private Button mAidlBtn;
    private Button mRecyclerBtn;
    private Button mItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAidlBtn = (Button)findViewById(R.id.aidl_btn);
        mAidlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aidlIntent = new Intent(MainActivity.this, BinderClientActivity.class);
                startActivity(aidlIntent);
            }
        });

        mRecyclerBtn = (Button)findViewById(R.id.recycler_view_btn);
        mRecyclerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recyclerViewIntent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(recyclerViewIntent);
            }
        });

        mItemDecoration = (Button)findViewById(R.id.item_decoration_btn);
        mItemDecoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDecorationIntent = new Intent(MainActivity.this, ItemDecorationActivity.class);
                startActivity(itemDecorationIntent);
            }
        });
    }
}
