package com.example.ioana.myapplication.DB;

import android.provider.BaseColumns;

/**
 * Created by Ioana on 11/17/2017.
 */public class RecordContract {

    private RecordContract(){}

    public static final class RecordEntry implements BaseColumns{

        public static final String TABLE_NAME="Record";
        public static final String SONG_ID="ID";
        public static final String SONG_NAME="Song";
        public static final String SONG_BAND="Band";
        public static final String SONG_GENRE="Genre";

    }
}


