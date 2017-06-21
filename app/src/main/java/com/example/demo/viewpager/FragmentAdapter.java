package com.example.demo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class FragmentAdapter  extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentList;

    public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}