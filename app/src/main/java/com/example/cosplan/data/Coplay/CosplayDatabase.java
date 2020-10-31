package com.example.cosplan.data.Coplay;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(Converters.class)
@Database(entities = Cosplay.class,version = 1,exportSchema = false)
abstract class CosplayDatabase extends RoomDatabase {
    public abstract CosplayDao cosplayDao();
    private static CosplayDatabase INSTANCE;
    public static CosplayDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (CosplayDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),CosplayDatabase.class,"cosplayDatabase").build();
                }
            }
        }
        return INSTANCE;
    }
}
