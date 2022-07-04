package com.example.dairaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.splashscreen);
        setContentView(R.layout.activity_main);
    /*    getSupportActionBar().hide();

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
    */

    }

    @Override
    protected void onStart() {
        super.onStart();
        pause();
    }

    void pause(){
        timer = new Timer();
        Intent intent = new Intent(this, LoginActivity.class);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1250);

    }

}