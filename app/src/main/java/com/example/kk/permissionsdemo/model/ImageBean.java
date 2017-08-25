package com.example.kk.permissionsdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kk on 2017/8/24.
 */

public class ImageBean implements Parcelable {

    private String uri;

    private boolean check;


    public ImageBean(String path) {
        this.uri = path;
        this.check = false;
    }
    public ImageBean(String path, boolean check) {
        this.uri = path;
        this.check = check;
    }

    protected ImageBean(Parcel in) {
        uri = in.readString();
        check = in.readByte() != 0;
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeByte((byte) (check ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageBean imageBean = (ImageBean) o;

        return uri != null ? uri.equals(imageBean.uri) : imageBean.uri == null;

    }
}
