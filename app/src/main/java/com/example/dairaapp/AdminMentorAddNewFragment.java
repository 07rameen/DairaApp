package com.example.dairaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminMentorAddNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminMentorAddNewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    EditText editTextName, editTextCnic, editTextContact, editTextEmail;
    Button buttonAdd;
    private String mParam1;
    private String mParam2;

    public AdminMentorAddNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminMentorAddNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminMentorAddNewFragment newInstance(String param1, String param2) {
        AdminMentorAddNewFragment fragment = new AdminMentorAddNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_mentor_add_new, container, false);
        editTextName = view.findViewById(R.id.edittextnameadminmentoraddnew);
        editTextCnic = view.findViewById(R.id.edittextcnicadminmentoraddnew);
        editTextContact = view.findViewById(R.id.edittextcontactadminmentoraddnew);
        editTextEmail = view.findViewById(R.id.edittextemailadminmentoraddnew);
        buttonAdd = view.findViewById(R.id.buttonadminmentoraddnew);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        return view;
    }

    public void register() {
        if (validate()) {
            fAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextContact.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getContext(), "Mentor has been Added", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("name", editTextName.getText().toString());
                    userInfo.put("cnic", editTextCnic.getText().toString());
                    userInfo.put("contact", editTextContact.getText().toString());
                    userInfo.put("email", editTextEmail.getText().toString());
                    userInfo.put("password", editTextContact.getText().toString());
                    userInfo.put("userLevel", "2");
                    df.set(userInfo);
                    editTextName.setText("");
                    editTextCnic.setText("");
                    editTextContact.setText("");
                    editTextEmail.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Fail to create mentor Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean validate(){
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
        return retValue;
    }
}