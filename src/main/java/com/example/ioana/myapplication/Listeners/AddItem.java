package com.example.ioana.myapplication.Listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.AlphabeticIndex;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ioana.myapplication.Controller.RecordController;
import com.example.ioana.myapplication.Domain.RecordItem;
import com.example.ioana.myapplication.ListMusic;
import com.example.ioana.myapplication.R;
import com.example.ioana.myapplication.Services.RemoteRecordServiceImpl;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddItem implements View.OnClickListener {

//    private RecordController recordController;
//
//    public AddItem(RecordController rc){
//                recordController = rc;
//    }
    private RemoteRecordServiceImpl.RemoteRecordServiceInterface remoteService;
    private static final String TAG = "MyActivity";
    private Context parentContext;

    public AddItem(RemoteRecordServiceImpl.RemoteRecordServiceInterface service, Context context){
        remoteService = service;
        parentContext = context;
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
                                //boolean createSuccessful = new RecordController(context).addData(record);
                                addRecord(record);
//                                if(createSuccessful){
//                                    Toast.makeText(context, "Movie information was saved.", Toast.LENGTH_SHORT).show();
//                                }else{
//                                    Toast.makeText(context, "Unable to save movie information.", Toast.LENGTH_SHORT).show();
//                                }
                                dialog.cancel();
                            }
                }).show();
    }

    private void addRecord(RecordItem record){
        new AsyncTask<RecordItem, Object, Boolean>(){
            @Override
            protected Boolean doInBackground(RecordItem... recordItems) {
                for (RecordItem record:recordItems) {
                    Call<RecordItem> call = remoteService.createRecord(record.getName(), record);
                    call.enqueue(new Callback<RecordItem>() {
                        @Override
                        public void onResponse(
                                final Call<RecordItem> call,
                                final Response<RecordItem> response) {
                            Log.d(TAG, "----------------------onResponse: yeeey");
                            // RemoteRecordServiceImpl.notifyAllObservers();
                        }

                        @Override
                        public void onFailure(
                                final Call<RecordItem> call,
                                final Throwable t) {
                            Log.e(TAG, "----------------------onResponse:noooo....." + t.getLocalizedMessage()  ,t);
                        }
                    });
                }
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                RemoteMessage rm = new RemoteMessage.Builder("musicrecord-dd68b@gcm.googleapis.com")
                        .setMessageId(String.valueOf(2132155))
                        .addData("my_message", "Added Record")
                        .addData("Added Record","dsadsa").build();
                fm.send(rm);
                return true;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                ((ListMusic) parentContext).readRecords();
            }
        }.execute(record);
    }
}