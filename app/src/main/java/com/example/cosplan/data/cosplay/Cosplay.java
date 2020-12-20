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
    @ColumnInfo(name = "CosplayBudget")
    public double mCosplayBudget;
    @ColumnInfo(name = "CosplayCurrentBudget")
    public double mCosplayRemainingBudget;

    public Cosplay(int mCosplayId, @NonNull String mCosplayName, @NonNull String mCosplayStartDate, @NonNull String mCosplayEndDate, double mCosplayBudget, double mCosplayRemainingBudget, @NonNull String mCosplayIMG, String mCosplayNote, int mNumberOfParts, double mCosplayPercentage) {
        this.mCosplayId = mCosplayId;
        this.mCosplayName = mCosplayName;
        this.mCosplayStartDate = mCosplayStartDate;
        this.mCosplayEndDate = mCosplayEndDate;
        this.mCosplayBudget = mCosplayBudget;
        this.mCosplayRemainingBudget = mCosplayRemainingBudget;
        this.mCosplayIMG = mCosplayIMG;
        this.mCosplayNote = mCosplayNote;
        this.mNumberOfParts = mNumberOfParts;
        this.mCosplayPercentage = mCosplayPercentage;
    }

    @NonNull
    @ColumnInfo( name = "CosplayIMG")
    public String  mCosplayIMG;
    @ColumnInfo(name = "CosplayNote")
    public String mCosplayNote;
    @ColumnInfo(name = "NumberOfParts")
    public int mNumberOfParts;
    @ColumnInfo(name = "CosplayPercentage")
    public double mCosplayPercentage;

    public Cosplay() {
    }

    public Cosplay(int mCosplayId, @NonNull String mCosplayName, @NonNull String mCosplayStartDate, @NonNull String mCosplayEndDate, double mCosplayBudget, double mCosplayCurrentBudget, @NonNull String mCosplayIMG) {
        this.mCosplayId = mCosplayId;
        this.mCosplayName = mCosplayName;
        this.mCosplayStartDate = mCosplayStartDate;
        this.mCosplayEndDate = mCosplayEndDate;
        this.mCosplayBudget = mCosplayBudget;
        this.mCosplayRemainingBudget = mCosplayCurrentBudget;
        this.mCosplayIMG = mCosplayIMG;
    }

// --Commented out by Inspection START (7/12/2020 22:41):
//    public Cosplay(int Id, @NonNull String Name, @NonNull String StartDate, @NonNull String EndDate, double Budget, @NonNull Bitmap Img) {
//        this.mCosplayId = Id;
//        this.mCosplayName = Name;
//        this.mCosplayStartDate = StartDate;
//        this.mCosplayEndDate = EndDate;
//        this.mCosplayBudget = Budget;
//        this.mCosplayIMG = Img;
//    }
// --Commented out by Inspection STOP (7/12/2020 22:41)

    public Cosplay(int Id, @NonNull String Name, @NonNull String StartDate, @NonNull String EndDate, double Budget, @NonNull String Img, String note) {
        this.mCosplayId = Id;
        this.mCosplayName = Name;
        this.mCosplayStartDate = StartDate;
        this.mCosplayEndDate = EndDate;
        this.mCosplayBudget = Budget;
        this.mCosplayIMG = Img;
        this.mCosplayNote = note;
    }

    public Cosplay(int mCosplayId, @NonNull String mCosplayName, @NonNull String mCosplayStartDate, @NonNull String mCosplayEndDate, double mCosplayBudget, double mCosplayCurrentBudget, @NonNull String mCosplayIMG, String mCosplayNote) {
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
        mCosplayRemainingBudget = in.readDouble();
        mCosplayIMG = in.readString();
        mCosplayNote = in.readString();
        mNumberOfParts = in.readInt();
        mCosplayPercentage = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCosplayId);
        dest.writeString(mCosplayName);
        dest.writeString(mCosplayStartDate);
        dest.writeString(mCosplayEndDate);
        dest.writeDouble(mCosplayBudget);
        dest.writeDouble(mCosplayRemainingBudget);
        dest.writeString(mCosplayIMG);
        dest.writeString(mCosplayNote);
        dest.writeInt(mNumberOfParts);
        dest.writeDouble(mCosplayPercentage);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
