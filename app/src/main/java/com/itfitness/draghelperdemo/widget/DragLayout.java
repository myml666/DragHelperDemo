package com.itfitness.draghelperdemo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @ProjectName: DragHelperDemo
 * @Package: com.itfitness.draghelperdemo.widget
 * @ClassName: DragLayout
 * @Description: java类作用描述
 * @Author: 作者名 itfitness
 * @CreateDate: 2020/4/26 17:34
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/26 17:34
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DragLayout extends LinearLayout {
    private ViewDragHelper viewDragHelper;
    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == getChildAt(1);
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - getPaddingRight() - child.getMeasuredWidth();
                int dragLeft;
                dragLeft = Math.min(Math.max(left,leftBound),rightBound);
                return dragLeft;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                int topBound = getPaddingTop();
                int bottomBound = getHeight() - getPaddingBottom() - child.getMeasuredHeight();
                int dragTop;
                dragTop = Math.min(Math.max(top,topBound),bottomBound);
                return dragTop;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if(getChildAt(1) == releasedChild){
                    int floatX;
                    int floatY;
                    if((releasedChild.getLeft()+(releasedChild.getMeasuredWidth()/2))<getWidth()/2){
                        floatX = getPaddingLeft();
                    }else {
                        floatX = getWidth() - getPaddingRight() - releasedChild.getMeasuredWidth();
                    }

                    viewDragHelper.settleCapturedViewAt(floatX, releasedChild.getTop());
                    invalidate();
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }
}
