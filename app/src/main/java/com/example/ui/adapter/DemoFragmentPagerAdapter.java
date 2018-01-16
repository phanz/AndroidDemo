package com.example.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.ui.fragment.pager.BasePagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanzai.peng on 2018/1/15.
 */

public class DemoFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BasePagerFragment> mPagerFragmentList;
    private Context mContext;

    public DemoFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mPagerFragmentList = new ArrayList<>();
        mContext = context;
    }

    public void addItem(BasePagerFragment basePagerFragment){
        mPagerFragmentList.add(basePagerFragment);
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.news_title);
        tv.setText(mPagerFragmentList.get(position).getTitle());
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        //img.setImageResource(imageResId[position]);
        return v;
    }

    @Override
    public Fragment getItem(int position) {
        return mPagerFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mPagerFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPagerFragmentList.get(position).getTitle();
    }
}
