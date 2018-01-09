package com.example.widgets.common.fragment;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener{

    private View mTrainingImage;
    private View mDeviceImage;
    private View mMeImage;

    private Fragment mHealthFragment;
    private Fragment mDeviceFragment;
    private Fragment mMeFragment;
    private Fragment mFragmentNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mTrainingImage = findViewById(R.id.health_bottom_view);
        mDeviceImage = findViewById(R.id.device_bottom_view);
        mMeImage = findViewById(R.id.me_bottom_view);
        mTrainingImage.setOnClickListener(this);
        mDeviceImage.setOnClickListener(this);
        mMeImage.setOnClickListener(this);

        mHealthFragment = new HealthFragment();
        mDeviceFragment = new DeviceFragment();
        mMeFragment = new MeFragment();

        mFragmentNow = null;
        changeFragment(mHealthFragment);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.health_bottom_view:
                changeFragment(mHealthFragment);
                break;

            case R.id.device_bottom_view:
                changeFragment(mDeviceFragment);
                break;

            case R.id.me_bottom_view:
                changeFragment(mMeFragment);
                break;

            default:
                break;
        }
    }

    public void changeFragment(Fragment fragment){
        if(fragment != mFragmentNow){
            mFragmentNow = fragment;
            Bundle bundle = new Bundle();
            bundle.putString("key","Activity Data");//Fragment数据传递
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
        }
    }
}
