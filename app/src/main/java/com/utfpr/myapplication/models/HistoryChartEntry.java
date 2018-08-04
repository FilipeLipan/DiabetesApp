package com.utfpr.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryChartEntry implements Parcelable {
    public Long x;
    public Long y;

    public HistoryChartEntry(){

    }

    public HistoryChartEntry(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public HistoryChartEntry setX(Long x) {
        this.x = x;
        return this;
    }

    public Long getY() {
        return y;
    }

    public HistoryChartEntry setY(Long y) {
        this.y = y;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.x);
        dest.writeLong(this.y);
    }


    protected HistoryChartEntry(Parcel in) {
        this.x = in.readLong();
        this.y = in.readLong();
    }

    public static final Parcelable.Creator<HistoryChartEntry> CREATOR = new Parcelable.Creator<HistoryChartEntry>() {
        @Override
        public HistoryChartEntry createFromParcel(Parcel source) {
            return new HistoryChartEntry(source);
        }

        @Override
        public HistoryChartEntry[] newArray(int size) {
            return new HistoryChartEntry[size];
        }
    };
}
