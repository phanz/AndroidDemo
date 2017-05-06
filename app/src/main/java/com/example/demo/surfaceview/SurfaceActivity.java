package com.example.demo.surfaceview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demo.R;

public class SurfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySurfaceView view = new MySurfaceView(this);
        setContentView(view);
    }
}
