package com.example.demo.swiperefresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.demo.R;

public class SwipeRefreshActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.activity_main);
        tv = (TextView)findViewById(R.id.textView1);
        //设置刷新时的动画颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv.setText("正在刷新");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("刷新完成");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
    }
}
