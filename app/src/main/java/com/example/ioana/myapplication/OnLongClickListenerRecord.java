package com.example.ioana.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioana.myapplication.Controller.RecordController;
import com.example.ioana.myapplication.Controller.RecordVoteController;
import com.example.ioana.myapplication.Domain.RecordItem;
import com.example.ioana.myapplication.Domain.RecordVote;

/**
 * Created by Ioana on 1/6/2018.
 */

class OnLongClickListenerRecord implements View.OnLongClickListener {
    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        TextView recordIdTextView = (TextView) view.findViewById(R.id.recordId);
        id=recordIdTextView.getText().toString();
        final CharSequence[] items = { "Edit", "Delete","Vote","View Details" };

        new AlertDialog.Builder(context).setTitle("Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }

                        else if (item == 1) {
                            boolean deleteSuccessful = new RecordController(context).deleteRecord(Integer.parseInt(id));
                            if (deleteSuccessful){
                                Toast.makeText(context, "Movie record was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete movie record.", Toast.LENGTH_SHORT).show();
                            }
                            ((ListMusic) context).readData();
                        }
                        else if(item==2){
                            voteRecord(Integer.parseInt(id));
                        }
                        else if(item==3){
                            viewDetails(Integer.parseInt(id), context);
                        }
                        dialog.dismiss();
                    }
                }).show();
        return false;
    }

   public void editRecord(final int recordId) {
       final RecordController ctrl = new RecordController(context);
       RecordItem record = ctrl.readSingleRecord(recordId);

       LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       final View formElementsView = inflater.inflate(R.layout.activity_edit_item, null, false);

       final EditText editTitleSong = (EditText) formElementsView.findViewById(R.id.song_edit);
       final EditText editBandSong = (EditText) formElementsView.findViewById(R.id.band_edit);
       final EditText editGenreSong = (EditText) formElementsView.findViewById(R.id.genre_edit);


       editTitleSong.setText(record.getName());
       editBandSong.setText(record.getBand());
       editGenreSong.setText(record.getGenre());

       new AlertDialog.Builder(context)
               .setView(formElementsView)
               .setTitle("Edit Record")
               .setPositiveButton("Save Changes",
                       new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RecordItem record = new RecordItem();
                                record.setId(recordId);
                                record.setName(editTitleSong.getText().toString());
                                record.setBand(editBandSong.getText().toString());
                                record.setGenre(editGenreSong.getText().toString());

                                boolean updateSuccessful = ctrl.update(record);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update record.", Toast.LENGTH_SHORT).show();
                                }

                                ((ListMusic) context).readData();

                                dialog.cancel();
                            }

                       }).show();
           }

    public void voteRecord(final int recordId){
        final RecordVoteController recordVoteCtrl = new RecordVoteController(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View voteRecordView = inflater.inflate(R.layout.vote_record, null, false);

        final NumberPicker reviewPicker = (NumberPicker) voteRecordView.findViewById(R.id.vote);
        final String[] values= {"1","2","3","4","5"};

        reviewPicker.setMinValue(1);
        reviewPicker.setMaxValue(5);
        reviewPicker.setWrapSelectorWheel(true);
        reviewPicker.setDisplayedValues(values);
        new AlertDialog.Builder(context)
                .setView(voteRecordView)
                .setTitle("Vote")
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RecordVote recordVote = new RecordVote();
                                recordVote.setRecordId(recordId);
                                recordVote.setVote(reviewPicker.getValue());

                                boolean createSuccessful = recordVoteCtrl.create(recordVote);

                                if(createSuccessful){
                                    Toast.makeText(context, "Record was voted.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to vote record.", Toast.LENGTH_SHORT).show();
                                }

                                ((ListMusic) context).readData();

                                dialog.cancel();
                            }

                                }).show();
                   }

    public void viewDetails(final int recordID, Context context){
        final RecordController ctrl = new RecordController(context);
        RecordItem record = ctrl.readSingleRecord(recordID);

        Intent myIntent = new Intent(context, DetailsRecordActivity.class);
        myIntent.putExtra("recordID", recordID); //Optional parameters
        context.startActivity(myIntent);
    }



 }

