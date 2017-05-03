package com.example.demo.listview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanzai.peng on 2017/3/7.
 */

public abstract class BasicAdapter<T> extends BaseAdapter {
    private List<T> mShowItems = new ArrayList<>();

    public BasicAdapter(){

    }

    public BasicAdapter(List<T> showItems){
        mShowItems = showItems;
    }

    @Override
    public int getCount() {
        return mShowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mShowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BasicHolder holder;
        if( view == null){
            holder = createViewHolder(i);
        } else {
            holder = (BasicHolder)view.getTag();
        }
        holder.bindView(mShowItems.get(i));
        View v = holder.getView();
        return v;
    }

    protected  int headType = 0;
    protected  int bodyType = 1;

    @Override
    public int getItemViewType(int position) {
        if( mShowItems.get(position) instanceof String ){
            return headType;
        } else {
            return bodyType;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void addItem(T item){
        mShowItems.add(item);
        notifyDataSetChanged();
    }

    public void setItems(List<T> items){
        mShowItems.clear();
        if(items != null){
            for(T item : items){
                mShowItems.add(item);
            }
        }
        notifyDataSetChanged();
    }

    public abstract BasicHolder createViewHolder(int position);
}
