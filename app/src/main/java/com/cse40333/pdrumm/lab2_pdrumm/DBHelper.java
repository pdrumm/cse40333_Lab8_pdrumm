package com.cse40333.pdrumm.lab2_pdrumm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;


public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "basketball.db";
    public static int DATABASE_VERSION = 1;

    public static String TABLE_TEAM = "Team";
    public static String C_TEAM_ID = "_id";
    public static String C_TEAM_NAME = "name";
    public static String C_TEAM_NICKNAME = "nickname";
    public static String C_TEAM_LOGO = "logo";
    public static String C_TEAM_WINS = "wins";
    public static String C_TEAM_LOSSES = "losses";
    public static String C_TEAM_STADIUM = "stadium";

    public static String TABLE_GAME = "Game";
    public static String C_GAME_ID = "_id";
    public static String C_GAME_DATE = "date";
    public static String C_GAME_HOME_TEAM_ID = "home_team_id";
    public static String C_GAME_AWAY_TEAM_ID = "away_team_id";
    public static String C_GAME_HOME_FIRST_HALF_SCORE = "home_first_half_score";
    public static String C_GAME_HOME_SECOND_HALF_SCORE = "home_second_half_score";
    public static String C_GAME_AWAY_FIRST_HALF_SCORE = "away_first_half_score";
    public static String C_GAME_AWAY_SECOND_HALF_SCORE = "away_second_half_score";
    public static String C_GAME_HOME_FINAL_SCORE = "home_final_score";
    public static String C_GAME_AWAYS_FINAL_SCORE = "away_final_score";

    public static String TABLE_IMAGES = "Images";
    public static String C_IMAGES_ID = "_id";
    public static String C_IMAGES_GAME = "game_id";
    public static String C_IMAGES_IMAGE = "image_blob";
    public static String C_IMAGES_DATE = "date";
    public static String C_IMAGES_URI = "uri";


    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TEAM + " ( " +
                C_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_TEAM_NAME + " TEXT, " +
                C_TEAM_NICKNAME + " TEXT, " +
                C_TEAM_LOGO + " TEXT, " +
                C_TEAM_WINS + " INTEGER, " +
                C_TEAM_LOSSES + " INTEGER, " +
                C_TEAM_STADIUM + " TEXT " +
                ")"
        );

        db.execSQL("CREATE TABLE " + TABLE_GAME + " ( " +
                C_GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_GAME_DATE + " INTEGER, " +
                C_GAME_HOME_TEAM_ID + " INTEGER, " +
                C_GAME_AWAY_TEAM_ID + " INTEGER, " +
                C_GAME_HOME_FIRST_HALF_SCORE + " INTEGER, " +
                C_GAME_HOME_SECOND_HALF_SCORE + " INTEGER, " +
                C_GAME_HOME_FINAL_SCORE + " INTEGER, " +
                C_GAME_AWAY_FIRST_HALF_SCORE + " INTEGER, " +
                C_GAME_AWAY_SECOND_HALF_SCORE + " INTEGER, " +
                C_GAME_AWAYS_FINAL_SCORE + " INTEGER " +
                ")"
        );

        db.execSQL("CREATE TABLE " + TABLE_IMAGES + " ( " +
                C_IMAGES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_IMAGES_GAME + " INTEGER, " +
                C_IMAGES_IMAGE + " BLOB, " +
                C_IMAGES_DATE + " TEXT, " +
                C_IMAGES_URI + " TEXT " +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLE_GAME );
        db.execSQL("DROP TABLE if exists " + TABLE_TEAM );
        db.execSQL("DROP TABLE if exists " + TABLE_IMAGES );
        onCreate(db);
    }

    public int insertData(String tblname, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();

        long ret = db.insert(tblname, null, contentValues );


        if (ret > -1) {
            System.out.println("Successfully inserted");
        } else {
            System.out.println("Insert Unsuccessful");
        }

        int rowId = getLastInsertId(db);
        db.close();

        return rowId;
    }

    public int getLastInsertId (SQLiteDatabase db) {
        int rowId = 0;
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();
        rowId = cursor.getInt(0);
        return rowId;
    }

    public void deleteData(String tblname, String clause, int _id) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(tblname, clause, new String[]{Integer.toString(_id)});
        db.close();
    }


    public Cursor getAllEntries(String tblname, String[] columns) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tblname, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getSelectEntries(String tblname, String[] columns, String where, String[] args) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tblname, columns, where, args, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public String[] getTableFields(String tblname) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor dbCursor = db.query(tblname, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        return columnNames;
    }

}
