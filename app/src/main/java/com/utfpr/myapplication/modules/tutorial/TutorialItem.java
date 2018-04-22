package com.utfpr.myapplication.modules.tutorial;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lispa on 30/03/2018.
 */

public class TutorialItem  implements Parcelable{

    private String iconUrl;
    private String title;
    private String description;
    private boolean isLastItem;


    public String getIconUrl() {
        return iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLastItem() {
        return isLastItem;
    }

    public void setAsLastItem(){
        isLastItem = true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iconUrl);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeByte(this.isLastItem ? (byte) 1 : (byte) 0);
    }

    public TutorialItem() {
    }

    protected TutorialItem(Parcel in) {
        this.iconUrl = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.isLastItem = in.readByte() != 0;
    }

    public static final Creator<TutorialItem> CREATOR = new Creator<TutorialItem>() {
        @Override
        public TutorialItem createFromParcel(Parcel source) {
            return new TutorialItem(source);
        }

        @Override
        public TutorialItem[] newArray(int size) {
            return new TutorialItem[size];
        }
    };
}
