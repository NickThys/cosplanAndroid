package com.example.cosplan.data.cosplay;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "cosplay_table")
public class Cosplay implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    public int mCosplayId;
    @NonNull
    @ColumnInfo(name = "CosplayName")
    public String mCosplayName;
    @NonNull
    @ColumnInfo(name = "CosplayStartDate")
    public String mCosplayStartDate;
    @NonNull
    @ColumnInfo(name = "CosplayEndDate")
    public String mCosplayEndDate;
    @NonNull
    @ColumnInfo(name = "CosplayBudget")
    public double mCosplayBudget;
    @NonNull
    @ColumnInfo(name= "CosplayCurrentBudget")
    public double mCosplayRemainingBudget;
    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "CosplayIMG")
    public Bitmap mCosplayIMG;
    @ColumnInfo(name = "CosplayNote")
    public String mCosplayNote;
    @ColumnInfo(name = "NumberOfParts")
    public int mNumberOfParts;
    @ColumnInfo(name = "CosplayPercentage")
    public double mCosplayPercentage;

    public Cosplay(){}

    public Cosplay(int mCosplayId, @NonNull String mCosplayName, @NonNull String mCosplayStartDate, @NonNull String mCosplayEndDate, double mCosplayBudget, double mCosplayCurrentBudget, @NonNull Bitmap mCosplayIMG) {
        this.mCosplayId = mCosplayId;
        this.mCosplayName = mCosplayName;
        this.mCosplayStartDate = mCosplayStartDate;
        this.mCosplayEndDate = mCosplayEndDate;
        this.mCosplayBudget = mCosplayBudget;
        this.mCosplayRemainingBudget = mCosplayCurrentBudget;
        this.mCosplayIMG = mCosplayIMG;
    }

    public Cosplay(@NonNull int Id, @NonNull String Name, @NonNull String StartDate, @NonNull String EndDate, @NonNull double Budget, @NonNull Bitmap Img){
        this.mCosplayId = Id;
        this.mCosplayName =Name;
        this.mCosplayStartDate =StartDate;
        this.mCosplayEndDate = EndDate;
        this.mCosplayBudget = Budget;
        this.mCosplayIMG = Img;
    }
    public Cosplay(@NonNull int Id,@NonNull String Name,@NonNull String StartDate,@NonNull String EndDate,@NonNull double Budget,@NonNull Bitmap Img,String note){
        this.mCosplayId = Id;
        this.mCosplayName =Name;
        this.mCosplayStartDate =StartDate;
        this.mCosplayEndDate = EndDate;
        this.mCosplayBudget = Budget;
        this.mCosplayIMG = Img;
        this.mCosplayNote=note;
    }

    public Cosplay(int mCosplayId, @NonNull String mCosplayName, @NonNull String mCosplayStartDate, @NonNull String mCosplayEndDate, double mCosplayBudget, double mCosplayCurrentBudget, @NonNull Bitmap mCosplayIMG, String mCosplayNote) {
        this.mCosplayId = mCosplayId;
        this.mCosplayName = mCosplayName;
        this.mCosplayStartDate = mCosplayStartDate;
        this.mCosplayEndDate = mCosplayEndDate;
        this.mCosplayBudget = mCosplayBudget;
        this.mCosplayRemainingBudget = mCosplayCurrentBudget;
        this.mCosplayIMG = mCosplayIMG;
        this.mCosplayNote = mCosplayNote;
    }

    protected Cosplay(Parcel in) {
        mCosplayId = in.readInt();
        mCosplayName = in.readString();
        mCosplayStartDate = in.readString();
        mCosplayEndDate = in.readString();
        mCosplayBudget = in.readDouble();
        mCosplayIMG = in.readParcelable(Bitmap.class.getClassLoader());
        mCosplayNote=in.readString();
    }

    public static final Creator<Cosplay> CREATOR = new Creator<Cosplay>() {
        @Override
        public Cosplay createFromParcel(Parcel in) {
            return new Cosplay(in);
        }

        @Override
        public Cosplay[] newArray(int size) {
            return new Cosplay[size];
        }
    };

    public String getCosplayName() {
        return this.mCosplayName;
    }

    public String getCosplayStartDate() {
        return this.mCosplayStartDate;
    }

    public String getCosplayEndDate() {
        return this.mCosplayEndDate;
    }

    public double getCosplayBudget() {
        return this.mCosplayBudget;
    }

    public Bitmap getCosplayIMG() {
        return this.mCosplayIMG;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCosplayId);
        dest.writeString(mCosplayName);
        dest.writeString(mCosplayStartDate);
        dest.writeString(mCosplayEndDate);
        dest.writeDouble(mCosplayBudget);
        dest.writeParcelable(mCosplayIMG, flags);
    }
}
