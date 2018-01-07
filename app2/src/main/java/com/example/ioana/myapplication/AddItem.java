package com.example.ioana.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ioana.myapplication.Controller.RecordController;
import com.example.ioana.myapplication.Domain.RecordItem;


public class AddItem implements View.OnClickListener {

    private RecordController recordController;
    public AddItem(RecordController rc){
                recordController = rc;
    }

            public void onClick(View view){
               final Context context = view.getRootView().getContext();
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              final View formElementsView = inflater.inflate(R.layout.activity_add_item, null, false);

                      final EditText editTextSongName = (EditText) formElementsView.findViewById(R.id.song_edit);
             final EditText editTextSongBand = (EditText) formElementsView.findViewById(R.id.band_edit);
              final EditText editTextSongGenre = (EditText) formElementsView.findViewById(R.id.genre_edit);


                        new AlertDialog.Builder(context)
                                .setView(formElementsView)
                                .setTitle("Add Record")
                                .setPositiveButton("Add",
                                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                                        String name = editTextSongName.getText().toString();
                                                       String band = editTextSongBand.getText().toString();
                                                        String genre =editTextSongGenre.getText().toString();
                                Log.d("lallalal","getData... ");
                                                              RecordItem record = new RecordItem(name,band,genre);
                                                       boolean createSuccessful = new RecordController(context).addData(record);

                                                                if(createSuccessful){
                                                                Toast.makeText(context, "Movie information was saved.", Toast.LENGTH_SHORT).show();
                                                            }else{
                                                                Toast.makeText(context, "Unable to save movie information.", Toast.LENGTH_SHORT).show();
                                                            }
                                                       ((ListMusic) context).readData();
                                                        dialog.cancel();
                                                    }
                         }).show();
            }
}