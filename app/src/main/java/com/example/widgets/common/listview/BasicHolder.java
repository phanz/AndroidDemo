package com.example.widgets.common.listview;

import android.content.Context;
import android.view.View;

/**
 * Created by hanzai.peng on 2017/3/7.
 */

public abstract class BasicHolder<T> {
    private View view;
    protected Context mContext;
    public BasicHolder(Context context){
        mContext = context;
        view = createView();
        view.setTag(this);
    }

    public View getView() {
        return view;
    }

    protected abstract View createView();

    public abstract void bindView(T appInfo);
}
