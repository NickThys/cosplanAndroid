package com.example.cosplan.data.Cosplay_Part;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cosplan.data.Coplay.Converters;
import com.example.cosplan.data.Coplay.Cosplay;

@TypeConverters(Converters.class)
@Database(entities = {Part.class, Cosplay.class},version = 1,exportSchema = false)
abstract class PartDatabase extends RoomDatabase {
    public abstract PartDao partDao();
    private static PartDatabase INSTANCE;
    public static PartDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (PartDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), PartDatabase.class,"partDatabase").build();
                }
            }
        }
        return INSTANCE;
    }
}
