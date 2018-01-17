package com.example.ui.fragment.communicate;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demo.R;

public class ResultFragmentActivity extends AppCompatActivity {

    private ResultFragment mResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment);

        FragmentManager fm = getSupportFragmentManager();
        mResultFragment = (ResultFragment) fm.findFragmentById(R.id.my_fragment_container);
        if(mResultFragment == null){
            mResultFragment = new ResultFragment();
            fm.beginTransaction().add(R.id.my_fragment_container, mResultFragment).commit();
        }
    }
}
