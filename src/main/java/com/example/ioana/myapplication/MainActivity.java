package com.example.ioana.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

//    private static final int EMAIL_ACTIVITY_CODE = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        final EditText name_edit = (EditText) findViewById(R.id.name);
//
//        final EditText email_edit = (EditText) findViewById(R.id.email);
//
//        final Button register = (Button) findViewById(R.id.register_button);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = name_edit.getText().toString();
//                String email = email_edit.getText().toString();
//                EditText password_edit = (EditText) findViewById(R.id.password);
//                String password = password_edit.getText().toString();
//                EditText password_confirmed_edit = (EditText) findViewById(R.id.confirm_password);
//                String password_confirmed = password_confirmed_edit.getText().toString();
//                if(password.contentEquals(password_confirmed)){
//                    SendMail("Welcome to my app "+name+"!",email);
//                }else{
//                    Toast.makeText(MainActivity.this,"passwords do not match!",Toast.LENGTH_LONG);
//                    password_edit.setError("not a match");
//                    password_confirmed_edit.setError("not a match");
//                }
//            }
//        });
//    }
//
//    private void SendMail(String message, String receiver){
//        Intent myint = new Intent(Intent.ACTION_SENDTO);
//        myint.setType("*/*");
//        myint.setData(Uri.parse("mailto:"));
//        myint.putExtra(Intent.EXTRA_EMAIL,receiver);
//        myint.putExtra(Intent.EXTRA_SUBJECT,"Welcome!");
//        myint.putExtra(Intent.EXTRA_TEXT,message);
//
//        if (myint.resolveActivity(getPackageManager())!=null){
//            startActivityForResult(myint,EMAIL_ACTIVITY_CODE);
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if(requestCode == EMAIL_ACTIVITY_CODE){
//            allowAccess();
//        }
//    }
//
//    private void allowAccess(){
//        Intent listIntent=new Intent(MainActivity.this,ListMusic.class);
//        startActivity(listIntent);
//    }

    private FirebaseAuth mAuth;
    private TextView authFailed;
    private Button loginButton, signupButton;
    private EditText emailEdit, passwordEdit;
    private static Boolean admin;
    private static final String TAG = "LOGIN_SIGNUP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.child("admins").child("email").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getValue(String.class);
                    if (email.equals(mAuth.getCurrentUser().getEmail())) {
                        MainActivity.admin = true;
                    } else {
                        MainActivity.admin = true;
                    }
                    Intent intent = new Intent(MainActivity.this, ListMusic.class);
                    startActivity(intent);
                    //signedIn(true);
                    Log.d(TAG, "AAAAAAAAAAAADMIIIINUL :    " + email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "CANCELLLEEEED:    ");
                }
            });
        }
        setContentView(R.layout.activity_login_signup);

        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);
        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.password);
        authFailed = (TextView) findViewById(R.id.authFailed);
        MainActivity.admin = false;

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signIn(emailEdit.getText().toString(), passwordEdit.getText().toString());
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signUp(emailEdit.getText().toString(), passwordEdit.getText().toString());
            }
        });
    }

    public void signIn(String email, String password) {
        Log.d("maain", "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            rootRef.child("admins").child("email").addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String email = dataSnapshot.getValue(String.class);
                                    if (email.equals(mAuth.getCurrentUser().getEmail())) {
                                        MainActivity.admin = true;
                                    } else {
                                        MainActivity.admin = false;
                                    }
                                    Intent intent = new Intent(MainActivity.this, ListMusic.class);
                                    startActivity(intent);
                                    //signedIn(true);
                                    Log.d(TAG, "AAAAAAAAAAAADMIIIINUL :    " + email);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d(TAG, "CANCELLLEEEED:    ");
                                }
                            });

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            authFailed.setText("Authentification FAILED !");
                        }
                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            authFailed.setText("Authentification FAILED !");
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signUp(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            MainActivity.admin = false;
                            Intent intent = new Intent(MainActivity.this, ListMusic.class);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            authFailed.setText("Authentification FAILED !");
                            MainActivity.admin = false;
                        }
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    //Normal user - HomeActivity
    public void signedIn(boolean isAdmin) {
        Intent intent;
        authFailed.setText("");
        if (isAdmin == true) {
            intent = new Intent(MainActivity.this, HomeAdminActivity.class);
        } else {
            intent = new Intent(MainActivity.this, HomeActivity.class);
        }
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailEdit.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Required.");
            valid = false;
        } else {
            emailEdit.setError(null);
        }

        String password = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Required.");
            valid = false;
        } else {
            passwordEdit.setError(null);
        }

        return valid;
    }

    public static Boolean isAdmin() {
        return MainActivity.admin;
    }
}
