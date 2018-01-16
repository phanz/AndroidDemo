package com.example.ui.widgets.custom.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
    }

    class CustomView extends View {
        Paint paint;
        public CustomView(Context context){
            super(context);
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

}
