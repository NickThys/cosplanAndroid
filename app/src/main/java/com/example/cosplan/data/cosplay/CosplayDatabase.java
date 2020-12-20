package com.example.cosplan.data.cosplay;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cosplan.data.cosplay.checkList.CheckListPartDao;
import com.example.cosplan.data.cosplay.checkList.ChecklistPart;
import com.example.cosplan.data.cosplay.events.Event;
import com.example.cosplan.data.cosplay.events.EventDao;
import com.example.cosplan.data.cosplay.part.Part;
import com.example.cosplan.data.cosplay.part.PartDao;
import com.example.cosplan.data.cosplay.refImg.ReferenceImg;
import com.example.cosplan.data.cosplay.refImg.ReferenceImgDao;
import com.example.cosplan.data.cosplay.shoppingList.ShoppingListPart;
import com.example.cosplan.data.cosplay.shoppingList.ShoppingListPartDao;
import com.example.cosplan.data.cosplay.wIPImg.WIPImg;
import com.example.cosplan.data.cosplay.wIPImg.WIPImgDao;
import com.example.cosplan.data.cosplay.webshop.Webshop;
import com.example.cosplan.data.cosplay.webshop.WebshopDao;

@TypeConverters(Converters.class)
@Database(entities = {Cosplay.class, Part.class, ReferenceImg.class, Webshop.class, ChecklistPart.class, ShoppingListPart.class, WIPImg.class, Event.class}, version = 3)
public abstract class CosplayDatabase extends RoomDatabase {
    public abstract CosplayDao mCosplayDao();

    public abstract PartDao mPartDao();

    public abstract ReferenceImgDao mReferenceImgDao();

    public abstract WebshopDao mWebshopDao();

    public abstract CheckListPartDao mCheckListPartDao();

    public abstract ShoppingListPartDao mShoppingListPartDao();

    public abstract WIPImgDao mWipImgDao();

    public abstract EventDao mEventDao();

    private static CosplayDatabase INSTANCE;

    public static CosplayDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CosplayDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CosplayDatabase.class, "cosplayDatabase").addMigrations(MIGRATION_1_2,MIGRATION_2_3).build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE INDEX index_checklist_part ON  CosplayChecklist_table(CosplayId)");
            database.execSQL("CREATE INDEX index_event ON  CosplayEvent_table(CosplayId)");
            database.execSQL("CREATE INDEX index_part ON  CosplayPart_table(CosplayId)");
            database.execSQL("CREATE INDEX index_reference_img ON  CosplayRefImg_table(CosplayId)");
            database.execSQL("CREATE INDEX index_shoppinglist_part ON  CosplayShoppingList_table(CosplayId)");
            database.execSQL("CREATE INDEX index_webshop ON  CosplayWebshop_table(CosplayId)");
            database.execSQL("CREATE INDEX index_wip_img ON  CosplayWIPImg_table(CosplayId)");

        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //region wip img
            database.execSQL("DROP TABlE CosplayWIPImg_table");
            database.execSQL("CREATE TABLE IF NOT EXISTS `CosplayWIPImg_table` (`CosplayId` INTEGER NOT NULL, `CosplayWIPImgId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `CosplayWIPImgImage` TEXT, FOREIGN KEY(`CosplayId`) REFERENCES `cosplay_table`(`Id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
            database.execSQL("CREATE INDEX index_wip_img ON  CosplayWIPImg_table(CosplayId)");

            //endregion
            //region ref img
            database.execSQL("DROP TABlE CosplayRefImg_table");

            database.execSQL("CREATE TABLE IF NOT EXISTS `CosplayRefImg_table` (`CosplayId` INTEGER NOT NULL, `CosplayRefImgId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `CosplayRefImgImage` TEXT, FOREIGN KEY(`CosplayId`) REFERENCES `cosplay_table`(`Id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
            database.execSQL("CREATE INDEX index_reference_img ON  CosplayRefImg_table(CosplayId)");

            //endregion
            //region part
            database.execSQL("CREATE TABLE IF NOT EXISTS `CosplayPart_table_new` (`CosplayId` INTEGER NOT NULL, `CosplayPartId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `CosplayPartName` TEXT NOT NULL, `CosplayPartBuyMake` TEXT NOT NULL, `CosplayPartLink` TEXT, `CosplayPartCost` REAL NOT NULL, `CosplayPartStatus` TEXT, `CosplayPartEndDate` TEXT, `CosplayPartImage` TEXT, `CosplayPartNote` TEXT, FOREIGN KEY(`CosplayId`) REFERENCES `cosplay_table`(`Id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
            database.execSQL("CREATE INDEX index_part ON  CosplayPart_table_new(CosplayId)");
            database.execSQL("UPDATE CosplayPart_table SET CosplayPartImage=' ' ");
            database.execSQL("INSERT INTO CosplayPart_table_new(CosplayId,CosplayPartName,CosplayPartBuyMake,CosplayPartLink,CosplayPartCost,CosplayPartStatus,CosplayPartEndDate,CosplayPartImage,CosplayPartNote)" +
                    "SELECT CosplayId,CosplayPartName,CosplayPartBuyMake,CosplayPartLink,CosplayPartCost,CosplayPartStatus,CosplayPartEndDate,CosplayPartImage,CosplayPartNote FROM CosplayPart_table");
            //endregion
            //region cosplay
            database.execSQL("CREATE TABLE IF NOT EXISTS `cosplay_table_new` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `CosplayName` TEXT NOT NULL, `CosplayStartDate` TEXT NOT NULL, `CosplayEndDate` TEXT NOT NULL, `CosplayBudget` REAL NOT NULL, `CosplayCurrentBudget` REAL NOT NULL, `CosplayIMG` TEXT NOT NULL, `CosplayNote` TEXT, `NumberOfParts` INTEGER NOT NULL, `CosplayPercentage` REAL NOT NULL)");
            database.execSQL("UPDATE cosplay_table SET CosplayIMG=' ' ");

            database.execSQL("INSERT INTO cosplay_table_new(CosplayName,CosplayStartDate,CosplayEndDate,CosplayBudget,CosplayCurrentBudget,CosplayIMG,CosplayNote,NumberOfParts,CosplayPercentage)" +
                    "SELECT CosplayName,CosplayStartDate,CosplayEndDate,CosplayBudget,CosplayCurrentBudget,CosplayIMG,CosplayNote,NumberOfParts,CosplayPercentage FROM cosplay_table");
            //endregion
            //region Drop table
            database.execSQL("DROP TABLE cosplay_table");
            database.execSQL("DROP TABLE CosplayPart_table");


            //endregion
            //region rename
            database.execSQL("ALTER TABLE cosplay_table_new RENAME TO cosplay_table");
            database.execSQL("ALTER TABLE CosplayPart_table_new RENAME TO CosplayPart_table");

            //endregion


        }
    };
}
