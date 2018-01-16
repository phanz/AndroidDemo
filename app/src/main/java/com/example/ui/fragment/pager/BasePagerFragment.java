package com.example.ui.fragment.pager;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;

public abstract class BasePagerFragment extends Fragment {
    public static final String TAG = "BasePagerFragment";

    protected static final String TITLE = "title";
    protected String mTitle;

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
