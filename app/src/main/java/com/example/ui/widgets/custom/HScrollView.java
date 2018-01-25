package com.example.ui.widgets.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

/**
 * Scroller滚动
 *
 * @author csdn chunqiuwei
 */
public class HScrollView extends ViewGroup {

    private ListAdapter listAdapter;

    private int rightIndex = 0;
    private int leftIndex = -1;

    private GestureDetector gestureDetector;
    private int leftOffset = 0;
    private int scrollXMax = Integer.MAX_VALUE;
    private int totalDistanceX = 0;
    private int preTotalDistanceX = 0;

    public HScrollView(Context context) {
        super(context);
        initParams();
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initParams();
    }

    public HScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams();
    }

    private void initParams() {
        gestureDetector = new GestureDetector(getContext(), gestureListener);
    }

    private OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        /**
         * 处理手指 由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
         *
         * @param e1 手势起点的移动事件
         * @param e2 当前手势点的移动事件
         * @param distanceX 距离上次调用onScroll方法的时候x轴滚动的距离 lastEvent2 - event2 = distance
         * @param distanceY 距离上次调用onScroll 方法的时候y轴滚动的距离
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            totalDistanceX += distanceX;//手指左滑为正,显示右边的内容
            requestLayout();
            return true;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if (listAdapter == null) {
            return;
        }
        if (totalDistanceX <= 0) {
            totalDistanceX = 0;
        }
        if (totalDistanceX > this.scrollXMax) {
            totalDistanceX = scrollXMax;
        }

        int distanceX = totalDistanceX - preTotalDistanceX;
        removeInvisibleViews(-distanceX);
        addRightChildViews(-distanceX);
        addLeftChildViews(-distanceX);
        layoutChildViews(-distanceX);
        preTotalDistanceX = totalDistanceX;
    }

    private void removeInvisibleViews(int distanceX) {
        // 移除左边看不到的view
        View firstVisibleView = getChildAt(0);
        if (firstVisibleView != null && distanceX + firstVisibleView.getRight() <= 0) {
            removeViewInLayout(firstVisibleView);
            leftOffset += firstVisibleView.getMeasuredWidth();
            leftIndex++;
        }

        // 移除右边看不到的view
        View lastVisibleView = getChildAt(getChildCount() - 1);
        if (lastVisibleView != null && lastVisibleView.getLeft() + distanceX >= getWidth()) {
            removeViewInLayout(lastVisibleView);
            rightIndex--;
        }
    }

    private void addRightChildViews(int distanceX) {
        // 2.让屏幕尽可能的显示Item。注意刚开始的时候是没有
        View rightChildView = getChildAt(getChildCount() - 1);

        // 获取此childView右边框距离parentView左边框的距离
        int rightEdge = rightChildView != null ? rightChildView.getRight() : 0;
        while (rightEdge + distanceX < getWidth() && rightIndex < listAdapter.getCount()) {
            View child = listAdapter.getView(rightIndex, null, null);
            child = measureChild(child);
            addViewInLayout(child, -1, child.getLayoutParams(), true);
            rightEdge += child.getMeasuredWidth();

            if (rightIndex == listAdapter.getCount() - 1) {
                scrollXMax = rightEdge + preTotalDistanceX - getWidth();
            }
            rightIndex++;
        }
    }

    private void addLeftChildViews(int distanceX) {
        View leftChildView = getChildAt(0);
        int leftEdge = leftChildView != null ? leftChildView.getLeft() : 0;
        while (leftEdge + distanceX > 0 && leftIndex >= 0) {
            View child = listAdapter.getView(leftIndex, null, null);
            child = measureChild(child);
            addViewInLayout(child, 0, child.getLayoutParams(), true);
            leftEdge -= child.getMeasuredWidth();
            leftIndex--;
            leftOffset -= child.getMeasuredWidth();
        }
    }

    /**
     * 3.把步骤2添加的view通过Layout布局到parentView中
     */
    private void layoutChildViews(int distanceX) {
        if (getChildCount() == 0) {
            return;
        }
        leftOffset += distanceX;
        int childLeft = leftOffset;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            child.layout(childLeft, 0, childWidth + childLeft, child.getMeasuredHeight());
            // 不过最好的写法是
            childLeft += childWidth + child.getPaddingRight();
        }
    }


    /**
     * 测量每个child的宽和高
     *
     * @param view
     * @return
     */
    private View measureChild(View view) {
        LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
        }
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view;
    }

    // @Override
    public ListAdapter getAdapter() {
        return listAdapter;
    }

    // @Override
    public void setAdapter(ListAdapter adapter) {
        this.listAdapter = adapter;
    }
}
