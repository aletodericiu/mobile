package com.example.ioana.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.icu.text.AlphabeticIndex;
import android.util.Log;

/**
 * Created by Ioana on 11/17/2017.
 */
public class RecordDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Records.db";



    public static final int DATABASE_VERSION=2;

    public RecordDBHelper(Context context) {
        super(context, DATABASE_NAME,  null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String query="CREATE TABLE "+ RecordContract.RecordEntry.TABLE_NAME + "("+ RecordContract.RecordEntry.SONG_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RecordContract.RecordEntry.SONG_NAME +

                " VARCHAR(50), " + RecordContract.RecordEntry.SONG_BAND + " VARCHAR(50), "
                + RecordContract.RecordEntry.SONG_GENRE + " VARCHAR(50));";
        db.execSQL(query);


        final String sql2 = "CREATE TABLE RecordVote " +
                               "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              "RecordID int Not null," +
                             "VOTE int not null," +
                              "FOREIGN KEY (RecordID) REFERENCES Record(ID))";


                                db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
