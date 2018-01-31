package com.example.ioana.gameapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioana.gameapp.adapters.ItemListAdapter;
import com.example.ioana.gameapp.components.GsonComponent;
import com.example.ioana.gameapp.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.ioana.gameapp.domain.Item;

public class EmployeeActivity extends AppCompatActivity {

    private ListView mListView;
    private ItemListAdapter mAdapter;
    private List<Item> mItems=new ArrayList<>();

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        mListView = (ListView) findViewById(R.id.lv_items);
        mProgressBar =(ProgressBar) findViewById(R.id.pb_employee);

        mAdapter = new ItemListAdapter(this, mItems);
        mListView.setAdapter(mAdapter);

        makeQuery();
    }

    void makeQuery(){
        URL queryURL=null;
        try {
            queryURL = new URL(NetworkUtils.BASE_URL + "/all");
            Log.d("sal","heei");
        }catch(IOException e){
            e.printStackTrace();
        }
        new QueryTask().execute(queryURL);
    }

    public class QueryTask extends AsyncTask<URL,Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(URL... params) {
            URL queryUrl=params[0];
            String jsonResult=null;
            try {
                jsonResult=NetworkUtils.getResponseFromHttpUrl(queryUrl);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String s){
            mProgressBar.setVisibility(View.INVISIBLE);
            if(s != null && !s.isEmpty()){
                Type type = new TypeToken<Collection<Item>>(){
                }.getType();

                mItems = GsonComponent.getInstance().getGson().fromJson(s,type);
                Log.d("hdhe",s);
                mAdapter = new ItemListAdapter(EmployeeActivity.this, mItems);
                mListView.setAdapter(mAdapter);
            }else{
                Toast.makeText(EmployeeActivity.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
