package com.example.ioana.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int EMAIL_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText name_edit = (EditText) findViewById(R.id.name);

        final EditText email_edit = (EditText) findViewById(R.id.email);

        final Button register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_edit.getText().toString();
                String email = email_edit.getText().toString();
                EditText password_edit = (EditText) findViewById(R.id.password);
                String password = password_edit.getText().toString();
                EditText password_confirmed_edit = (EditText) findViewById(R.id.confirm_password);
                String password_confirmed = password_confirmed_edit.getText().toString();
                if(password.contentEquals(password_confirmed)){
                    SendMail("Welcome to my app "+name+"!",email);
                }else{
                    Toast.makeText(MainActivity.this,"passwords do not match!",Toast.LENGTH_LONG);
                    password_edit.setError("not a match");
                    password_confirmed_edit.setError("not a match");
                }
            }
        });
    }

    private void SendMail(String message, String receiver){
        Intent myint = new Intent(Intent.ACTION_SENDTO);
        myint.setType("*/*");
        myint.setData(Uri.parse("mailto:"));
        myint.putExtra(Intent.EXTRA_EMAIL,receiver);
        myint.putExtra(Intent.EXTRA_SUBJECT,"Welcome!");
        myint.putExtra(Intent.EXTRA_TEXT,message);

        if (myint.resolveActivity(getPackageManager())!=null){
            startActivityForResult(myint,EMAIL_ACTIVITY_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == EMAIL_ACTIVITY_CODE){
            allowAccess();
        }
    }

    private void allowAccess(){
        Intent listIntent=new Intent(MainActivity.this,ListMusic.class);
        startActivity(listIntent);
    }
}