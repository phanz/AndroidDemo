package com.example.ui.widgets.scroll.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.demo.R;

public class AnimationActivity extends AppCompatActivity {

    private Spinner mInterpolatorSpinner;
    private PointAnimView mPointAnimView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mInterpolatorSpinner = (Spinner)findViewById(R.id.interpolator_type_spinner);
        mPointAnimView = (PointAnimView)findViewById(R.id.point_anim_view);
        mInterpolatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPointAnimView.setInterpolatorType((int)id);
                mPointAnimView.stopAnimation();
                mPointAnimView.startAnimation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mInterpolatorSpinner.setSelection(0,true);




    }
}
