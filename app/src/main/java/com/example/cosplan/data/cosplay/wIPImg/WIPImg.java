package com.example.cosplan.data.cosplay.wIPImg;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.cosplay.Cosplay;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "CosplayWIPImg_table",
        foreignKeys = @ForeignKey(onDelete = ForeignKey.CASCADE, entity = Cosplay.class,
                parentColumns = "Id",
                childColumns = "CosplayId"))
public class WIPImg {
    @ColumnInfo(name = "CosplayId",index = true)
    public int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayWIPImgId")
    public int mCosplayWIPImgId;
    @ColumnInfo(name = "CosplayWIPImgImage")
    public String mCosplayWIPImgImage;

    public WIPImg(int mCosplayId, int mCosplayWIPImgId, @NotNull String mCosplayWIPImgImage) {
        this.mCosplayId = mCosplayId;
        this.mCosplayWIPImgId = mCosplayWIPImgId;
        this.mCosplayWIPImgImage = mCosplayWIPImgImage;
    }

    @Ignore
    public WIPImg() {
    }
}
