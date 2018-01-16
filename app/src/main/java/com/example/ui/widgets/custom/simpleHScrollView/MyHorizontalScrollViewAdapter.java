package com.example.ui.widgets.custom.simpleHScrollView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;

import java.util.List;

/**
 * Created by hanzai.peng on 2017/4/14.
 */

public class MyHorizontalScrollViewAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mDatas;

    public MyHorizontalScrollViewAdapter(Context context, List<Integer> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_index_gallery_item, parent, false);
            viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mText = (TextView) convertView.findViewById(R.id.id_index_gallery_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImg.setImageResource(mDatas.get(position));
        viewHolder.mText.setText("some info");
        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }
}
