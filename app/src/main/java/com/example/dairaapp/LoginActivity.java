package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.edittextemail);
        editTextPassword = findViewById(R.id.edittextpassword);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public boolean validate(){
        boolean retValue = true;
        if (editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError("Email is Required");
            retValue = false;
        }
        if (editTextPassword.getText().toString().isEmpty()) {
            editTextPassword.setError("Password is Required");
            retValue = false;
        }
        return retValue;
    }

    public void loginClickListner(View view) {
        if (validate()) {
            fAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    checkUserLevel(authResult.getUser().getUid());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Not", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userLevel = documentSnapshot.getString("userLevel");
                if (userLevel.equals("1")){
                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));

                }
                else if (userLevel.equals("2"))
                {
                    startActivity(new Intent(getApplicationContext(), MentorMainActivity.class));
                }
                else if (userLevel.equals("3"))
                {

                }
                else if (userLevel.equals("4"))
                {
                    startActivity(new Intent(getApplicationContext(), ParticipantMainActivity.class));
                }

                finish();
            }
        });
    }

    public void registerClickListner(View view) {
        Intent intent = new Intent(this, ActivtyRegister.class);
        startActivity(intent);
    }
}