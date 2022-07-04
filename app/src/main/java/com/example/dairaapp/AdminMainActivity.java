package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        bottomNavigationView = findViewById(R.id.adminmainbottomnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.adminmainmaincontainer, new adminHomeFragment()).commit();
        setTitle("Home");
        bottomNavigationView.setSelectedItemId(R.id.adminnav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.adminnav_home:
                        setTitle("Home");
                        fragment = new adminHomeFragment();
                        break;
                    case R.id.adminnav_events:
                        fragment = new AdminEventFragment();
                        setTitle("Events");
                        break;
                    case R.id.adminnav_mentor:
                        fragment = new AdminMentorFragment();
                        setTitle("Mentors");
                        break;
                    case R.id.adminnav_registeration:
                        fragment = new AdminRegisterationFragment();
                        setTitle("Registerations");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.adminmainmaincontainer, fragment).commit();
                return true;
            }
        });
    }

//    public void logoutAdminMainClickListner(View view) {
//        FirebaseAuth.getInstance().signOut();;
//        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        finish();
//    }
}