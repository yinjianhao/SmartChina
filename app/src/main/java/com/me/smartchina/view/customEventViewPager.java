package com.me.smartchina.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @auther yjh
 * @date 2016/8/21
 */
public class customEventViewPager extends ViewPager {

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    public customEventViewPager(Context context) {
        super(context);
    }

    public customEventViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                endX = ev.getX();
                endY = ev.getY();

                if (Math.abs(startX - endX) > Math.abs(startY - endY)) {
                    if (endX - startX > 0 && getCurrentItem() == 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (endX - startX < 0 && getCurrentItem() == getAdapter().getCount() - 1) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
