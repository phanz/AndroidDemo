package com.example.widgets.common.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.R;

import java.util.ArrayList;

public class ViewPagerActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String TAG = "ViewPagerActivity";

    private int tabWidth;
    private ImageView cursorImg;
    private LinearLayout.LayoutParams lp;

    private TextView num_tab1_tv;
    private TextView num_tab2_tv;
    private TextView num_tab3_tv;
    private TextView num_tab4_tv;
    private TextView num_tab5_tv;

    private FrameLayout tab1_fl;
    private FrameLayout tab2_fl;
    private FrameLayout tab3_fl;
    private FrameLayout tab4_fl;
    private FrameLayout tab5_fl;

    private ArrayList<TextView> tvList;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentsList;

    RimFragment fragment1, fragment2, fragment3, fragment4, fragment5;
    private Object currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewpager);
        initViews();

    }

    private void initViews() {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        tabWidth = width / 5;
        cursorImg = (ImageView) findViewById(R.id.rim_cursor);
        lp = (LinearLayout.LayoutParams) cursorImg.getLayoutParams();
        lp.width = tabWidth;
        cursorImg.setLayoutParams(lp);

        num_tab1_tv = (TextView) findViewById(R.id.rim_tab1_tv);
        tab1_fl = (FrameLayout) findViewById(R.id.rim_tab1_fl);

        num_tab2_tv = (TextView) findViewById(R.id.rim_tab2_tv);
        tab2_fl = (FrameLayout) findViewById(R.id.rim_tab2_fl);

        num_tab3_tv = (TextView) findViewById(R.id.rim_tab3_tv);
        tab3_fl = (FrameLayout) findViewById(R.id.rim_tab3_fl);

        num_tab4_tv = (TextView) findViewById(R.id.rim_tab4_tv);
        tab4_fl = (FrameLayout) findViewById(R.id.rim_tab4_fl);

        num_tab5_tv = (TextView) findViewById(R.id.rim_tab5_tv);
        tab5_fl = (FrameLayout) findViewById(R.id.rim_tab5_fl);

        tab1_fl.setOnClickListener(this);
        tab2_fl.setOnClickListener(this);
        tab3_fl.setOnClickListener(this);
        tab4_fl.setOnClickListener(this);
        tab5_fl.setOnClickListener(this);

        tvList = new ArrayList<>();
        tvList.add(num_tab1_tv);
        tvList.add(num_tab2_tv);
        tvList.add(num_tab3_tv);
        tvList.add(num_tab4_tv);
        tvList.add(num_tab5_tv);
        initViewPager();

    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.rim_third_vp);
        fragmentsList = new ArrayList<>();
        fragment1 = new RimFragment();
        fragment1.setMsgName("1", "周边");

        fragment2 = new RimFragment();
        fragment2.setMsgName("2", "周边");
        fragment3 = new RimFragment();
        fragment3.setMsgName("3", "周边");
        fragment4 = new RimFragment();
        fragment4.setMsgName("4", "周边");
        fragment5 = new RimFragment();
        fragment5.setMsgName("5", "周边");
        fragmentsList.add(fragment1);
        fragmentsList.add(fragment2);
        fragmentsList.add(fragment3);
        fragmentsList.add(fragment4);
        fragmentsList.add(fragment5);

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentsList));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rim_tab1_fl:
                viewPager.setCurrentItem(0);
                selectTab(0);
                break;
            case R.id.rim_tab2_fl:
                viewPager.setCurrentItem(1);
                selectTab(1);
                break;
            case R.id.rim_tab3_fl:
                viewPager.setCurrentItem(2);
                selectTab(2);
                break;
            case R.id.rim_tab4_fl:
                viewPager.setCurrentItem(3);
                selectTab(3);
                break;
            case R.id.rim_tab5_fl:
                viewPager.setCurrentItem(4);
                selectTab(4);
                break;
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        lp.leftMargin = positionOffsetPixels / 5 + tabWidth * position;
        cursorImg.setLayoutParams(lp);
        currentIndex = position;
    }

    @Override
    public void onPageSelected(int position) {
        selectTab(position);
    }

    public void selectTab(int i){
        fragment1.setMsgName((i + 1) + "", "周边");//周边的官方和会员的接口参数,1为官方的数据
        num_tab1_tv.setTextColor(getResources().getColor(R.color.red_base));
        num_tab2_tv.setTextColor(getResources().getColor(R.color.text_gray_4));
        num_tab3_tv.setTextColor(getResources().getColor(R.color.text_gray_4));
        num_tab4_tv.setTextColor(getResources().getColor(R.color.text_gray_4));
        num_tab5_tv.setTextColor(getResources().getColor(R.color.text_gray_4));
        for(TextView textView : tvList){
            textView.setTextColor(getResources().getColor(R.color.text_gray_4));
        }
        tvList.get(i).setTextColor(getResources().getColor(R.color.red_base));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
