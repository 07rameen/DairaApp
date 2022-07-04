package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ParticipantMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_main);
    }

    public void logoutPartMainClickListner(View view) {
        FirebaseAuth.getInstance().signOut();;
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}