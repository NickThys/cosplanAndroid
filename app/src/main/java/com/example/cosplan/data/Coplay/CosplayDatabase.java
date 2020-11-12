package com.example.cosplan.data.Coplay;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cosplan.data.Coplay.Part.Part;
import com.example.cosplan.data.Coplay.Part.PartDao;

@TypeConverters(Converters.class)
@Database(entities = {Part.class, Cosplay.class},version = 1,exportSchema = false)
public abstract class CosplayDatabase extends RoomDatabase {
    public abstract CosplayDao cosplayDao();
    public abstract PartDao partDao();
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
