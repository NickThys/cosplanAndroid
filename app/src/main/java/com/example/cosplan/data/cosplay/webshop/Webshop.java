package com.example.cosplan.data.cosplay.webshop;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.cosplay.Cosplay;

@Entity(tableName = "CosplayWebshop_table",foreignKeys = @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Cosplay.class,
        parentColumns = "Id",
        childColumns = "CosplayId"))
public class Webshop implements Parcelable {
    @ColumnInfo(name = "CosplayId",index = true)
    public int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayWebshopId")
    public int mCosplayWebshopId;

    @ColumnInfo(name = "CosplayWebshopName")
    public String mCosplayWebshopName;

    @ColumnInfo(name = "CosplayWebshopLink")
    public String mCosplayWebshopLink;

    @Ignore
    public Webshop(){}

    public Webshop(int mCosplayId,int mCosplayWebshopId, @NonNull String mCosplayWebshopName,@NonNull String mCosplayWebshopLink) {
        this.mCosplayId = mCosplayId;
        this.mCosplayWebshopId = mCosplayWebshopId;
        this.mCosplayWebshopName = mCosplayWebshopName;
        this.mCosplayWebshopLink = mCosplayWebshopLink;
    }

    protected Webshop(Parcel in) {
        mCosplayId = in.readInt();
        mCosplayWebshopId = in.readInt();
        mCosplayWebshopName = in.readString();
        mCosplayWebshopLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCosplayId);
        dest.writeInt(mCosplayWebshopId);
        dest.writeString(mCosplayWebshopName);
        dest.writeString(mCosplayWebshopLink);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
