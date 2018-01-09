package com.example.widgets.custom.compoundbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.demo.R;
import com.example.widgets.custom.compoundbutton.view.CheckSwitchButton;
import com.example.widgets.custom.compoundbutton.view.SlideSwitchView;

public class CompoundButtonActivity extends AppCompatActivity {

    private ToggleButton mTogBtn;
    private CheckSwitchButton mCheckSwithcButton;
    private CheckSwitchButton mEnableCheckSwithcButton;
    private SlideSwitchView mSlideSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_button);
        initView();
    }

    private void initView() {
        mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //选中
                } else {
                    //未选中
                }
            }
        });// 添加监听事件
        mCheckSwithcButton = (CheckSwitchButton) findViewById(R.id.mCheckSwithcButton);
        mEnableCheckSwithcButton = (CheckSwitchButton) findViewById(R.id.mEnableCheckSwithcButton);
        mCheckSwithcButton.setChecked(false);
        mCheckSwithcButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    mEnableCheckSwithcButton.setEnabled(false);
                    mSlideSwitchView.setEnabled(false);
                } else {
                    mEnableCheckSwithcButton.setEnabled(true);
                    mSlideSwitchView.setEnabled(true);
                }
            }
        });
        mSlideSwitchView = (SlideSwitchView) findViewById(R.id.mSlideSwitchView);
        mSlideSwitchView.setOnChangeListener(new SlideSwitchView.OnSwitchChangedListener() {

            @Override
            public void onSwitchChange(SlideSwitchView switchView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {

                }
            }
        });
    }
}
