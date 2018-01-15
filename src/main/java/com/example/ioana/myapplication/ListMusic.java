package com.example.ioana.myapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ioana.myapplication.Controller.RecordController;
import com.example.ioana.myapplication.Domain.RecordItem;
import com.example.ioana.myapplication.Listeners.AddItem;
import com.example.ioana.myapplication.Observer.Observer;
import com.example.ioana.myapplication.Services.RemoteRecordServiceImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMusic extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Observer {

    private static final int EDIT_ACTIVITY_RESPONSE_CODE = 2;
    private static final String TAG = "Aici";


    private List<RecordItem> records = new ArrayList<RecordItem>();
    ListView mylist;
    RecordListAdapter myadapter;
    private RemoteRecordServiceImpl.RemoteRecordServiceInterface remoteService = RemoteRecordServiceImpl.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        //RecordController rc=new RecordController(this);
//        records = new ArrayList<RecordItem>() {{
//            add(new RecordItem("Song1", "Band1", "rock"));
//            add(new RecordItem("Song2", "Band2", "pop"));
//            add(new RecordItem("Song3", "Band3", "rock"));
//            add(new RecordItem("Song4", "Band4", "classic"));
//            add(new RecordItem("Song5", "Band5", "dance"));
//            add(new RecordItem("Song6", "Band6", "pop"));
//        }};
        //records=rc.getData();
        mylist = (ListView) findViewById(R.id.list);
        myadapter = new RecordListAdapter(ListMusic.this, records);
        mylist.setAdapter(myadapter);
//        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent myintent = new Intent(ListMusic.this, EditItem.class);
//                RecordItem selectedItem = (RecordItem) mylist.getItemAtPosition(position);
//                myintent.putExtra("position", position);
//                myintent.putExtra("song", selectedItem.getName());
//                myintent.putExtra("band", selectedItem.getBand());
//                myintent.putExtra("genre", selectedItem.getGenre());
//                startActivityForResult(myintent, EDIT_ACTIVITY_RESPONSE_CODE);
//            }
//        });
        readRecords();
        Button buttonCreate = (Button) findViewById(R.id.add_button);
        final Context c = this;
        if (MainActivity.isAdmin())
            buttonCreate.setOnClickListener(new AddItem(remoteService, c));
        else
            buttonCreate.setVisibility(View.INVISIBLE);
        setupSharedPreferences();
    }

    public void readData() {
        RecordController rc = new RecordController(this);
        records = rc.getData();
        mylist = (ListView) findViewById(R.id.list);
        myadapter = new RecordListAdapter(ListMusic.this, records);
        mylist.setAdapter(myadapter);
//        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent myintent = new Intent(ListMusic.this, EditItem.class);
//                RecordItem selectedItem = (RecordItem) mylist.getItemAtPosition(position);
//                myintent.putExtra("position", position);
//                myintent.putExtra("song", selectedItem.getName());
//                myintent.putExtra("band", selectedItem.getBand());
//                myintent.putExtra("genre", selectedItem.getGenre());
//                startActivityForResult(myintent, EDIT_ACTIVITY_RESPONSE_CODE);
//            }
//        });
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //todo
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDIT_ACTIVITY_RESPONSE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                RecordItem item = (RecordItem) mylist.getItemAtPosition(data.getIntExtra("position", 0));
                item.setName(data.getStringExtra("song"));
                item.setBand(data.getStringExtra("band"));
                item.setGenre(data.getStringExtra("genre"));
                myadapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("show_text")) {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void update() {
        Log.d(TAG, "OOOOOOBSEEEEEEEEEEEEEEEEEERVAAAAAAAABLE");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc)
                        .setContentTitle("Records Application")
                        .setContentText("The list of records has been modified");

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public void displayData(Map<String, RecordItem> records) {
        ArrayList<RecordItem> recordsArray = new ArrayList<>();

        for (Map.Entry<String, RecordItem> entry : records.entrySet()) {
            recordsArray.add(entry.getValue());
        }

        RecordListAdapter recordsAdapter = new RecordListAdapter(this, recordsArray);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(recordsAdapter);
    }

    public void readRecords() {
        Call<Map<String, RecordItem>> call = remoteService.getAllRecords();
        call.enqueue(new Callback<Map<String, RecordItem>>() {
            @Override
            public void onResponse(
                    final Call<Map<String, RecordItem>> call,
                    final Response<Map<String, RecordItem>> response) {
                final Map<String, RecordItem> records = response.body();
                if (records != null && !records.isEmpty()) {
                    displayData(records);
                    Log.d(TAG, "******************onResponse: Records found as map with size: " + records.size());
                } else {
                    Log.d(TAG, "******************onResponse: No records found");
                }
            }

            @Override
            public void onFailure(
                    final Call<Map<String, RecordItem>> call,
                    final Throwable t) {
                Log.e(TAG, "**********************onResume: Failed to find records..." + t.getLocalizedMessage(), t);
            }
        });
    }
}
