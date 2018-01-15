package com.example.ioana.myapplication.Listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.example.ioana.myapplication.ListMusic;
import com.example.ioana.myapplication.MainActivity;
import com.example.ioana.myapplication.R;
import com.example.ioana.myapplication.Services.RemoteRecordServiceImpl;
import com.example.ioana.myapplication.Services.RemoteVoteServiceImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ioana on 1/6/2018.
 */

public class OnLongClickListenerRecord implements View.OnLongClickListener {
    private RemoteRecordServiceImpl.RemoteRecordServiceInterface remoteRecordService = RemoteRecordServiceImpl.getInstance();
    private RemoteVoteServiceImpl.RemoteVoteServiceInterface remoteVoteService = RemoteVoteServiceImpl.getInstance();
    Context context;
    String id;
    public static final String TAG = "Heeei";

    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        TextView recordIdTextView = (TextView) view.findViewById(R.id.recordId);
        TextView recordNameTextView = (TextView) view.findViewById(R.id.song);
        id=recordIdTextView.getText().toString();
        final String name=recordNameTextView.getText().toString();
        if(MainActivity.isAdmin()) {
            final CharSequence[] items = {"Edit", "Delete"};

            new AlertDialog.Builder(context).setTitle("Record")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                if (MainActivity.isAdmin()) {
                                    getRecord(name);
                                } else {
                                    //TBD
                                }
                            } else if (item == 1) {
                                deleteRecord(name);
//                            boolean deleteSuccessful = new RecordController(context).deleteRecord(Integer.parseInt(id));
//                            if (deleteSuccessful){
//                                Toast.makeText(context, "Movie record was deleted.", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(context, "Unable to delete movie record.", Toast.LENGTH_SHORT).show();
//                            }
                                ((ListMusic) context).readRecords();
                            }
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
            final CharSequence[] items = {"Vote", "View Details"};

            new AlertDialog.Builder(context).setTitle("Record")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                voteRecord(name);
                            } else if (item == 1) {
                                viewDetails(name, context);
                            }
                            dialog.dismiss();
                        }
                    }).show();
        }
        return false;
    }

   public void editRecord(RecordItem record) {
//       final RecordController ctrl = new RecordController(context);
//       RecordItem record = ctrl.readSingleRecord(recordId);

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
                                //record.setId(recordId);
                                record.setName(editTitleSong.getText().toString());
                                record.setBand(editBandSong.getText().toString());
                                record.setGenre(editGenreSong.getText().toString());

                                updateRecord(record);
                                RemoteRecordServiceImpl.notifyAllObservers();

//                                boolean updateSuccessful = ctrl.update(record);
//
//                                if(updateSuccessful){
//                                    Toast.makeText(context, "Record was updated.", Toast.LENGTH_SHORT).show();
//                                }else{
//                                    Toast.makeText(context, "Unable to update record.", Toast.LENGTH_SHORT).show();
//                                }

                                ((ListMusic) context).readRecords();

                                dialog.cancel();
                            }

                       }).show();
           }

    public void voteRecord(final String name){
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
                                recordVote.setId(java.util.UUID.randomUUID().toString());
                                recordVote.setRecordName(name);
                                recordVote.setVote(reviewPicker.getValue());

                                addVote(recordVote);
//                                boolean createSuccessful = recordVoteCtrl.create(recordVote);
//
//                                if(createSuccessful){
//                                    Toast.makeText(context, "Record was voted.", Toast.LENGTH_SHORT).show();
//                                }else{
//                                    Toast.makeText(context, "Unable to vote record.", Toast.LENGTH_SHORT).show();
//                                }

                                ((ListMusic) context).readRecords();

                                dialog.cancel();
                            }

                                }).show();
                   }

    public void viewDetails(String name, Context context){
//        final RecordController ctrl = new RecordController(context);
//        RecordItem record = ctrl.readSingleRecord(recordID);
//
//        Intent myIntent = new Intent(context, DetailsRecordActivity.class);
//        myIntent.putExtra("recordID", recordID); //Optional parameters
//        context.startActivity(myIntent);
        Intent myIntent = new Intent(context, DetailsRecordActivity.class);
        myIntent.putExtra("recordName", name);
        context.startActivity(myIntent);
    }

    private void addVote(RecordVote vote){
        Call<RecordVote> call = remoteVoteService.createVote(vote.getId(), vote);
        call.enqueue(new Callback<RecordVote>() {

            @Override
            public void onResponse(
                    final Call<RecordVote> call,
                    final Response<RecordVote> response) {
                Log.d(TAG, "----------------------onResponse:Votee yeey");

            }

            @Override
            public void onFailure(
                    final Call<RecordVote> call,
                    final Throwable t) {
                Log.e(TAG, "----------------------onResponse: Vote noo....." + t.getLocalizedMessage()  ,t);
            }
        });
    }

    private void deleteRecord(String name){
        Call<RecordItem> call = remoteRecordService.deleteRecord(name);
        call.enqueue(new Callback<RecordItem>() {
            @Override
            public void onResponse(
                    final Call<RecordItem> call,
                    final Response<RecordItem> response) {
                Log.d(TAG, "----------------------onResponse: mergee");
                RemoteRecordServiceImpl.notifyAllObservers();
            }

            @Override
            public void onFailure(
                    final Call<RecordItem> call,
                    final Throwable t) {
                Log.e(TAG, "----------------------onResponse:NUUUUU....." + t.getLocalizedMessage(), t);
            }
        });
    }

    private void updateRecord(RecordItem record) {
        Call<RecordItem> call = remoteRecordService.createRecord(record.getName(), record);
        call.enqueue(new Callback<RecordItem>() {
            @Override
            public void onResponse(
                    final Call<RecordItem> call,
                    final Response<RecordItem> response) {
                //((MainActivity) context).readRecords();
                ((ListMusic) context).readRecords();

                Log.d(TAG, "----------------------onResponse: merge");
            }

            @Override
            public void onFailure(
                    final Call<RecordItem> call,
                    final Throwable t) {
                Log.e(TAG, "----------------------onResponse:NUUUUU ....." + t.getLocalizedMessage(), t);
            }
        });
    }

    public void getRecord(final String name) {
        Call<RecordItem> call = remoteRecordService.getRecord(name);
        call.enqueue(new Callback<RecordItem>() {
            @Override
            public void onResponse(final Call<RecordItem> call,final Response<RecordItem> response) {
                final RecordItem record = response.body();
                if (record != null ) {
                    editRecord(record);
                    Log.d(TAG, "~~~~~~~~~~~~~~~~~~~onResponse: RECORD: " + record.getBand());
                } else {
                    Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~onResponse: No record found with the TITLE:" + name);
                }
            }

            @Override
            public void onFailure(Call<RecordItem> call, Throwable t) {
                Log.e(TAG, "~~~~~~~~~~~~~~~~`onResume: Failed to find record..." + t.getLocalizedMessage(), t);

            }
        });
    }

 }

