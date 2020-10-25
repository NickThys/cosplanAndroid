package com.example.cosplan.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.VersionedParcelize;


@Entity(tableName="webshop_table")
public class Webshop implements Parcelable {
@PrimaryKey
    @NonNull
    @ColumnInfo(name="Name")
    public String mSiteName;
    @NonNull
    @ColumnInfo(name = "Link")
    public String mSiteLink;
    public Webshop(){}
    public Webshop(@NonNull String name,@NonNull String link){this.mSiteLink=link;this.mSiteName=name;}

    protected Webshop(Parcel in) {
        mSiteName = in.readString();
        mSiteLink = in.readString();
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

    public String getWebsiteLink(){return this.mSiteLink;}
    public String getWebsiteName(){return this.mSiteName;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSiteName);
        dest.writeString(mSiteLink);
    }
}