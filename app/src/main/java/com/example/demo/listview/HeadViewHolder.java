package com.example.demo.listview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.demo.R;

/**
 * Created by hanzai.peng on 2017/3/7.
 */

public class HeadViewHolder extends BasicHolder{

    private TextView mTitleView;

    public HeadViewHolder(Context context){
        super(context);
    }

    @Override
    protected View createView() {
        View view = View.inflate(mContext, R.layout.item_adapter_title,null);
        mTitleView = (TextView) view.findViewById(R.id.tv_head);
        return view;
    }

    @Override
    public void bindView(Object appInfo) {
        mTitleView.setText((String)appInfo);
    }
}
