package com.example.ofw.ipcdemo.parcelable;

import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ofw on 2018/2/5.
 */

public class ParcelableBean implements Parcelable {

    public int id;
    public String name;
    public boolean isMale;

    public ParcelableBean(int id, String name, boolean isMale) {
        super();
        this.id = id;
        this.name = name;
        this.isMale = isMale;
    }

    protected ParcelableBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.isMale = in.readInt() == 1;
//        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel in) {
            return new ParcelableBean(in);
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(isMale ? 1 : 0);
//        dest.writeParcelable();
    }
}
