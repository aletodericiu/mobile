package com.example.ioana.gameapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void onClientClicked(View v)
    {
        Intent clintIntent=new Intent(this, ClientActivity.class);
        startActivity(clintIntent);
    }

    void onEmployeeClicked(View v)
    {
        Intent employeeIntent=new Intent(this, EmployeeActivity.class);
        startActivity(employeeIntent);
    }
}
