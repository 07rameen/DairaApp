package com.example.dairaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminEventAddNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminEventAddNewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText editTextName;
    Button buttonAdd;
    FirebaseDatabase rootnode;
    DatabaseReference reference;

    public AdminEventAddNewFragment() {
        // Required empty public constructor
    }

    public static AdminEventAddNewFragment newInstance(String param1, String param2) {
        AdminEventAddNewFragment fragment = new AdminEventAddNewFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_event_add_new, container, false);
        editTextName = view.findViewById(R.id.edittextadmineventaddnew);
        buttonAdd = view.findViewById(R.id.buttonadmineventaddnew);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("Events");
                EventHelperClass eventHelperClass = new EventHelperClass(editTextName.getText().toString(), "None");
                reference.child(editTextName.getText().toString()).setValue(eventHelperClass);
                editTextName.setText("");
                Toast.makeText(getContext(), "Event Added Successfully.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }



}