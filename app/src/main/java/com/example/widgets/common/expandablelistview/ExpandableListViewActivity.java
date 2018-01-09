package com.example.widgets.common.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.demo.R;

public class ExpandableListViewActivity extends AppCompatActivity {

    private ExpandableListView expandableList;
    private String[] category = new String[]{"僵尸  ", "魔法植物", "远程植物"};

    private String[][] subCategory = new String[][]{
            {"旗帜僵尸", "铠甲僵尸", "书生见识", "铁桶僵尸", "尸娃僵尸", "舞蹈僵尸"},
            {"黄金蘑菇", "贪睡蘑菇", "大头蘑菇", "诱惑植物", "多嘴蘑菇", "七彩蘑菇"},
            {"满天星", "风车植物", "带刺植物", "贪睡植物", "双子植物", "胆怯蘑菇"}

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        expandableList = (ExpandableListView) findViewById(R.id.expandable_list);
        expandableList.setGroupIndicator(null);
        final MyExpandableListAdapter adapter = new MyExpandableListAdapter(ExpandableListViewActivity.this);
        /*final SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                category,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {"NAME", "UUID"},
                new int[] { android.R.id.text1, android.R.id.text2 },
                subCategory,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {"NAME", "UUID"},
                new int[] { android.R.id.text1, android.R.id.text2 }
        );*/
        expandableList.setAdapter(adapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String msg = "你单击了：" + adapter.getChild(groupPosition, childPosition);
                Toast.makeText(ExpandableListViewActivity.this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}
