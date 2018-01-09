package com.example.widgets.common.expandablelistview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demo.R;

/**
 * Created by hanzai.peng on 2017/4/7.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private String[] category = new String[]{"僵尸  ", "魔法植物", "远程植物","僵尸  "};

    private String[][] subCategory = new String[][]{
            {"旗帜僵尸", "铠甲僵尸", "书生见识", "铁桶僵尸", "尸娃僵尸", "舞蹈僵尸"},
            {"黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇", "七彩蘑菇"},
            {"满天星", "风车植物", "带刺植物", "贪睡植物", "双子植物", "胆怯蘑菇"},
            {"旗帜僵尸", "铠甲僵尸", "书生见识", "铁桶僵尸", "尸娃僵尸", "舞蹈僵尸"}

    };

    private Context mContext;

    public MyExpandableListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.group_expandable_list_view, null);
        TextView textView = (TextView) layout.findViewById(R.id.category);
        textView.setText(category[groupPosition]);
        ImageView arrowImg = (ImageView)layout.findViewById(R.id.arrowImage);

        if(groupPosition % 2 == 0){
            layout.setBackgroundColor(Color.GRAY);
        }
        if(isExpanded){
            arrowImg.setBackgroundResource(R.drawable.arrow_up);
        }else{
            arrowImg.setBackgroundResource(R.drawable.arrow_down);
        }
        return layout;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return category.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return category[groupPosition];
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subCategory[groupPosition].length;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.item_expandable_list_view, null);
        TextView textView = (TextView) layout.findViewById(R.id.sub_category);
        textView.setText(subCategory[groupPosition][childPosition]);

        return layout;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subCategory[groupPosition][childPosition];
    }
}
