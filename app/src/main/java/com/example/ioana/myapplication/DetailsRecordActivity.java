package com.example.ioana.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ioana.myapplication.Controller.RecordController;
import com.example.ioana.myapplication.Controller.RecordVoteController;
import com.example.ioana.myapplication.Domain.RecordItem;
import com.example.ioana.myapplication.Domain.RecordVote;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Ioana on 1/6/2018.
 */

public class DetailsRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_record);

        Intent intent = getIntent();
        int recordID = intent.getIntExtra("recordID",0);

        createChart(recordID);
        setDetails(recordID);
    }

    private void createChart(int recordID){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] points = new DataPoint[5];
        int number=0;

        Map<Integer,Integer> dict = createVoteDict(recordID);

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
//       @Override
//       public String formatLabel(double value, boolean isValueX) {
//           if (isValueX) {
//               return  Review.values()[(int)value].toString();//super.formatLabel(value, isValueX);
//           } else {
//               return super.formatLabel(value, isValueX) ;
//           }
//       }
        });


    }

    public Map<Integer,Integer> createVoteDict(int recordID){

        Map<Integer,Integer> dict= new HashMap<>();
        RecordVoteController ctrl = new RecordVoteController(this);
        List<RecordVote> recordVotes = ctrl.getVotesOfRecord(recordID);

        for(int i = 1; i<=5;i++){
            dict.put(i,0);
        }

        for(RecordVote recordVote : recordVotes){
            dict.put(recordVote.getVote(),dict.get(recordVote.getVote())+1);
        }
        Map<Integer, Integer> sortedDict = new TreeMap<>(dict);
        return sortedDict;
    }


    public void setDetails(int recordID){

        final RecordController ctrl = new RecordController(this);
        RecordItem record = ctrl.readSingleRecord(recordID);

        final TextView editSongName = (TextView) findViewById(R.id.textSongName);
        final TextView editSongBand = (TextView) findViewById(R.id.textSongBand);
        final TextView editSongGenre = (TextView) findViewById(R.id.textSongGenre);

        editSongName.setText("Name: "+ record.getName());
        editSongBand.setText("Band: "+ record.getBand());
        editSongGenre.setText("Genre: "+ record.getGenre());
    }
}
