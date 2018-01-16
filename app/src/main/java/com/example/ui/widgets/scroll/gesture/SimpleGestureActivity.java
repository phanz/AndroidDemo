package com.example.ui.widgets.scroll.gesture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.demo.R;

public class SimpleGestureActivity extends AppCompatActivity {

    public static final String TAG = SimpleGestureActivity.class.getSimpleName();

    private GestureDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        mDetector = new GestureDetector(this,new SimpleGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener{
        final int FLING_MIN_DISTANCE = 100;
        final int FLING_MIN_VELOCITY = 200;
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp: ");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress: ");
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll: ");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        /**
         * 判断：用户向左滑动距离超过100px且滑动速度超过100px/s,即判断向左滑动
         * @param e1 第一个ACTION_DOWN MotionEvent
         * @param e2 最后一个ACTION_MOVE MotionEvent
         * @param velocityX X轴上的移动速度，像素/秒
         * @param velocityY Y轴上的移动速度，像素/秒
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //判断向左滑动还是向右滑动【上下滑动同理可推】
            if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                Log.d(TAG, "onFling:  Fling left");
            }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                Log.d(TAG, "onFling:  Fling right");
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress: ");
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown: ");
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: ");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d(TAG, "onDoubleTapEvent: " + e.getAction());
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed: ");
            return super.onSingleTapConfirmed(e);
        }
    }

}
