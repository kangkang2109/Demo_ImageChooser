package com.example.kk.permissionsdemo.recyclerview;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.permissionsdemo.R;
import com.example.kk.permissionsdemo.model.ImageBean;
import com.example.kk.permissionsdemo.ui.OpenAlumActivity;
import com.example.kk.permissionsdemo.utils.ScreenUtil;
import com.example.kk.permissionsdemo.utils.StrongViewHolder;

import java.util.ArrayList;

/**
 * Created by kk on 2017/8/24.
 */

public class ShowPicAdapter extends RecyclerView.Adapter<StrongViewHolder> {

    ArrayList<ImageBean> images;

    Activity activity;

    OnCheckBoxListener listener;

    public ShowPicAdapter(Activity activity) {
        this.images = new ArrayList<>();
        this.activity = activity;
        ScreenUtil.init(activity);
    }

    @Override
    public StrongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_choose_pic_item, parent, false);

        return new StrongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StrongViewHolder holder, final int position) {
        if (activity instanceof OpenAlumActivity) {
            //解决CheckBox出错的现象
            holder.<CheckBox>findView(R.id.checkbox).setOnCheckedChangeListener(null);


            if (images.get(position).getCheck()) {
                holder.<CheckBox>findView(R.id.checkbox).setChecked(true);
            } else {
                holder.<CheckBox>findView(R.id.checkbox).setChecked(false);
            }

            holder.<CheckBox>findView(R.id.checkbox).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null) {
                        listener.onCheckChanged(images.get(position), isChecked);
                    }
                }
            });
        } else {
            holder.<CheckBox>findView(R.id.checkbox).setVisibility(View.GONE);
        }
        ViewGroup.LayoutParams params = holder.<ImageView>findView(R.id.iv).getLayoutParams();
        params.width = ScreenUtil.getScreenWidth() / 2;
        params.height = params.width;
        /**
         * 出现OOM，没有缓存设置
         * holder.<ImageView>findView(R.id.iv).setImageURI(Uri.parse(images.get(position).getUri()));
         */

        Glide.with(activity)
                .load(images.get(position).getUri())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.<ImageView>findView(R.id.iv));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addPic(ImageBean ib) {
        images.add(ib);
        notifyDataSetChanged();
    }
    public void resetData(ArrayList<ImageBean> list) {
        this.images = list;
        notifyDataSetChanged();
    }

    public ArrayList<ImageBean> getData() {
        return images;
    }

    public void setOnCheckBoxListener(OnCheckBoxListener listener){
        this.listener = listener;
    }
    public interface OnCheckBoxListener {
        void onCheckChanged(ImageBean imageBean, boolean isCheck);
    }
}
