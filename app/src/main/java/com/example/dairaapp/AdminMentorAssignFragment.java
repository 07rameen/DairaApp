package com.example.dairaapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminMentorAssignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminMentorAssignFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Spinner spinnerEvent, spinnerMentor;
    DatabaseReference reference;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    FirebaseFirestore db;
    ArrayList<String> list2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button buttonAssign;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminMentorAssignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminMentorAssignFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminMentorAssignFragment newInstance(String param1, String param2) {
        AdminMentorAssignFragment fragment = new AdminMentorAssignFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_mentor_assign, container, false);
        spinnerEvent = view.findViewById(R.id.spinnereventadminmentorassign);
        spinnerMentor = view. findViewById(R.id.spinnereventadminmentorassign2);
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Events");
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list2);
        spinnerEvent.setAdapter(adapter);
        spinnerMentor.setAdapter(adapter2);
        listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    list.add(snapshot1.child("name").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db.collection("Users").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        MentorHelperClass mentor = null;
                        if (error!=null) {
                            Log.d("TAG", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                mentor = dc.getDocument().toObject(MentorHelperClass.class);
                            }
                            if (mentor.getUserLevel().equals("2")){
                                list2.add(mentor.getName());
                            }
                            adapter2.notifyDataSetChanged();
                        }
                    }
                });
        buttonAssign = view.findViewById(R.id.buttonadminmentorassign);
        buttonAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference df = fStore.collection("AssignEvents").document(spinnerEvent.getSelectedItem().toString());
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("event", spinnerEvent.getSelectedItem().toString());
                userInfo.put("mentor", spinnerMentor.getSelectedItem().toString());
                df.set(userInfo);
                Toast.makeText(getContext(),spinnerEvent.getSelectedItem().toString() + " has been assigned to " + spinnerMentor.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}