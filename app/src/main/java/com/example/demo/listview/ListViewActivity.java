package com.example.demo.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.demo.R;

public class ListViewActivity extends AppCompatActivity {

    private ListView mNameLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mNameLv = (ListView) findViewById(R.id.nameLv);
        PersonAdapter personAdapter = new PersonAdapter(this);
        mNameLv.setAdapter(personAdapter);

        personAdapter.addItem("Title");
        personAdapter.addItem(new Person("phanz","28"));
        personAdapter.addItem(new Person("phanz","28"));
        personAdapter.addItem(new Person("phanz","28"));

        setListViewHeightBasedOnChildren(mNameLv);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {

            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
