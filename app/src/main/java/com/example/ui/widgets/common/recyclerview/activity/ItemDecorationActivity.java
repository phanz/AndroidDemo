package com.example.ui.widgets.common.recyclerview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.demo.R;
import com.example.ui.widgets.common.recyclerview.adapter.HomeAdapter;
import com.example.ui.widgets.common.recyclerview.itemdecoration.LeftAndRightTagDecoration;
import com.example.ui.widgets.common.recyclerview.itemdecoration.PinnedSectionDecoration;
import com.example.ui.widgets.common.recyclerview.itemdecoration.SectionDecoration;
import com.example.ui.widgets.common.recyclerview.itemdecoration.SimpleDividerDecoration;
import com.example.ui.widgets.common.recyclerview.itemdecoration.SimplePaddingDecoration;

import java.util.ArrayList;
import java.util.List;

public class ItemDecorationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDataList;
    private HomeAdapter mAdapter;

    private SimplePaddingDecoration paddingDecoration;
    private SimpleDividerDecoration dividerDecoration;
    private LeftAndRightTagDecoration leftAndRightTagDecoration;
    private SectionDecoration sectionDecoration;
    private PinnedSectionDecoration pinnedSectionDecoration;

    private boolean paddingFlag;
    private boolean divideFlag;
    private boolean sectionFlag;
    private boolean pinnedSectionFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_decoration);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        paddingDecoration = new SimplePaddingDecoration();
        dividerDecoration = new SimpleDividerDecoration();
        leftAndRightTagDecoration = new LeftAndRightTagDecoration();
        sectionDecoration = new SectionDecoration(decorationCallback);
        pinnedSectionDecoration = new PinnedSectionDecoration(decorationCallback);
        paddingFlag = true;
        divideFlag = false;
        sectionFlag = false;
        pinnedSectionFlag = false;

        initData();
        mAdapter = new HomeAdapter(this,mDataList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(paddingDecoration);

        /*mRecyclerView.addItemDecoration(new SimpleDividerDecoration());
        mRecyclerView.addItemDecoration(new LeftAndRightTagDecoration());
        mRecyclerView.addItemDecoration(new PinnedSectionDecoration(decorationCallback));*/
    }

    public void initData(){
        mDataList = new ArrayList<>();
        for(int i = 'A';i <= 'Z';i++){
            int randomLength = (int)(Math.random() * 10);
            for(int j = i;(j < i + randomLength) && (j <= 'Z'); j++){
                mDataList.add((char)i + "" + (char)j);
            }
        }
    }

    SectionDecoration.DecorationCallback decorationCallback =
            new SectionDecoration.DecorationCallback(){

                @Override
                public long getGroupId(int position) {
                    char a = mDataList.get(position).charAt(0);
                    return Character.toUpperCase(a);
                }

                @Override
                public String getGroupFirstLine(int position) {
                    return mDataList.get(position).substring(0,1).toUpperCase();
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_decoration_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.simple_padding:
                paddingChange();
                break;
            case R.id.simple_divider:
                dividerChange();
                break;
            case R.id.section_decoration:
                sectionChange();
                break;
            case R.id.pinned_section_decoration:
                pinnedSectionChange();
                break;

            default:
                break;
        }
        return true;
    }

    public void paddingChange(){
        paddingFlag = !paddingFlag;
        if(paddingFlag){
            mRecyclerView.addItemDecoration(paddingDecoration);
        }else{
            mRecyclerView.removeItemDecoration(paddingDecoration);
        }
    }

    public void dividerChange(){
        divideFlag = !divideFlag;
        if(divideFlag){
            mRecyclerView.addItemDecoration(dividerDecoration);
            mRecyclerView.addItemDecoration(leftAndRightTagDecoration);
        }else{
            mRecyclerView.removeItemDecoration(dividerDecoration);
            mRecyclerView.removeItemDecoration(leftAndRightTagDecoration);
        }
    }

    public void sectionChange(){
        sectionFlag = !sectionFlag;
        if(sectionFlag){
            mRecyclerView.addItemDecoration(sectionDecoration);
        }else{
            mRecyclerView.removeItemDecoration(sectionDecoration);
        }
    }

    public void pinnedSectionChange(){
        pinnedSectionFlag = !pinnedSectionFlag;
        if(pinnedSectionFlag){
            mRecyclerView.addItemDecoration(pinnedSectionDecoration);
        }else{
            mRecyclerView.removeItemDecoration(pinnedSectionDecoration);
        }
    }
}
