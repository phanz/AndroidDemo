package com.example.demo.background;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demo.R;

/**
 * selector可以引用lay-list和shape作为其背景
 * lay-list可以引用shape作为其背景
 * shape是基础的背景
 */
public class BackgroundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
    }
}
