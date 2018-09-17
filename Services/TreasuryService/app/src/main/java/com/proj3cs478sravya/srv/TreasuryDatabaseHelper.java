package com.proj3cs478sravya.srv;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sravya on 4/28/2018.
 */

public class TreasuryDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "treasurydatabase";
    public static final String TB_NAME = "treasury";
    public static final String Year = "year_t";
    public static final String Month = "month_t";
    public static final String Date = "date_t";
    public static final String Day = "day_t";
    public static final String Open = "open_t";
    public static final String Close = "close_t";
    public static final String[] columns = {Year,Month,Date,Day,Open,Close};
    final private Context ctxt;

   final private String create_cmd =
           "create table treasury (year_t INTEGER,month_t INTEGER,date_t INTEGER,day_t TEXT,open_t INTEGER,close_t INTEGER)";

    public TreasuryDatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

        this.ctxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table treasury (year_t INTEGER,month_t INTEGER,date_t INTEGER,day_t TEXT,open_t INTEGER,close_t INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("Drop table if exists treasury");
            onCreate(db);

    }
    public void insert_db (String[] str){
        ContentValues vals = new ContentValues();
            vals.clear();
            SQLiteDatabase db = this.getWritableDatabase();
            vals.put(Year, str[0].toString());
            vals.put(Month, str[1].toString());
            vals.put(Date,str[2].toString() );
            vals.put(Day, str[3].toString());
            vals.put(Open, str[4].toString());
            vals.put(Close, str[5].toString());
           long abc = db.insert("treasury", null, vals);
           System.out.println(abc);

    }
    void deleteDB(){
        ctxt.deleteDatabase(DB_NAME);
    }
}
