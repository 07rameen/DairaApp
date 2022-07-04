package com.example.dairaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivtyRegister extends AppCompatActivity {

    EditText editTextName, editTextCnic, editTextContact, editTextEmail, editTextPassword;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_register);
        editTextName = findViewById(R.id.edittextregname);
        editTextCnic = findViewById(R.id.edittextregcnic);
        editTextContact = findViewById(R.id.edittextregcontact);
        editTextEmail = findViewById(R.id.edittextregemail);
        editTextPassword = findViewById(R.id.edittextregpassword);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }


    public void registerRegClickListner(View view) {
        if (validate()) {
            fAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(ActivtyRegister.this, "Account has been Created", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("name", editTextName.getText().toString());
                    userInfo.put("cnic", editTextCnic.getText().toString());
                    userInfo.put("contact", editTextContact.getText().toString());
                    userInfo.put("email", editTextEmail.getText().toString());
                    userInfo.put("password", editTextPassword.getText().toString());
                    userInfo.put("userLevel", "4");
                    df.set(userInfo);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ActivtyRegister.this, "Fail to create account Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    
    public boolean validate() {
        boolean retValue = true;
        if (editTextName.getText().toString().isEmpty()) {
            editTextName.setError("Name is Missing");
            retValue = false;
        }
        if (editTextCnic.getText().toString().isEmpty()) {
            editTextCnic.setError("CNIC is Missing");
            retValue = false;
        }
        if (editTextContact.getText().toString().isEmpty()) {
            editTextContact.setError("Contact is Missing");
            retValue = false;
        }
        if (editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError("Email is Missing");
            retValue = false;
        }
        if (editTextPassword.getText().toString().isEmpty()) {
            editTextPassword.setError("Password is Missing");
            retValue = false;
        }
        return retValue;
    }

}