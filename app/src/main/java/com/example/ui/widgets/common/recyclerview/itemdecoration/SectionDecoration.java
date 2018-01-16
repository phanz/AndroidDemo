package com.example.ui.widgets.common.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by hanzai.peng on 2017/4/13.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration{

    private static final int SECTION_HEIGHT = 64;

    private int sectionHeight;
    private Paint sectionPaint;
    private Paint textPaint;
    private Paint.FontMetrics fontMetrics;
    private DecorationCallback callback;

    public SectionDecoration(DecorationCallback callback){
        sectionHeight = SECTION_HEIGHT;
        sectionPaint = new Paint();
        sectionPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(80);
        textPaint.getFontMetrics(fontMetrics);
        fontMetrics = new Paint.FontMetrics();

        this.callback = callback;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getLeft();
        int right = parent.getWidth();
        for(int i = 0;i < childCount; i++){
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            if(isFirstInGroup(position)){
                int top = view.getTop() - sectionHeight;
                int bottom = view.getTop();
                String textLine = callback.getGroupFirstLine(position);
                Log.d("TAG","ChildAt:" + i + "\tFirstLine:"+textLine);
                c.drawRect(left,top,right,bottom,sectionPaint);
                c.drawText(textLine,left,bottom,textPaint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if(isFirstInGroup(position)){
            outRect.top = sectionHeight;
        }

    }

    private boolean isFirstInGroup(int position){
        if(position == 0){
            return true;
        } else {
            long preGroupId = callback.getGroupId(position - 1);
            long groupId = callback.getGroupId(position);
            return preGroupId != groupId;
        }
    }

    public interface DecorationCallback{
        long getGroupId(int position);
        String getGroupFirstLine(int position);
    }
}
