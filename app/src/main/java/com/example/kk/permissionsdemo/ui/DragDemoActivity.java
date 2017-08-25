package com.example.kk.permissionsdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.kk.permissionsdemo.recyclerview.OnItemClickLinstener;
import com.example.kk.permissionsdemo.R;
import com.example.kk.permissionsdemo.recyclerview.DragDemoAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kk on 2017/8/19.
 */

public class DragDemoActivity extends BaseActivity{

    @BindView(R.id.rv)
    RecyclerView rv_drag;

    DragDemoAdapter adapter;

    List<String> results;

    public static void start(Context context) {
        Intent i = new Intent(context, DragDemoActivity.class);
        context.startActivity(i);
    }


    @Override
    public int initLayoutId() {
        return R.layout.activity_recyclerview_drag_item;
    }

    @Override
    public void initData(Bundle saveInstanceState) {
        if (results == null) {
            results = Arrays.asList("1", "2","3", "4","5","6");
        }
    }

    @Override
    public void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new DragDemoAdapter(results);
        rv_drag.setAdapter(adapter);
        rv_drag.setLayoutManager(new LinearLayoutManager(this));
        rv_drag.setHasFixedSize(true);
        rv_drag.addOnItemTouchListener(new OnItemClickLinstener(rv_drag) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Toast.makeText(DragDemoActivity.this, "Item   "+ vh.getLayoutPosition(), Toast.LENGTH_SHORT).show();
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags;
                int swipeFlags;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlags = 0;
                } else {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    swipeFlags = 0;
                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(results, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(results, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }


        });
        touchHelper.attachToRecyclerView(rv_drag);
    }

}
