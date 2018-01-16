package com.example.ui.widgets.custom.simpleHScrollView;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HScrollViewActivity extends AppCompatActivity {

    private MyHorizontalScrollView mMyHorizontalScrollView;
    private MyHorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;
    private List<Integer> mDatas = new ArrayList<>(Arrays.asList(
            R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,
            R.drawable.e,R.drawable.f,R.drawable.g
    ));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscroll_view);

        mImg = (ImageView)findViewById(R.id.id_content);
        mMyHorizontalScrollView = (MyHorizontalScrollView)findViewById(R.id.id_horizontalScrollView);
        mAdapter = new MyHorizontalScrollViewAdapter(this,mDatas);
        mMyHorizontalScrollView.setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
                mImg.setImageResource(mDatas.get(position));
                viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        mMyHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mImg.setImageResource(mDatas.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        mMyHorizontalScrollView.initDataSet(mAdapter);
    }
}
