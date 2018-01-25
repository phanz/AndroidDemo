package com.example.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.ui.fragment.pager.CommonWidgetFragment;
import com.example.ui.fragment.pager.CustomFragment;
import com.example.ui.fragment.pager.DataFragment;
import com.example.ui.fragment.pager.LibraryFragment;
import com.example.ui.fragment.pager.OtherFragment;
import com.example.ui.fragment.pager.SystemFragment;
import com.example.ui.fragment.pager.TaskFragment;

/**
 * Created by hanzai.peng on 2017/3/17.
 */

public class DemoFragment extends Fragment {
    private static final String TAG = DemoFragment.class.getSimpleName();

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo,null);

        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        DemoFragmentPagerAdapter pagerAdapter =
                new DemoFragmentPagerAdapter(getChildFragmentManager(),getActivity());
        pagerAdapter.addItem(CommonWidgetFragment.newInstance("通用"));
        pagerAdapter.addItem(CustomFragment.newInstance("自定义"));
        pagerAdapter.addItem(DataFragment.newInstance("数据"));
        pagerAdapter.addItem(TaskFragment.newInstance("任务"));
        pagerAdapter.addItem(SystemFragment.newInstance("系统"));
        pagerAdapter.addItem(LibraryFragment.newInstance("开源库"));
        pagerAdapter.addItem(OtherFragment.newInstance("其他"));

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View customView = pagerAdapter.getTabView(i);
                tab.setCustomView(customView);
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
        return view;
    }

}
