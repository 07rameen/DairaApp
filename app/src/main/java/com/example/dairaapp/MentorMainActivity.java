package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MentorMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_main);
        bottomNavigationView = findViewById(R.id.mentormainbottomnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.mentormainActivityContainer, new MentorHomeFragment()).commit();
        setTitle("Home");
        bottomNavigationView.setSelectedItemId(R.id.mentornav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.mentornav_home:
                        setTitle("Home");
                        fragment = new MentorHomeFragment();
                        break;
                    case R.id.mentornav_events:
                        fragment = new MentorSubEventFragment();
                        setTitle("Sub Events");
                        break;
                    case R.id.mentornav_oc:
                        fragment = new MentorOCFragment();
                        setTitle("OC");
                        break;
                    case R.id.mentornav_registeration:
                        fragment = new AdminRegisterationFragment();
                        setTitle("Registerations");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.mentormainActivityContainer, fragment).commit();
                return true;
            }
        });
    }
}