package com.example.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by phanz on 2017/4/29.
 */

public class StaticTextPaintView extends View {

    private Paint mPaint;
    public StaticTextPaintView(Context context) {
        super(context);
    }

    public StaticTextPaintView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        TextPaint tp = new TextPaint();
        tp.setColor(Color.BLUE);
        tp.setStyle(Paint.Style.FILL);
        tp.setTextSize(50);
        String message = "使用普通的Paint来绘制文字在处理文字换行回车时会比较麻烦，尤其是不同机型不同屏幕；" +
                "而使用StaticLayout,将会使换行比较方便";
        StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);
        canvas.restore();
    }
}
