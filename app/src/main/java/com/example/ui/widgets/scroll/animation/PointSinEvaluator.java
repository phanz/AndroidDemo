package com.example.ui.widgets.scroll.animation;

import android.animation.TypeEvaluator;


/**
 * Created by phanz on 2017/9/17.
 */

public class PointSinEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());

        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endPoint.getY() / 2;
        Point point = new Point(x, y);
        return point;
    }
}