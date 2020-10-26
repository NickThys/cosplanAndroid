package com.example.cosplan.data.Convention;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Convention.class,version = 1,exportSchema = false)
abstract class ConventionDatabase extends RoomDatabase {
    public abstract ConventionDao conventionDao();
    private static ConventionDatabase INSTANCE;
    public static ConventionDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (ConventionDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),ConventionDatabase.class,"conventionDatabase").build();

                }
            }
        }
        return INSTANCE;
    }
}
