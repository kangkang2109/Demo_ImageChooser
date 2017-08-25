package com.example.kk.permissionsdemo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kk.permissionsdemo.model.ImageBean;
import com.example.kk.permissionsdemo.R;
import com.example.kk.permissionsdemo.recyclerview.ShowPicAdapter;
import com.example.kk.permissionsdemo.utils.StatusBarUtils;

import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.EXTRA_OUTPUT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.camera)
    Button bt_camera;

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.album)
    Button bt_album;

    @BindView(R.id.drag)
    Button bt_drag;

    private ShowPicAdapter adapter;

    public static final String TAG = "Main";

    private String mPhotoUri;

    public static final int CHOOSEPIC = 200;

    public static final int PHOTO = 100;

    public static final int PERMISSION_CEMARE_WRITE = 300;

    public static final int PERMISSION_READ = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtils.setStatusBarTranslucent(this);
        adapter = new ShowPicAdapter(this);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(adapter);
    }

    @OnClick(R.id.camera)
    public void cameraClick(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CEMARE_WRITE);
            return;
        }
        openCamera();
    }

    @OnClick(R.id.album)
    void alumClick() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return;
        }
        openAlum();
    }

    @OnClick(R.id.drag)
    void dragClick() {
        Intent i = new Intent(this, DragDemoActivity.class);
        startActivity(i);
    }

    public void openAlum() {
        Intent intent = new Intent(this, OpenAlumActivity.class);
        intent.putParcelableArrayListExtra("list", adapter.getData());
        startActivityForResult(intent, CHOOSEPIC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CEMARE_WRITE && grantResults.length != 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "permission deny!", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == PERMISSION_READ && grantResults.length != 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlum();
            } else {
                Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DemoPic");
        if (!dir.exists()) {
            dir.mkdir();
        }
        mPhotoUri = dir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png";
        File f = new File(mPhotoUri);
        Uri uri = Uri.fromFile(f);
        i.putExtra(EXTRA_OUTPUT, uri);
        Log.e(TAG, "takePhoto: " + uri.toString() );
        Log.e(TAG, "takePhoto: " + uri.getPath() );
        Log.e(TAG, "takePhoto: " + uri.getEncodedPath());
        startActivityForResult(i, PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO && mPhotoUri != null && new File(mPhotoUri).exists()) {
            adapter.addPic(new ImageBean(Uri.fromFile(new File(mPhotoUri)).toString(), true));
        } else if (requestCode == CHOOSEPIC && data != null) {
            ArrayList<ImageBean> images = data.getParcelableArrayListExtra("CheckList");
            adapter.resetData(images);
        }
    }

}
