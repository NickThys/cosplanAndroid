package com.example.cosplan.data.Convention;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.type.Date;

enum Country{Belgium,Netherlands}
@Entity(tableName = "convention_table")
public class Convention {
    @NonNull
    @ColumnInfo(name = "Name")
    public String mConName;
    @NonNull
    @ColumnInfo(name = "BeginDate")
    public Date mConBeginDate;
    @NonNull
    @ColumnInfo(name = "EndDate")
    public Date mConEndDate;
    @NonNull
    @ColumnInfo(name = "Country")
    public Country mConCountry;
    @NonNull
    @ColumnInfo(name = "Place")
    public String mConPlace;

    public String getConventionName(){return this.mConName;}
    public Date getConventionBeginDate(){return this.mConBeginDate;}
    public Date getConventionEndDate(){return this.mConEndDate;}
    public Country getConventionCountry(){return this.mConCountry;}
    public String getConventionPlace(){return this.mConPlace;}

}
