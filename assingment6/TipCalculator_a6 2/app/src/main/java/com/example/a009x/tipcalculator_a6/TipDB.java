package com.example.a009x.tipcalculator_a6;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TipDB {

    public static final String DB_NAME = "tip.db";
    public static final int    DB_VERSION = 1;

    public static final String TIP_TABLE = "tip";
    public static final String TIP_ID = "_id";

    public static final int    TIP_ID_COL = 0;
    public static final String BILL_DATE = "bill_date";

    public static final int    BILL_DATE_COL = 1;
    public static final String BILL_AMOUNT = "bill_amount";

    public static final int    BILL_AMOUNT_COL = 2;
    public static final String TIP_PERCENT = "tip_percent";

    public static final int    TIP_PERCENT_COL = 3;

    public static final String CREATE_TIP_TABLE=
            "CREATE TABLE " + TIP_TABLE + " (" +
                    TIP_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    BILL_DATE + " INTEGER NOT NULL, " +
                    BILL_AMOUNT + " REAL, " +
                    TIP_PERCENT + " REAL);";

    public static final String DROP_TIP_TABLE=
            "DROP TABLE IF EXISTS " + TIP_TABLE;

    public static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name,
                        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_TIP_TABLE);

            // insert test data
            db.execSQL("INSERT INTO tip VALUES (1, 0, 40.00, .15)");
            db.execSQL("INSERT INTO tip VALUES (2, 0, 50.00, .20)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d("TipCalculator", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(TipDB.DROP_TIP_TABLE);
            onCreate(db);

        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public TipDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private void openReadableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    public ArrayList<Tip> getBills(){

        this.openReadableDB();
        Cursor cursor = db.query(TIP_TABLE,
                null, null, null, null, null, null);

        ArrayList<Tip> bills = new ArrayList<Tip>();
        while (cursor.moveToNext()) {
            Tip tip = new Tip(
                    cursor.getLong(TIP_ID_COL),
                    cursor.getLong(BILL_DATE_COL),
                    cursor.getFloat(BILL_AMOUNT_COL),
                    cursor.getFloat(TIP_PERCENT_COL));
            bills.add(tip);
        }

        if(cursor!= null){
            cursor.close();
        }
        this.closeDB();

        return bills;
    }

    public long insertTip(Tip tip) {
        ContentValues cv = new ContentValues();
        cv.put(BILL_DATE, System.currentTimeMillis());
        cv.put(BILL_AMOUNT, tip.getBillAmount());
        cv.put(TIP_PERCENT, tip.getTipPercent());

        this.openWriteableDB();
        long rowID = db.insert(TIP_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public long deleteTip(long id) {
        String where = TIP_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(TIP_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

}
