package com.example.component;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.component.activity.LifeCycleActivity;
import com.example.demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_cycle_btn})
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.activity_cycle_btn:
                Intent intent = new Intent(this,LifeCycleActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
