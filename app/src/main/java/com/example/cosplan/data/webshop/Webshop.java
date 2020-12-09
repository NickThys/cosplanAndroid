package com.example.cosplan.data.webshop;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "webshop_table")
public class Webshop implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int mWebshopId;

    @NonNull
    @ColumnInfo(name = "Name")
    public String mWebshopName;
    @NonNull
    @ColumnInfo(name = "Link")
    public String mWebshopLink;

    public Webshop() {
    }

    public Webshop(int mId, @NonNull String mName, @NonNull String mLink) {
        this.mWebshopId = mId;
        this.mWebshopLink = mLink;
        this.mWebshopName = mName;
    }


    protected Webshop(Parcel in) {
        mWebshopId = in.readInt();
        mWebshopName = in.readString();
        mWebshopLink = in.readString();
    }

    public static final Creator<Webshop> CREATOR = new Creator<Webshop>() {
        @Override
        public Webshop createFromParcel(Parcel in) {
            return new Webshop(in);
        }

        @Override
        public Webshop[] newArray(int size) {
            return new Webshop[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mWebshopId);
        dest.writeString(mWebshopName);
        dest.writeString(mWebshopLink);
    }
}