package com.example.ioana.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ioana.myapplication.Domain.RecordVote;
import com.example.ioana.myapplication.RecordDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ioana on 1/6/2018.
 */

public class RecordVoteController extends RecordDBHelper {
    public RecordVoteController(Context context) {
        super(context);
    }

    public boolean create(RecordVote recordVote){
        ContentValues values = new ContentValues();

        values.put("RecordID", recordVote.getRecordId());
        values.put("VOTE", recordVote.getVote());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("RecordVote", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public List<RecordVote> getVotesOfRecord(int recordId){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM RecordVote WHERE RecordID = " + recordId;

        List<RecordVote> recordsList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
                int recordIdd = Integer.parseInt(cursor.getString(cursor.getColumnIndex("RecordID")));
                int vote = Integer.parseInt(cursor.getString(cursor.getColumnIndex("VOTE")));

                RecordVote recordVote = new RecordVote(id, recordIdd, vote);
                recordsList.add(recordVote);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recordsList;
    }
}
