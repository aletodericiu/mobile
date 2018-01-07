package com.example.ioana.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ioana.myapplication.RecordContract;
import com.example.ioana.myapplication.RecordDBHelper;
import com.example.ioana.myapplication.Domain.RecordItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ioana on 1/6/2018.
 */

public class RecordController extends RecordDBHelper {
    //tag
    private static final String TAG = "Ale - DatabaseHelper";

    public RecordController(Context context) {
        super(context);
    }

    public boolean addData(RecordItem r){
        Log.d(TAG,"addData: setting content values... ");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecordContract.RecordEntry.SONG_NAME,r.getName());
        contentValues.put(RecordContract.RecordEntry.SONG_BAND,r.getBand());
        contentValues.put(RecordContract.RecordEntry.SONG_GENRE,r.getGenre());

        Log.d(TAG,"addData: Adding record - " + r.toString() + " into the DB!");

        long res = db.insert(RecordContract.RecordEntry.TABLE_NAME,null,contentValues);

        if (res == -1)
            return false;
        return true;
    }

    public List<RecordItem> getData(){
        Log.d(TAG,"getData... ");

        List<RecordItem> recordsList=new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + RecordContract.RecordEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));

                String name = cursor.getString(cursor.getColumnIndex("Song"));
                String band = cursor.getString(cursor.getColumnIndex("Band"));

                String genre = cursor.getString(cursor.getColumnIndex("Genre"));

                RecordItem record = new RecordItem(id, name, band, genre);
                recordsList.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;

    }


    public Cursor getRecordID(RecordItem r){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  " + RecordContract.RecordEntry.SONG_ID + " FROM " + RecordContract.RecordEntry.TABLE_NAME +
                " WHERE " + RecordContract.RecordEntry.SONG_NAME + " = '" + r.getName() + "' AND " +
                RecordContract.RecordEntry.SONG_BAND + " = '" + r.getBand() + RecordContract.RecordEntry.SONG_GENRE + " = '" + r.getGenre() + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean update(RecordItem record) {

        ContentValues values = new ContentValues();

        values.put(RecordContract.RecordEntry.SONG_NAME, record.getName());
        values.put(RecordContract.RecordEntry.SONG_BAND, record.getBand());
        values.put(RecordContract.RecordEntry.SONG_GENRE, record.getGenre());

        String where = RecordContract.RecordEntry.SONG_ID+ " = ?";
        String[] whereArgs = {Integer.toString(record.getId())};
        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(RecordContract.RecordEntry.TABLE_NAME , values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public int extractRecordID(RecordItem oldRecord){
        Cursor data = getRecordID(oldRecord);

        int itemID = -1;

        while(data.moveToNext())
            itemID = data.getInt(0);

        return itemID;
    }

    public boolean deleteRecord(int recordID){
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(RecordContract.RecordEntry.TABLE_NAME,  RecordContract.RecordEntry.SONG_ID +"='" + recordID + "'", null) > 0;
        db.close();

        return deleteSuccessful;
    }

    public RecordItem readSingleRecord(int recordId) {

        RecordItem record = null;
        String sql = "SELECT * FROM "+ RecordContract.RecordEntry.TABLE_NAME +" WHERE id = " + recordId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
            String name = cursor.getString(cursor.getColumnIndex("Song"));
            String band = cursor.getString(cursor.getColumnIndex("Band"));
            String genre = cursor.getString(cursor.getColumnIndex("Genre"));

            record = new RecordItem(id, name, band, genre);
        }
        cursor.close();
        db.close();
        return record;
    }
}

