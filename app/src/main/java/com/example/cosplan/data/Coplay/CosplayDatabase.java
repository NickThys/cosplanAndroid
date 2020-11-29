package com.example.cosplan.data.Coplay;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cosplan.data.Coplay.CheckList.CheckListPartDao;
import com.example.cosplan.data.Coplay.CheckList.ChecklistPart;
import com.example.cosplan.data.Coplay.Events.Event;
import com.example.cosplan.data.Coplay.Events.EventDao;
import com.example.cosplan.data.Coplay.Part.Part;
import com.example.cosplan.data.Coplay.Part.PartDao;
import com.example.cosplan.data.Coplay.RefImg.ReferenceImg;
import com.example.cosplan.data.Coplay.RefImg.ReferenceImgDao;
import com.example.cosplan.data.Coplay.ShoppingList.ShoppingListPart;
import com.example.cosplan.data.Coplay.ShoppingList.ShoppingListPartDao;
import com.example.cosplan.data.Coplay.WIPImg.WIPImg;
import com.example.cosplan.data.Coplay.WIPImg.WIPImgDao;
import com.example.cosplan.data.Coplay.Webshop.Webshop;
import com.example.cosplan.data.Coplay.Webshop.WebshopDao;

@TypeConverters(Converters.class)
@Database(entities = {Cosplay.class, Part.class, ReferenceImg.class, Webshop.class, ChecklistPart.class, ShoppingListPart.class, WIPImg.class,Event.class}, version =4,exportSchema = true)
public abstract class CosplayDatabase extends RoomDatabase {
    public abstract CosplayDao cosplayDao();

    public abstract PartDao partDao();

    public abstract ReferenceImgDao referenceImgDao();

    public abstract WebshopDao webshopDao();

    public abstract CheckListPartDao checkListPartDao();

    public abstract ShoppingListPartDao shoppingListPartDao();

    public abstract WIPImgDao wipImgDao();

    public abstract EventDao eventDao();

    private static CosplayDatabase INSTANCE;

    public static CosplayDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CosplayDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CosplayDatabase.class, "cosplayDatabase").addMigrations(MIGRATION_2_3,MIGRATION_3_4).build();
                }
            }
        }
        return INSTANCE;
    }
    static final Migration MIGRATION_2_3=new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CosplayPart_table ADD COLUMN CosplayPartNote TEXT");
        }
    };
    static final Migration MIGRATION_3_4=new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE cosplay_table ADD COLUMN CosplayCurrentBudget REAL");
        }
    };
}
