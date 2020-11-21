package com.example.cosplan.data.Coplay.WIPImg;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.Coplay.Cosplay;

@Entity(tableName = "CosplayWIPImg_table",
        foreignKeys = @ForeignKey(entity = Cosplay.class,
                parentColumns = "Id",
                childColumns = "CosplayId"))
public class WIPImg {
    @ColumnInfo(name = "CosplayId")
    public int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayWIPImgId")
    public int mCosplayWIPImgIg;

    @ColumnInfo(name = "CosplayWIPImgImage")
    public Bitmap mCosplayWIPImgImage;

    public WIPImg(int mCosplayId, int mCosplayWIPImgIg, Bitmap mCosplayWIPImgImage) {
        this.mCosplayId = mCosplayId;
        this.mCosplayWIPImgIg = mCosplayWIPImgIg;
        this.mCosplayWIPImgImage = mCosplayWIPImgImage;
    }

    public WIPImg() {
    }
}
