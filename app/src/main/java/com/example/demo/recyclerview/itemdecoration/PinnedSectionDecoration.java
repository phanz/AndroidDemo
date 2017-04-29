package com.example.demo.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanzai.peng on 2017/4/14.
 */

public class PinnedSectionDecoration extends RecyclerView.ItemDecoration {

    private static final int SECTION_HEIGHT = 64;
    private int sectionHeight;
    private Paint sectionPaint;
    private Paint textPaint;
    private Paint.FontMetrics fontMetrics;
    private SectionDecoration.DecorationCallback callback;

    public PinnedSectionDecoration(SectionDecoration.DecorationCallback callback){
        sectionHeight = SECTION_HEIGHT;
        sectionPaint = new Paint();
        sectionPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(80);
        fontMetrics = new Paint.FontMetrics();
        textPaint.getFontMetrics(fontMetrics);

        this.callback = callback;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;

        long preGroupId,groupId = -1;
        for(int i= 0; i < parent.getChildCount(); i++){
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if(preGroupId == groupId) continue;
            String textLine = callback.getGroupFirstLine(position);
            int textY = Math.max(sectionHeight,child.getTop());
            if(position + 1 < state.getItemCount()){
                int viewBottom = child.getBottom();
                long nextGroupId = callback.getGroupId(position + 1);
                if(nextGroupId != groupId && viewBottom < textY){
                    textY = viewBottom;
                }
            }
            c.drawRect(left,textY - sectionHeight,right,textY,sectionPaint);

            //int baseline = (int) (top + (bottom - top - (fontMetrics.ascent + fontMetrics.descent)) / 2);
            c.drawText(textLine,left,textY,textPaint);
        }

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
