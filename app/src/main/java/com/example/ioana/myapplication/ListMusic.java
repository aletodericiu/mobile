package com.example.ioana.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ioana.myapplication.Controller.RecordController;
import com.example.ioana.myapplication.Domain.RecordItem;

import java.util.List;

public class ListMusic extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int EDIT_ACTIVITY_RESPONSE_CODE = 2;

    private List<RecordItem> records;
    ListView mylist;
    RecordListAdapter myadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        RecordController rc=new RecordController(this);
//        records = new ArrayList<RecordItem>() {{
//            add(new RecordItem("Song1", "Band1", "rock"));
//            add(new RecordItem("Song2", "Band2", "pop"));
//            add(new RecordItem("Song3", "Band3", "rock"));
//            add(new RecordItem("Song4", "Band4", "classic"));
//            add(new RecordItem("Song5", "Band5", "dance"));
//            add(new RecordItem("Song6", "Band6", "pop"));
//        }};
        records=rc.getData();
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
        Button buttonCreate = (Button) findViewById(R.id.add_button);
        buttonCreate.setOnClickListener(new AddItem(rc));
        setupSharedPreferences();
    }

    public void readData(){
        RecordController rc=new RecordController(this);
        records=rc.getData();
        mylist = (ListView) findViewById(R.id.list);
        myadapter = new RecordListAdapter(ListMusic.this, records);
        mylist.setAdapter(myadapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(ListMusic.this, EditItem.class);
                RecordItem selectedItem = (RecordItem) mylist.getItemAtPosition(position);
                myintent.putExtra("position", position);
                myintent.putExtra("song", selectedItem.getName());
                myintent.putExtra("band", selectedItem.getBand());
                myintent.putExtra("genre", selectedItem.getGenre());
                startActivityForResult(myintent, EDIT_ACTIVITY_RESPONSE_CODE);
            }
        });
    }


    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
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
        menuInflater.inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_settings){
            Intent intent=new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("show_text")){
            Toast.makeText(this,"OK",Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
