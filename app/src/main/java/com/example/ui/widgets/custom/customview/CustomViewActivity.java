package com.example.ui.widgets.custom.customview;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.demo.R;
import com.example.ui.widgets.custom.PictureTextActivity;

public class CustomViewActivity extends AppCompatActivity {
    public static final String TAG = "CustomViewActivity";

    private TitleBar mTitleBar;

    private SelectView selectView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//不管用
        getSupportActionBar().hide();
        setContentView(R.layout.activity_custom_view);

        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        initTitleBar();

        selectView = (SelectView) findViewById(R.id.select_view);
        selectView.setmStartWidth(0);
        selectView.setListener(new SelectView.getNumberListener() {
            @Override
            public void getNumber(int number) {
                Log.d(TAG,"选中的数组为：" + number);
            }
        });
    }

    private void initTitleBar() {
        if (null != mTitleBar) {
            mTitleBar.setTitle("标题栏");
            mTitleBar.setTitleColor(Color.WHITE);
            mTitleBar.setTitleSize(20);
            mTitleBar.setImmersive(true);
            setActivityImmersive(this);

            mTitleBar.setLeftImageResource(R.drawable.icon_back);
            mTitleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_add) {
                @Override
                public void performAction(View view) {
                    Toast.makeText(CustomViewActivity.this, "Add Action", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static void setActivityImmersive(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
