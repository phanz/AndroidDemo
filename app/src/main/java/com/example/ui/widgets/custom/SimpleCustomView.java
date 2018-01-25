package com.example.ui.widgets.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hanzai.peng on 2018/1/25.
 */

public class SimpleCustomView extends View {

    Paint paint;

    public SimpleCustomView(Context context){
        super(context);
        init();
    }

    public SimpleCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int px = getMeasuredWidth()/2;
        int py = getMeasuredHeight()/2;
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,px,py,paint);
        paint.setColor(Color.RED);
        canvas.save();
        canvas.rotate(90,px/2,py/2);
        canvas.drawLine(px/2,0,0,py/2,paint);
        canvas.drawLine(px/2,0,px,py/2,paint);
        canvas.drawLine(px/2,0,px/2,py,paint);
        canvas.restore();
        canvas.drawCircle(px - 50,py - 50,50,paint);
    }
}
