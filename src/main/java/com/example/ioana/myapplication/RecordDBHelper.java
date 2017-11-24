package com.example.ioana.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;

/**
 * Created by Ioana on 11/17/2017.
 */
public class RecordDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Records.db";

    public static final int DATABASE_VERSION=1;

    public RecordDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String query="CREATE TABLE "+ RecordContract.RecordEntry.TABLE_NAME + "("+ RecordContract.RecordEntry.SONG_NAME +
                " VARCHAR(50), " + RecordContract.RecordEntry.SONG_BAND + " VARCHAR(50), "
                + RecordContract.RecordEntry.SONG_GENRE + " VARCHAR(50), " + RecordContract.RecordEntry.SONG_LINK + " VARCHAR(50));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
