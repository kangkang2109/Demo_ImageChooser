package com.example.kk.permissionsdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.kk.permissionsdemo.utils.StatusBarUtils;

import java.util.zip.Inflater;

import butterknife.ButterKnife;

/**
 * Created by kk on 2017/8/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int initLayoutId();

    public abstract void initData(Bundle saveInstanceState);

    public abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarTranslucent(this);
        initData(savedInstanceState);
        initView();
    }

}
