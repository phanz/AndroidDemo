package com.example.ui.widgets.common.listview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.demo.R;

/**
 * Created by hanzai.peng on 2017/3/7.
 */

public class PersonHolder extends BasicHolder<Person> {

    private TextView mNameView;
    private TextView mAgeView;

    public PersonHolder(Context context){
        super(context);
    }

    @Override
    protected View createView() {
        View view = View.inflate(mContext,R.layout.item_adapter_content,null);
        mNameView = (TextView) view.findViewById(R.id.tv_name);
        mAgeView = (TextView) view.findViewById(R.id.tv_age);
        return view;
    }

    @Override
    public void bindView(Person person) {
        mNameView.setText(person.getName());
        mAgeView.setText(person.getAge());
    }
}
