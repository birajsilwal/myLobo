package com.example.mylobo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        // This is for better User Experience as user do not have to log in every time they use app
        // User only have to log in once until they log out manually
        if (ParseUser.getCurrentUser() != null) {
            // User is directly sent to HomeScreen
            goHomeScreen();
        }

        // If users have logged or user is logging in first time then they have to enter username and password
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username, password);
            }
        });
    }

    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new
        LogInCallback() {
            @Override
            // Parse exception will be null if everything is successed, there is no exception
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    // TODO: better error handling
                    Log.e(TAG, "Issue with login");
                    Toast.makeText(LoginActivity.this, "Login Error.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }else if (user != null){
                    goHomeScreen();
                }
                else {
                    Log.e(TAG, "error");
                    Toast.makeText(LoginActivity.this, "Login Error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goHomeScreen() {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
        finish();
    }
}
