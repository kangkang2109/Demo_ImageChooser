package com.example.kk.permissionsdemo.utils;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by kk on 2017/8/19.
 */

public class StrongViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public StrongViewHolder(View itemView) {
        super(itemView);
        if (views == null) {
            views = new SparseArray<>();
        }
    }

    public <T extends View> T findView(int id) {
        T view;
        if ((view = (T) views.get(id)) == null) {
            view = (T) itemView.findViewById(id);
            views.append(id, view);
        }
        return view;
    }
}
