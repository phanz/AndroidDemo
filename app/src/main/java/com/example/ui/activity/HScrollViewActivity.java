package com.example.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demo.R;
import com.example.ui.widgets.custom.HScrollView;
import com.example.ui.adapter.HScrollAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HScrollViewActivity extends AppCompatActivity {

    private HScrollView listView;
    private HScrollAdapter mAdapter;

    private List<Map<String, Object>> list;
    private Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscroll_view);
        initView();
    }

    private void initView() {
        listView = (HScrollView) findViewById(R.id.h_scroll_view_2);
        list = this.getData();
        mAdapter = new HScrollAdapter(this, list);
        listView.setAdapter(mAdapter);
    }

    private List<Map<String, Object>> getData() {

        int[] pic = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
                R.drawable.f, R.drawable.g};

        list = new ArrayList<>();
        for (int i = 0; i < pic.length; i++) {
            map = new HashMap<>();
            map.put("index", "第" + (i + 1) + "张");
            map.put("img", pic[i]);
            list.add(map);
        }
        return list;
    }
}
