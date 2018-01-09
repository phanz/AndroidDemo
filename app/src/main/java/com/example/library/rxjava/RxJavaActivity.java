package com.example.library.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;

public class RxJavaActivity extends AppCompatActivity {

    private Button mRxjvaBtn;
    private RxJava rxJava;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        rxJava = new RxJava();
        mRxjvaBtn = (Button)findViewById(R.id.rxjava_btn);
        mRxjvaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxJava.fish();
                //rxJava.birthFish();
            }
        });
    }
}
