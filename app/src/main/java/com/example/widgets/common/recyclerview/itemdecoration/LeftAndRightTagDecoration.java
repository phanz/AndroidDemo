package com.example.widgets.common.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanzai.peng on 2017/4/13.
 */

public class LeftAndRightTagDecoration extends RecyclerView.ItemDecoration{

    private static final int TAG_WIDTH = 8;

    private int tagWidth;
    private Paint leftPaint;
    private Paint rightPaint;

    public LeftAndRightTagDecoration(){
        tagWidth = TAG_WIDTH;
        leftPaint = new Paint();
        leftPaint.setColor(Color.RED);

        rightPaint = new Paint();
        rightPaint.setColor(Color.BLUE);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        for(int i = 0; i < childCount; i++){
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if( (position & 1) == 0){
                int left = parent.getLeft();
                int right = left + tagWidth;
                int top = child.getTop();
                int bottom = child.getBottom();
                c.drawRect(left,top,right,bottom,leftPaint);
            }else{
                int left = parent.getWidth() - tagWidth;
                int right = parent.getWidth();
                int top = child.getTop();
                int bottom = child.getBottom();
                c.drawRect(left,top,right,bottom,rightPaint);
            }
        }
    }
}
