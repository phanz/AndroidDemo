package com.example.widgets.common.listview;

import android.content.Context;

import java.util.List;

/**
 * Created by hanzai.peng on 2017/3/7.
 */

public class PersonAdapter extends BasicAdapter {

    private Context mContext;

    public PersonAdapter(Context context){
        mContext = context;
    }

    public PersonAdapter(Context context, List showItems){
        super(showItems);
        mContext = context;
    }

    @Override
    public BasicHolder createViewHolder(int position) {
        int itemViewType = getItemViewType(position);
        BasicHolder holder = null;
        if(itemViewType == headType){
            holder = new HeadViewHolder(mContext);
        } else {
            holder = new PersonHolder(mContext);
        }
        return holder;

    }
}
