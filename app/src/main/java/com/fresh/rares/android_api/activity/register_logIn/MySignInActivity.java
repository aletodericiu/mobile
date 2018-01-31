package com.fresh.rares.android_api.activity.register_logIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fresh.rares.android_api.R;
import com.fresh.rares.android_api.activity.BaseActivity;
import com.fresh.rares.android_api.activity.ClientActivity;
import com.fresh.rares.android_api.util.IOActions;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Rares Abrudan on 1/10/2018.
 *
 * @email - raresabr@gmail.com
 */

public class MySignInActivity extends BaseActivity implements View.OnClickListener
{
    //
    private EditText usernameEditText, passwordEditText;

    //
    private IOActions ioActions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Views
        usernameEditText = findViewById(R.id.editTextEmail_SignIn);
        passwordEditText = findViewById(R.id.editTextPassword_SignIn);

        // Buttons
        findViewById(R.id.buttonLogin_SignIn).setOnClickListener(this);
        findViewById(R.id.buttonSignUp_SignIn).setOnClickListener(this);

        ioActions = new IOActions(new ArrayList<>(), getApplicationContext());
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (getIntent().getIntExtra("logOut", 1) == 1)
        {
            ioActions.emptyFile();
        }

        if (!ioActions.checkEmptyFile("loginsession"))
        {
            Intent intent = new Intent(this, ClientActivity.class);
            intent.putExtra("email", ioActions.readLoggedInUserFromFile());
            startActivity(intent);
        }
    }

    public void signIn(String username, String password)
    {
        final String url = "http://10.0.2.2:8081/users/login/" + username + "/" + password;
        // Make request for JSONObject
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    // TODO: 1/24/2018 Add the neccessary consition
                    if (Objects.equals(response, "LoginSuccessful"))
                    {
                        System.out.println("MESSAGE RESPONSE: " + response);
                        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

                        ioActions.writeLoggedInUserToFile(username);

                        Intent intent = new Intent(this, ClientActivity.class);
                        intent.putExtra("email", username);
                        startActivity(intent);
                    }
                    else 
                    {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                },
                error ->
                {
//                    System.out.println("MESSAGE ERROR: " + error);
//                    Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(this, ClientActivity.class);
//                    intent.putExtra("email", username);
//                    startActivity(intent);

                    Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
        );

        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public void createAccount(String username, String password)
    {
        showProgressDialog();

        final String url = "http://10.0.2.2:8081/users/register/" + username + "/" + password;
        // Make request for JSONObject
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    System.out.println("MESSAGE RESPONSE: " + response);
                    Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show();
                },
                error ->
                {
                    System.out.println("MESSAGE ERROR: " + error);
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
        );
        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);

        hideProgressDialog();
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.buttonSignUp_SignIn)
        {
            createAccount(usernameEditText.getText().toString(), passwordEditText.getText().toString());
        }
        else if (i == R.id.buttonLogin_SignIn)
        {
            showProgressDialog();
            signIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            hideProgressDialog();
        }
    }
}
