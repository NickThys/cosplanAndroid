package com.example.cosplan.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.VersionedParcelize;


@Entity(tableName="webshop_table")
public class Webshop  {
@PrimaryKey
    @NonNull
    @ColumnInfo(name="Name")
    public String mSiteName;
    @NonNull
    @ColumnInfo(name = "Link")
    public String mSiteLink;
    public Webshop(){}
    public Webshop(@NonNull String name,@NonNull String link){this.mSiteLink=link;this.mSiteName=name;}

    public String getWebsiteLink(){return this.mSiteLink;}
    public String getWebsiteName(){return this.mSiteName;}


}