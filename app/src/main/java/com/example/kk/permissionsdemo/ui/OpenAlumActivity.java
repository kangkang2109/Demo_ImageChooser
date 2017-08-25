package com.example.kk.permissionsdemo.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.kk.permissionsdemo.model.ImageBean;
import com.example.kk.permissionsdemo.R;
import com.example.kk.permissionsdemo.recyclerview.ShowPicAdapter;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

/**
 * Created by kk on 2017/8/24.
 */

public class OpenAlumActivity extends BaseActivity {

    public static final String TAG = "Look";

    @BindView(R.id.sure)
    Button bt_choice;

    @BindView(R.id.rv)
    RecyclerView rv_pic;

    private ArrayList<ImageBean> checkedList;

    private ShowPicAdapter adapter;

    private android.os.Handler mHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.addPic((ImageBean) msg.getData().getParcelable("Image"));
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public int initLayoutId() {
        return R.layout.activity_look_through;
    }

    @Override
    public void initData(Bundle saveInstanceState) {
        checkedList = getIntent().getParcelableArrayListExtra("list");
        final ContentResolver cr = getContentResolver();
        final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        final String selection = "(("+MIME_TYPE+"=?)or("+MIME_TYPE+"=?))";
        final String [] selectionArgs = new String[]{"image/jpeg","image/png"};
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = cr.query(uri, null,
                        selection,selectionArgs,null);
                while (cursor != null && cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(DATA));
                    Log.e(TAG, "run: " + path);
                    ImageBean ib = new ImageBean(Uri.fromFile(new File(path)).toString());
                    Log.e(TAG, "run: " + ib.getUri() );
                    if (checkedList.contains(ib)) {
                        Log.e(TAG, "run: contain!!!!" );
                        ib.setCheck(true);
                    }
                    Log.e(TAG, "run: check " + ib.getCheck() );
                    Message m = Message.obtain();
                    Bundle b = new Bundle();
                    b.putParcelable("Image", ib);
                    m.setData(b);
                    mHandler.sendMessage(m);
                }
            }
        }).start();
    }


    @Override
    public void initView() {
        rv_pic.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ShowPicAdapter(this);
        rv_pic.setAdapter(adapter);
        adapter.setOnCheckBoxListener(new ShowPicAdapter.OnCheckBoxListener() {
            @Override
            public void onCheckChanged(ImageBean imageBean, boolean isCheck) {
                if (checkedList.contains(imageBean) && !isCheck) {
                    checkedList.remove(imageBean);
                } else if (!checkedList.contains(imageBean) && isCheck) {
                    imageBean.setCheck(true);
                    checkedList.add(imageBean);
                }
            }
        });
    }

    @OnClick(R.id.sure)
    void sureClick() {
        Intent i = new Intent();
        i.putParcelableArrayListExtra("CheckList", checkedList);
        setResult(0, i);
        finish();
    }
}
