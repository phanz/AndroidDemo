package com.example.ui.widgets.scroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;
import com.example.ui.widgets.scroll.animation.AnimationActivity;
import com.example.ui.widgets.scroll.gesture.SimpleGestureActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.animation_btn,R.id.gesture_btn })
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.animation_btn:
                intent = new Intent(this, AnimationActivity.class);
                startActivity(intent);
                break;

            case R.id.gesture_btn:
                intent = new Intent(this, SimpleGestureActivity.class);
                startActivity(intent);
                break;
        }
    }

}
