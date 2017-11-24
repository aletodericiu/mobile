package com.example.ioana.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {

    EditText song_edit;
    EditText band_edit;
    EditText genre_edit;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        song_edit=(EditText) findViewById(R.id.song_edit);
        band_edit=(EditText) findViewById(R.id.band_edit);
        genre_edit=(EditText) findViewById(R.id.genre_edit);
        save_button = (Button) findViewById(R.id.edit_button);

        Intent startingIntent = getIntent();
        final int position = startingIntent.getIntExtra("position",0);
        song_edit.setText(startingIntent.getStringExtra("song"));
        band_edit.setText(startingIntent.getStringExtra("band"));
        genre_edit.setText(startingIntent.getStringExtra("genre"));

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("position",position);
                returnIntent.putExtra("song",song_edit.getText().toString());
                returnIntent.putExtra("band",band_edit.getText().toString());
                returnIntent.putExtra("genre",genre_edit.getText().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}