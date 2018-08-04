package com.utfpr.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class History implements Parcelable {
    public String createdAt;
    public String result;
    public String type;
    public ArrayList<HistoryChartEntry> entries;

    public History(){

    }

    public History(String createdAt, String result, String type, ArrayList<HistoryChartEntry> entries) {
        this.createdAt = createdAt;
        this.result = result;
        this.type = type;
        this.entries = entries;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public History setCreatedAt(String createdAt) {
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
        dest.writeString(this.createdAt);
        dest.writeString(this.result);
        dest.writeString(this.type);
        dest.writeList(this.entries);
    }

    protected History(Parcel in) {
        long tmpCreatedAt = in.readLong();
        this.createdAt = in.readString();
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
