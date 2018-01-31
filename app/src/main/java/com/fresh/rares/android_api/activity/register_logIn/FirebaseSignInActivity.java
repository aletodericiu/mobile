package com.fresh.rares.android_api.activity.register_logIn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fresh.rares.android_api.R;
import com.fresh.rares.android_api.activity.BaseActivity;
import com.fresh.rares.android_api.activity.ClientActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseSignInActivity extends BaseActivity implements View.OnClickListener
{
    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;

    // [START declare_auth]
    public static FirebaseAuth firebaseAuth;
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Views
        mEmailField = findViewById(R.id.editTextEmail_SignIn);
        mPasswordField = findViewById(R.id.editTextPassword_SignIn);

        // Buttons
        findViewById(R.id.buttonLogin_SignIn).setOnClickListener(this);
        findViewById(R.id.buttonSignUp_SignIn).setOnClickListener(this);

        // [START initialize_auth]
        firebaseAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and updateOffline UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);

        if (getIntent().getIntExtra("logOut", 1) == 1)
        {
            signOut();
        }
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password)
    {
        Log.d(TAG, "createAccount:" + email);
        //check if input is valid
        if (!validateForm())
        {
            return;
        }

        //show loading screen
        showProgressDialog();

        // [START create_user_with_email]
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task ->
                {
                    if (task.isSuccessful())
                    {
                        // Sign in success, updateOffline UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);
                    }
                    else
                    {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(FirebaseSignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog();
                    // [END_EXCLUDE]
                });
        Toast.makeText(this, "Registration successfully", Toast.LENGTH_SHORT).show();
        // [END create_user_with_email]
    }

    private void signIn(String email, String password)
    {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm())
        {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task ->
                {
                    if (task.isSuccessful())
                    {
                        // Sign in success, updateOffline UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);

//                        Intent intent = new Intent(this, ClientActivity.class);
//                        intent.putExtra("email", currentUserEmail);
//                        System.out.println("EMAIL: " + currentUserEmail);
//                        startActivity(intent);
                    }
                    else
                    {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut()
    {
        //logout from app and set the visibility for buttons
        firebaseAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm()
    {
        boolean valid = true;

        //get the email
        String email = mEmailField.getText().toString();
        //check if valid
        if (TextUtils.isEmpty(email))
        {
            mEmailField.setError("Required.");
            valid = false;
        }
        else
        {
            mEmailField.setError(null);
        }

        //get the password
        String password = mPasswordField.getText().toString();
        //check if valid
        if (TextUtils.isEmpty(password))
        {
            mPasswordField.setError("Required.");
            valid = false;
        }
        else
        {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user)
    {
        //hide loading screen
        hideProgressDialog();

        //check if user is null
        if (user != null)
        {
            Intent intent = new Intent(this, ClientActivity.class);
            intent.putExtra("email", user.getEmail());
            startActivity(intent);
        }
//        else
//        {
//            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
//            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
//        }
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.buttonSignUp_SignIn)
        {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        else if (i == R.id.buttonLogin_SignIn)
        {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }
}
