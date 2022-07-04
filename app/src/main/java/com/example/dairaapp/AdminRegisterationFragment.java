package com.example.dairaapp;

import static com.google.firebase.database.core.RepoManager.clear;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminRegisterationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminRegisterationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<RegisterationHelperClass> registerationList;
    showAllRegisterationAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminRegisterationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminRegisterationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminRegisterationFragment newInstance(String param1, String param2) {
        AdminRegisterationFragment fragment = new AdminRegisterationFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_registeration, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewadminshowallreg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        registerationList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Registrations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clear();
                for ( DataSnapshot snapshot1 : snapshot.getChildren() ){
                    RegisterationHelperClass registeration = new RegisterationHelperClass();
                    registeration.setEventName(snapshot1.child("event").getValue().toString());
                    registeration.setParticipantName(snapshot1.child("participant").getValue().toString());
                    registerationList.add(registeration);
                }
                adapter = new showAllRegisterationAdapter(registerationList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void clear() {
        if ( registerationList != null ) {
            registerationList.clear();
            if ( adapter!= null ) {
                adapter.notifyDataSetChanged();
            }
        }
        else {
            registerationList = new ArrayList<>();
        }
    }
}