package com.example.cosplan.data.Coplay.Events;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.Coplay.Cosplay;

@Entity(tableName = "CosplayEvent_table",foreignKeys = @ForeignKey(entity = Cosplay.class,parentColumns = "Id",childColumns = "CosplayId"))
public class Event {
    @ColumnInfo(name = "CosplayId")
    public int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayEventId")
    public int mCosplayEventId;

    @ColumnInfo(name = "CosplayEventName")
    public String mCosplayEventName;

    @ColumnInfo(name = "CosplayEventPlace")
    public String mCosplayEventPlace;

    @ColumnInfo(name = "CosplayEventDate")
    public String mCosplayEventDate;

    @ColumnInfo(name = "CosplayEventType")
    public String mCosplayEventType;

    public Event(){}

    public Event(int mCosplayId, int mCosplayEventId, String mCosplayEventName, String mCosplayEventPlace, String mCosplayEventDate, String mCosplayEventType) {
        this.mCosplayId = mCosplayId;
        this.mCosplayEventId = mCosplayEventId;
        this.mCosplayEventName = mCosplayEventName;
        this.mCosplayEventPlace = mCosplayEventPlace;
        this.mCosplayEventDate = mCosplayEventDate;
        this.mCosplayEventType = mCosplayEventType;
    }
}