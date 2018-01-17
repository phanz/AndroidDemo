package com.example.ui.fragment.communicate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.R;
import com.example.ui.fragment.pager.BasePagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StackFragment extends BasePagerFragment {

    @BindView(R.id.title_text)
    public TextView mTitleText;

    public static BasePagerFragment newInstance(String title) {
        StackFragment fragment = new StackFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stack, container, false);
        ButterKnife.bind(this,view);
        mTitleText.setText(getTitle());
        return view;
    }

}
