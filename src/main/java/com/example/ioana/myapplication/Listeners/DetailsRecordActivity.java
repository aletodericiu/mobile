package com.example.ioana.myapplication.Listeners;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.ioana.myapplication.Controller.RecordController;
import com.example.ioana.myapplication.Controller.RecordVoteController;
import com.example.ioana.myapplication.Domain.RecordItem;
import com.example.ioana.myapplication.Domain.RecordVote;
import com.example.ioana.myapplication.R;
import com.example.ioana.myapplication.Services.RemoteRecordServiceImpl;
import com.example.ioana.myapplication.Services.RemoteVoteServiceImpl;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ioana on 1/6/2018.
 */

public class DetailsRecordActivity extends AppCompatActivity {

    private RemoteRecordServiceImpl.RemoteRecordServiceInterface remoteRecordService = RemoteRecordServiceImpl.getInstance();
    private RemoteVoteServiceImpl.RemoteVoteServiceInterface remoteVoteService = RemoteVoteServiceImpl.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_record);

        Intent intent = getIntent();
        String name = intent.getStringExtra("recordName");

        readVotes(name);
        setDetails(name);
    }

    private void createChart(String name,Map<String, RecordVote> votes){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] points = new DataPoint[5];
        int number=0;

        Map<Integer,Integer> dict = createVoteDict(name,votes);

        for(Map.Entry<Integer, Integer> entry : dict.entrySet()) {
            int vote = entry.getKey();
            int numberOfVotes = entry.getValue();
            points[number] = new DataPoint(vote, numberOfVotes);
            number++;
        }


        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {

            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

        });


    }

    public Map<Integer,Integer> createVoteDict(String name,Map<String, RecordVote> votes){

        Map<Integer,Integer> dict= new HashMap<>();
        List<RecordVote> recordVotes = new ArrayList<>();

        for (Map.Entry<String, RecordVote> entry : votes.entrySet()) {
            if(entry.getValue().getRecordName().equals(name)) {
                recordVotes.add(entry.getValue());
            }
        }

        for(int i = 1; i<=5;i++){
            dict.put(i,0);
        }

        for(RecordVote recordVote : recordVotes){
            dict.put(recordVote.getVote(),dict.get(recordVote.getVote())+1);
        }
        Map<Integer, Integer> sortedDict = new TreeMap<>(dict);
        return sortedDict;
    }


    public void setDetails(String recordName){

//        final RecordController ctrl = new RecordController(this);
//        RecordItem record = ctrl.readSingleRecord(recordID);

        final TextView editSongName = (TextView) findViewById(R.id.textSongName);
        final TextView editSongBand = (TextView) findViewById(R.id.textSongBand);
        final TextView editSongGenre = (TextView) findViewById(R.id.textSongGenre);

        Call<RecordItem> call = remoteRecordService.getRecord(recordName);
        call.enqueue(new Callback<RecordItem>() {
            @Override
            public void onResponse(final Call<RecordItem> call,final Response<RecordItem> response) {
                final RecordItem record = response.body();
                if (record != null ) {
                    editSongName.setText("Name: "+ record.getName());
                    editSongBand.setText("Band: "+ record.getBand());
                    editSongGenre.setText("Genre: "+ record.getGenre());
                    Log.d("hhh", "~~~~~~~~~~~~~~~~~~~onResponse: MOVIE: " );
                } else {
                    Log.d("hh", "~~~~~~~~~~~~~~~~~~~~~onResponse: No movie found with the TITLE:" );
                }
            }

            @Override
            public void onFailure(Call<RecordItem> call, Throwable t) {
                Log.e("rrr", "~~~~~~~~~~~~~~~~`onResume: Failed to find movie..." + t.getLocalizedMessage(), t);

            }
        });


    }

    public void readVotes(final String name) {
        Call<Map<String, RecordVote>> call = remoteVoteService.getAllVotes();
        call.enqueue(new Callback<Map<String, RecordVote>>() {
            @Override
            public void onResponse(
                    final Call<Map<String, RecordVote>> call,
                    final Response<Map<String, RecordVote>> response) {
                final Map<String, RecordVote> votes = response.body();
                if (votes != null && !votes.isEmpty()) {
                    createChart(name,votes);
                    Log.d("hei", "******************onResponse: Votes found as map with size: " + votes.size());
                } else {
                    Log.d("heeei", "******************onResponse: No votes found");
                }
            }

            @Override
            public void onFailure(
                    final Call<Map<String, RecordVote>> call,
                    final Throwable t) {
                Log.e("aa", "**********************onResume: Failed to find votes..." + t.getLocalizedMessage(), t);
            }
        });
    }
}
