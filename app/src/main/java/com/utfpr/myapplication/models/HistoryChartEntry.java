package com.utfpr.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryChartEntry implements Parcelable {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public HistoryChartEntry setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public HistoryChartEntry setY(int y) {
        this.y = y;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.x);
        dest.writeInt(this.y);
    }

    public HistoryChartEntry() {
    }

    protected HistoryChartEntry(Parcel in) {
        this.x = in.readInt();
        this.y = in.readInt();
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
