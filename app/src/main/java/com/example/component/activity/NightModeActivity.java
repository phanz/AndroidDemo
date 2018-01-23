package com.example.component.activity;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NightModeActivity extends AppCompatActivity implements ThemeManager.OnThemeChangeListener {

    private int mThemeNow = R.style.AppTheme;

    @BindView(R.id.hint_label)
    public TextView tv;
    @BindView(R.id.change_by_dynamic_btn)
    public Button btn_theme;
    @BindView(R.id.relativeLayout)
    public RelativeLayout relativeLayout;
    public ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mThemeNow = savedInstanceState.getInt("theme");
            setTheme(mThemeNow);
        }
        setContentView(R.layout.activity_night_mode);
        ThemeManager.registerThemeChangeListener(this);
        ButterKnife.bind(this);
        supportActionBar = getSupportActionBar();
    }

    @OnClick({ R.id.change_by_theme_btn,R.id.change_by_lib_btn,R.id.change_by_dynamic_btn })
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.change_by_theme_btn:
                Toast.makeText(this,"theme",Toast.LENGTH_SHORT).show();
                mThemeNow = (mThemeNow == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
                this.recreate();
                break;
            case R.id.change_by_lib_btn:
                Toast.makeText(this,"support",Toast.LENGTH_SHORT).show();
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                getDelegate().setLocalNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO
                        ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                // 同样需要调用recreate方法使之生效
                recreate();
                break;
            case R.id.change_by_dynamic_btn:
                Toast.makeText(this,"dynamic",Toast.LENGTH_SHORT).show();
                ThemeManager.setThemeMode(ThemeManager.getThemeMode() == ThemeManager.ThemeMode.DAY
                        ? ThemeManager.ThemeMode.NIGHT : ThemeManager.ThemeMode.DAY);
                break;

            default:
                break;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme", mThemeNow);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mThemeNow = savedInstanceState.getInt("theme");
    }

    public void initTheme() {

    }

    @Override
    public void onThemeChanged() {
        tv.setTextColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this, R.color.textColor)));
        btn_theme.setTextColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this, R.color.textColor)));
        relativeLayout.setBackgroundColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this, R.color.backgroundColor)));
        // 设置标题栏颜色
        if(supportActionBar != null){
            supportActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(ThemeManager.getCurrentThemeRes(this, R.color.colorPrimary))));
        }
        // 设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this, R.color.colorPrimary)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.unregisterThemeChangeListener(this);
    }
}
