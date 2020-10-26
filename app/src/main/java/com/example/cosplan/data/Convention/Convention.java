package com.example.cosplan.data.Convention;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.type.Date;


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
    public String mConCountry;
    @NonNull
    @ColumnInfo(name = "Place")
    public String mConPlace;

    public String getConventionName(){return this.mConName;}
    public Date getConventionBeginDate(){return this.mConBeginDate;}
    public Date getConventionEndDate(){return this.mConEndDate;}
    public String getConventionCountry(){return this.mConCountry;}
    public String getConventionPlace(){return this.mConPlace;}

}
