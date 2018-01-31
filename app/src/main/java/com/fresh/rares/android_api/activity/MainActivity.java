package com.fresh.rares.android_api.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fresh.rares.android_api.R;
import com.fresh.rares.android_api.activity.register_logIn.MySignInActivity;

/**
 * Created by Rares Abrudan on 1/18/2018.
 *
 * @email - raresabr@gmail.com
 */

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, FirebaseSignInActivity.class);
        Intent intent = new Intent(this, MySignInActivity.class);
        intent.putExtra("logOut", 0);
        startActivity(intent);
    }
}
