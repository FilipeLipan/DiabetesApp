package com.utfpr.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class History implements Parcelable {
    private Date createdAt;
    private String result;
    private String type;
    private ArrayList<HistoryChartEntry> entries;

    public Date getCreatedAt() {
        return createdAt;
    }

    public History setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getResult() {
        return result;
    }

    public History setResult(String result) {
        this.result = result;
        return this;
    }

    public String getType() {
        return type;
    }

    public History setType(String type) {
        this.type = type;
        return this;
    }

    public ArrayList<HistoryChartEntry> getEntries() {
        return entries;
    }

    public History setEntries(ArrayList<HistoryChartEntry> entries) {
        this.entries = entries;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeString(this.result);
        dest.writeString(this.type);
        dest.writeList(this.entries);
    }

    public History() {
    }

    protected History(Parcel in) {
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        this.result = in.readString();
        this.type = in.readString();
        this.entries = new ArrayList<HistoryChartEntry>();
        in.readList(this.entries, HistoryChartEntry.class.getClassLoader());
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel source) {
            return new History(source);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
