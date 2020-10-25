package com.example.cosplan.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="webshop_table")
public class Webshop {
@PrimaryKey
    @NonNull
    @ColumnInfo(name="Name")
    private String mSiteName;
@NonNull
    @ColumnInfo(name = "Link")
    private String mSiteLink;
    public Webshop(@NonNull String name,@NonNull String link){this.mSiteLink=link;this.mSiteName=name;}
    public String getWebsiteLink(){return this.mSiteLink;}
    public String getWebsiteName(){return this.mSiteName;}
}
