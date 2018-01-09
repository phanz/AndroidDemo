package com.example.widgets.custom.surfaceview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanz on 2017/8/26.
 */

public class SurfaceTestAdapter extends BaseAdapter {
    private List<String> nameList;
    private Context mContext;
    public SurfaceTestAdapter(Context context){
        nameList = new ArrayList<>();
        mContext = context;
    }
    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_surface,parent,false);
        MySurfaceView mySurfaceView = (MySurfaceView) view.findViewById(R.id.player_view);
        ViewGroup.LayoutParams params = mySurfaceView.getLayoutParams();
        params.width = 800;
        params.height = 800;
        mySurfaceView.setLayoutParams(params);
        return view;
    }

    public void setList(List<String> nameList){
        this.nameList = nameList;
        notifyDataSetChanged();
    }
}
