package com.example.future.popularmovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by lenovo on 30/01/2016.
 */


    public class MovieDbHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "MovieHelper.db";
        public static final String TABLE_NAME = "movie_dataBase";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_title = "Title";
        public static final String COLUMN_NAME_vote_average = "Vote";
        public static final String COLUMN_NAME_poster = "Poster";
        public static final String COLUMN_NAME_overview = "overview";
    public static final String COLUMN_NAME_release_data = "Release";


        //        MovieContract contract = new MovieContract() ;
        public MovieDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + COLUMN_NAME_ID + " INTEGER , "
                    + COLUMN_NAME_release_data + " String , "
                    + COLUMN_NAME_poster + " String , "
                    + COLUMN_NAME_title + " String , "
                    + COLUMN_NAME_overview +" String , "
                    + COLUMN_NAME_vote_average + " INTEGER)";

            db.execSQL(SQL_CREATE_TABLE);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

