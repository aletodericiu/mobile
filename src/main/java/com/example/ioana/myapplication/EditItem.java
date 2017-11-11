package com.example.ioana.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {

    EditText song_edit;
    EditText band_edit;
    EditText genre_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        song_edit=(EditText) findViewById(R.id.song_edit);
        band_edit=(EditText) findViewById(R.id.band_edit);
        genre_edit=(EditText) findViewById(R.id.genre_edit);
    }
}
