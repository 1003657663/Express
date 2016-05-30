package com.expressba.express.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by songchao on 16/5/30.
 */
public class FromAndTo implements Parcelable {

    public FromAndTo(){

    }

    String from;
    String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    protected FromAndTo(Parcel in) {
        from = in.readString();
        to = in.readString();
    }

    public static final Creator<FromAndTo> CREATOR = new Creator<FromAndTo>() {
        @Override
        public FromAndTo createFromParcel(Parcel in) {
            return new FromAndTo(in);
        }

        @Override
        public FromAndTo[] newArray(int size) {
            return new FromAndTo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
    }
}
