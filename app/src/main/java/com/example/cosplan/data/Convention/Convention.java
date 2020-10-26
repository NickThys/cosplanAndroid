package com.example.cosplan.data.Convention;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "convention_table")
public class Convention {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Name")
    public String mConName;
    @NonNull
    @ColumnInfo(name = "BeginDate")
    public String  mConBeginDate;
    @NonNull
    @ColumnInfo(name = "EndDate")
    public String  mConEndDate;
    @NonNull
    @ColumnInfo(name = "Country")
    public String mConCountry;
    @NonNull
    @ColumnInfo(name = "Place")
    public String mConPlace;

    public String getConventionName(){return this.mConName;}
    public String getConventionBeginDate(){return this.mConBeginDate;}
    public String  getConventionEndDate(){return this.mConEndDate;}
    public String getConventionCountry(){return this.mConCountry;}
    public String getConventionPlace(){return this.mConPlace;}

}
