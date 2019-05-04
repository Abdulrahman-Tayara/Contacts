package com.tayara.abdulrahman.telephoneguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnClickItemRecyclerView extends RecyclerView.SimpleOnItemTouchListener {

    interface OnRecyclerClickListener  {
        public void onClick(View view ,int position);
    }
    private final OnRecyclerClickListener listener;
    private final GestureDetectorCompat gestureDetector;

    public OnClickItemRecyclerView(Context context, final RecyclerView recyclerView, final OnRecyclerClickListener listener) {
        this.listener = listener;
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (view != null && listener != null) {
                    listener.onClick(view,recyclerView.getChildAdapterPosition(view));
                }
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        if (gestureDetector != null) {
            return gestureDetector.onTouchEvent(e);
        }else {
            return false;
        }
    }
}
