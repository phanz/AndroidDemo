package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.demo.aidl.client.BinderClientActivity;

public class MainActivity extends AppCompatActivity {

    private Button mAidlBtn;
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
    }
}
