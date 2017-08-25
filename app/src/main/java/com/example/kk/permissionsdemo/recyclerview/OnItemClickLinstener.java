package com.example.kk.permissionsdemo.recyclerview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kk on 2017/8/20.
 */

public abstract class OnItemClickLinstener implements RecyclerView.OnItemTouchListener {

    GestureDetectorCompat gd;

    RecyclerView rv;

    public OnItemClickLinstener(RecyclerView rv) {

        this.rv = rv;
        this.gd = new GestureDetectorCompat(rv.getContext(), new ItemTouchListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gd.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gd.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh);

    private class ItemTouchListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View view = rv.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder vh = rv.getChildViewHolder(view);
                onItemClick(vh);
            }
            return super.onSingleTapUp(e);
        }
    }
}
