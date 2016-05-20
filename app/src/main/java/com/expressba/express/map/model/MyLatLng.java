package com.expressba.express.map.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by songchao on 16/5/20.
 */
public class MyLatLng implements Parcelable{
    private static final String a = LatLng.class.getSimpleName();
    public final double latitude;
    public final double longitude;
    public final double latitudeE6;
    public final double longitudeE6;
    public final String create_time;

    public MyLatLng(double var1, double var3,String create_time) {
        double var5 = var1 * 1000000.0D;
        double var7 = var3 * 1000000.0D;
        this.latitudeE6 = var5;
        this.longitudeE6 = var7;
        this.latitude = var5 / 1000000.0D;
        this.longitude = var7 / 1000000.0D;
        this.create_time = create_time;
    }

    protected MyLatLng(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        latitudeE6 = in.readDouble();
        longitudeE6 = in.readDouble();
        create_time = in.readString();
    }

    public static final Creator<MyLatLng> CREATOR = new Creator<MyLatLng>() {
        @Override
        public MyLatLng createFromParcel(Parcel in) {
            return new MyLatLng(in);
        }

        @Override
        public MyLatLng[] newArray(int size) {
            return new MyLatLng[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(latitudeE6);
        dest.writeDouble(longitudeE6);
        dest.writeString(create_time);
    }
}
