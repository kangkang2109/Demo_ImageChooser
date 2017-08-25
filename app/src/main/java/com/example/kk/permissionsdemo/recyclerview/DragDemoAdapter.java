package com.example.kk.permissionsdemo.recyclerview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kk.permissionsdemo.R;
import com.example.kk.permissionsdemo.utils.StrongViewHolder;

import java.util.List;

/**
 * Created by kk on 2017/8/19.
 */

public class DragDemoAdapter extends RecyclerView.Adapter<StrongViewHolder> {


    private List<String> data;

    public DragDemoAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public StrongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_item, parent, false);
        return new StrongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StrongViewHolder holder, int position) {
        holder.<TextView>findView(R.id.textView).setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
