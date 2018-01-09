package com.example.widgets.custom.simpleHScrollView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanzai.peng on 2017/4/14.
 */

public class MyHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener{

    private static final String TAG = "MyHorizontalScrollView";

    private CurrentImageChangeListener mListener;
    private OnItemClickListener mOnItemClickListener;
    private LinearLayout mContainer;
    private int mChildWidth;
    private int mChildHeight;
    private int mCurrentIndex;
    private int mFirstIndex;
    private MyHorizontalScrollViewAdapter mAdapter;
    private int mCountOneScreen;
    private int mScreenWidth;
    private Map<View,Integer> mViewPos = new HashMap<>();

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    public void initDataSet(MyHorizontalScrollViewAdapter adapter){
        mAdapter = adapter;
        mContainer = (LinearLayout)getChildAt(0);
        View view = mAdapter.getView(0,null,mContainer);
        if(mChildWidth == 0 && mChildHeight == 0){

            int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            int h = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
            view.measure(w,h);
            mChildWidth = view.getMeasuredWidth();
            mChildHeight = view.getMeasuredHeight();
            mCountOneScreen = mScreenWidth / mChildWidth + 2;
            Log.e(TAG,view.getMeasuredWidth() + "," + view.getMeasuredHeight());
            Log.e(TAG,"mCountScreen = " + mCountOneScreen + " ,mChildWidth = "+ mChildWidth);
        }
        initFirstScreenChildren(mCountOneScreen);
    }

    public void initFirstScreenChildren(int countOneScreen){
        mContainer.removeAllViews();
        mViewPos.clear();
        for(int i = 0; i < countOneScreen; i++){
            View view = mAdapter.getView(i,null,mContainer);
            view.setOnClickListener(this);
            mContainer.addView(view);
            mViewPos.put(view,i);
            mCurrentIndex = i;
        }
        if(mListener != null){
            notifyCurrentImgChanged();
        }
    }

    public void notifyCurrentImgChanged(){
        for(int i = 0; i < mContainer.getChildCount(); i++){
            mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        mListener.onCurrentImgChanged(mFirstIndex,mContainer.getChildAt(0));
    }

    private void loadNextImg(){
        if(mCurrentIndex == mAdapter.getCount() -1){
            return;
        }
        scrollTo(0,0);
        mViewPos.remove(mContainer.getChildAt(0));
        mContainer.removeViewAt(0);

        View view = mAdapter.getView(++mCurrentIndex,null,mContainer);
        view.setOnClickListener(this);
        mContainer.addView(view);
        mViewPos.put(view,mCurrentIndex);
        mFirstIndex++;
        if(mListener != null){
            //notifyCurrentImgChanged();
        }
    }

    private void loadPreImg(){
        if(mFirstIndex == 0){
            return;
        }
        int index = mCurrentIndex - mCountOneScreen;
        if(index >= 0){
            int oldViewPos = mContainer.getChildCount() - 1;
            mViewPos.remove(mContainer.getChildAt(oldViewPos));
            mContainer.removeViewAt(oldViewPos);

            View view = mAdapter.getView(index,null,mContainer);
            mViewPos.put(view,index);
            mContainer.addView(view,0);
            view.setOnClickListener(this);
            scrollTo(mChildWidth,0);
            mCurrentIndex--;
            mFirstIndex--;
            if(mListener != null){
                //notifyCurrentImgChanged();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                int scrollX = getScrollX();
                Log.d("TAG","scrollX:"+scrollX);
                if(scrollX >= mChildWidth){
                    loadNextImg();
                }else if( scrollX == 0){
                    loadPreImg();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        if(mOnItemClickListener != null){
            for(int i = 0; i < mContainer.getChildCount(); i++){
                mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
            mOnItemClickListener.onClick(view,mViewPos.get(view));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setCurrentImageChangeListener(CurrentImageChangeListener listener){
        mListener = listener;
    }

    public interface  CurrentImageChangeListener{
        void onCurrentImgChanged(int position, View viewIndicator);
    }

    public interface  OnItemClickListener{
        void onClick(View view, int position);
    }
}
