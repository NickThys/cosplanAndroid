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
    public String mConventionName;
    @ColumnInfo(name = "BeginDate")
    public String mConventionBeginDate;
    @ColumnInfo(name = "EndDate")
    public String mConventionEndDate;
    @ColumnInfo(name = "Country")
    public String mConventionCountry;
    @ColumnInfo(name = "Place")
    public String mConventionPlace;
}
