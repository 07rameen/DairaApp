package com.example.dairaapp;

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
 * Use the {@link MentorSubEventShowAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentorSubEventShowAllFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<SubEventHelperClass> eventList;
    showAllSubEventAdapter adapter;
    public MentorSubEventShowAllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentorSubEventShowAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MentorSubEventShowAllFragment newInstance(String param1, String param2) {
        MentorSubEventShowAllFragment fragment = new MentorSubEventShowAllFragment();
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
        View view = inflater.inflate(R.layout.fragment_mentor_sub_event_show_all, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_mentor_subevents_showall);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        eventList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("SubEvents");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear();
                for ( DataSnapshot snapshot1 : snapshot.getChildren() ){
                    SubEventHelperClass event = new SubEventHelperClass(snapshot1.child("name").getValue().toString(),snapshot1.child("parentEvent").getValue().toString(),snapshot1.child("venue").getValue().toString(),snapshot1.child("date").getValue().toString(),snapshot1.child("time").getValue().toString(), snapshot1.child("fileName").getValue().toString());
                    eventList.add(event);
                }
                adapter = new showAllSubEventAdapter(eventList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}