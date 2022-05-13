package com.example.catgame;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {
    public GestureDetector gestureDetector;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    public OnSwipeListener(Context context)
    {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        public static final int SWIPE_THRESOLD = 100;
        public static final int SWIPE_VELOCITY_THERESOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float yDiff = e2.getY() - e1.getY();
            float xDiff = e2.getX() - e1.getX();
            if (Math.abs(xDiff) > Math.abs(yDiff))
            {
                if (Math.abs(xDiff) > SWIPE_THRESOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THERESOLD)
                {
                    if (xDiff > 0 )
                    {
                        onSwipeRight();
                    }
                    else
                    {
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
            else if (Math.abs(yDiff) > SWIPE_THRESOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THERESOLD)
            {
                if (yDiff > 0 )
                {
                    onSwipeBottom();
                }
                else
                {
                    onSwipeTop();
                }
                result = true;
            }
            return result;
        }
    }
    void onSwipeLeft(){}
    void onSwipeRight(){}
    void onSwipeTop(){}
    void onSwipeBottom(){}
}
