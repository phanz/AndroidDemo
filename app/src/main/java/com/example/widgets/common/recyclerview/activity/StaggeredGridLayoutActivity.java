package com.example.widgets.common.recyclerview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.demo.R;
import com.example.widgets.common.recyclerview.adapter.StaggeredHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class StaggeredGridLayoutActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mStaggeredHomeAdapter = new StaggeredHomeAdapter(this, mDatas);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mStaggeredHomeAdapter);
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initEvent();

    }

    private void initEvent() {
        mStaggeredHomeAdapter.setOnItemClickLitener(new StaggeredHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(StaggeredGridLayoutActivity.this,
                        position + " click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(StaggeredGridLayoutActivity.this,
                        position + " long click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_view_main_staggered, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_add:
                mStaggeredHomeAdapter.addData(1);
                break;
            case R.id.id_action_delete:
                mStaggeredHomeAdapter.removeData(1);
                break;
        }
        return true;
    }

}