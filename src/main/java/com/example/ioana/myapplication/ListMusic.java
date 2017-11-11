package com.example.ioana.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListMusic extends AppCompatActivity {

    private List<RecordItem> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        records=new ArrayList<RecordItem>(){{
            add(new RecordItem("Song1","Band1","rock"));
            add(new RecordItem("Song2","Band2","pop"));
            add(new RecordItem("Song3","Band3","rock"));
            add(new RecordItem("Song4","Band4","classic"));
            add(new RecordItem("Song5","Band5","dance"));
            add(new RecordItem("Song6","Band6","pop"));
        }};
        ListView mylist=(ListView)findViewById(R.id.list);
        RecordListAdapter myadapter=new RecordListAdapter(ListMusic.this,records);
        mylist.setAdapter(myadapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent=new Intent(ListMusic.this,EditItem.class);
                startActivity(myintent);
            }
        });
    }

}
