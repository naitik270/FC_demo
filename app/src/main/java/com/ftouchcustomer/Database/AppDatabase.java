package com.ftouchcustomer.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Database.Interface_Dao.OrderDetailDao;
import com.ftouchcustomer.Global.ClsGlobal;


@Database(entities = {ClsOrderDetailsEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})

public abstract class AppDatabase extends RoomDatabase {

    public abstract OrderDetailDao getOrderDetailDao();
//
    private static AppDatabase INSTANCE;
//
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context) {
        String dbname = ClsGlobal.DataBaseName;
        return Room.databaseBuilder(context, AppDatabase.class, dbname)
//                .addMigrations(MIGRATION_1_2)
//                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
    }
    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {


            database.execSQL("DROP TABLE IF EXISTS `Franchise`;");

            String qry = "CREATE TABLE `Franchise` (`FranchiseID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`FranchiseCode` TEXT,`FranchiseName` TEXT)";

            database.execSQL(qry);

//            SupportSQLiteStatement statement = database.compileStatement(qry);

            Log.e("--Table--", "--Franchise: " + qry);
//            Log.e("--Table--", "--FranchiseStatement:" + statement.executeUpdateDelete());


            database.execSQL("DROP TABLE IF EXISTS `FranchiseVisitMaster`;");



            String abc = "CREATE TABLE IF NOT EXISTS `FranchiseVisitMaster` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`FranchiseVisitID` INTEGER,`FranchiseName` TEXT," +
                    "`MobileNumber` TEXT,`BusinessName` TEXT," +
                    "`StateName` TEXT,`CityName` TEXT," +
                    "`BusinessAddress` TEXT,`ContactPerson` TEXT," +
                    "`ContactPersonNumber` TEXT,`EmailAddress` TEXT," +
                    "`BusinessEntity` TEXT,`BusinessType` TEXT," +
                    "`DurationOfExistingBusiness` TEXT,`TotalEmployeeStrengthStr` TEXT," +
                    "`ScopeOfBusiness` TEXT,`ConnectedRetailersStr` TEXT," +
                    "`DispositionIDStr` TEXT,`DispositionCode` TEXT," +
                    "`NextMeetingDateStr` TEXT,`NextMeetingTime` TEXT," +
                    "`NextMeetingRemark` TEXT,`VisitDuration` TEXT," +
                    "`LeadRemark` TEXT,`FeedbackSubmitDateStr` TEXT," +
                    "`FeedbackSubmitTime` TEXT,`EntryByUserCode` TEXT," +
                    "`InterestRatingStr` TEXT,`CapturedAddress` TEXT," +
                    "`LatituteInfo` TEXT,`LongitudeInfo` TEXT," +
                    "`EntryFromDeviceInformation` TEXT,`DeviceMACAddress` TEXT," +
                    "`DistributedEmployeeCode` TEXT,`VisitDateStr` TEXT," +
                    "`ContactWithType` TEXT,`PersonNameIfOther` TEXT," +
                    "`PersonContactNumberIfOther` TEXT,`FileName` TEXT," +
                    "`FileUploadRemark` TEXT," +
                    "`NextMeetingDate` INTEGER,`EntryDate` INTEGER)";


            database.execSQL(abc);

//            SupportSQLiteStatement statement1 = database.compileStatement(abc);

            Log.e("--Table--", "--FranchiseVisitMaster: " + abc);
//            Log.e("--Table--", "--FranchiseVisitMasterStatement:" + statement1.executeUpdateDelete());

        }
    };
*/
  /*  private static AppDatabase buildDatabase(Context context) {
        String dbname = ClsGlobal.DataBaseName;
        return Room.databaseBuilder(context, AppDatabase.class, dbname)
//                .addMigrations(MIGRATION_1_2)
//                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
    }*/


}
