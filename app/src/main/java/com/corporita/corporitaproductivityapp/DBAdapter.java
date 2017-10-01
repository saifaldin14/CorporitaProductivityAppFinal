package com.corporita.corporitaproductivityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by saif on 2017-08-29.
 */

public class DBAdapter {

    //DB info
    public static final String DATABASE_NAME = "MyGoalsDB";
    public static final String DATABASE_GOALS_TABLE = "GoalsTable";
    public static final int DATABASE_VERSION = 12;

    ////DB fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    //fields
    public static final String KEY_GOAL = "goal";
    public static final String KEY_MEAS = "measure";
    public static final String KEY_CATE = "category";
    public static final String KEY_TIME = "time";
    public static final String KEY_PROG = "progress";
    public static final String KEY_UPDA = "proupdate";

    //field numbers
    public static final int COL_GOAL = 1;
    public static final int COL_MEAS = 2;
    public static final int COL_CATE = 3;
    public static final int COL_TIME = 4;
    public static final int COL_PROG = 5;
    public static final int COL_UPDA = 6;

    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_GOAL, KEY_MEAS, KEY_CATE, KEY_TIME, KEY_PROG, KEY_UPDA};



    //Tables
    private static final String DATABASE_CREATE_SQL = "create table " + DATABASE_GOALS_TABLE + " ("
            + KEY_ROWID + " integer primary key autoincrement, "

            + KEY_GOAL + " text, " + KEY_MEAS + " text, " + KEY_CATE + " text, " + KEY_TIME + " text, "
            + KEY_PROG + " integer, " + KEY_UPDA + " text"

            + ");";

    //Context of applications who uses us
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    //Public methods

    public DBAdapter(Context ctx){

        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open(){

        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){

        myDBHelper.close();
    }

    public long insertRow(String goal, String measure, String category, String time, int progress, String update){

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GOAL,goal);
        initialValues.put(KEY_MEAS,measure);
        initialValues.put(KEY_CATE, category);
        initialValues.put(KEY_TIME, time);
        initialValues.put(KEY_PROG, progress);
        initialValues.put(KEY_UPDA, update);

        return db.insert(DATABASE_GOALS_TABLE, null, initialValues);

    }

    public boolean deleteRow(long rowID){

        String where = KEY_ROWID + "=" + rowID;
        return  db.delete(DATABASE_GOALS_TABLE, where, null) > 0;

    }
    public void deleteAll(){
        Cursor c = getAllRows();
        long rowID = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do{
                deleteRow(c.getLong((int) rowID));

            }while (c.moveToNext());
        }
        c.close();
    }

    public Cursor getAllRows(){

        String where = null;
        Cursor c = db.query(true, DATABASE_GOALS_TABLE, ALL_KEYS, where,
                null, null, null, null, null);

        if(c != null){
            c.moveToFirst();
        }

        return c;
    }



    public Cursor getRow(long rowId){

        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, DATABASE_GOALS_TABLE, ALL_KEYS, where,
                null, null, null, null, null);

        if(c != null){
            c.moveToFirst();
        }

        return c;
    }

    public boolean updateRow(long rowId, String goal, String measure, String category, String time, int progress, String update){

        String where = KEY_ROWID + "=" + rowId;


        ContentValues newValues = new ContentValues();
        newValues.put(KEY_GOAL,goal);
        newValues.put(KEY_MEAS,measure);
        newValues.put(KEY_CATE, category);
        newValues.put(KEY_TIME, time);
        newValues.put(KEY_PROG, progress);
        newValues.put(KEY_UPDA, update);

        return db.update(DATABASE_GOALS_TABLE, newValues, where, null) > 0;
    }


    //Private Helper Classes

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {

            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_GOALS_TABLE);

            onCreate(_db);

        }
    }







}

