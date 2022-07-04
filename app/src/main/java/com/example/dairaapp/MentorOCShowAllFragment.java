package com.example.dairaapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MentorOCShowAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentorOCShowAllFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<OCHelperClass> oclist;
    showAllOCAdapter adapter;
    FirebaseFirestore db;

    public MentorOCShowAllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentorOCShowAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MentorOCShowAllFragment newInstance(String param1, String param2) {
        MentorOCShowAllFragment fragment = new MentorOCShowAllFragment();
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
        View view = inflater.inflate(R.layout.fragment_mentor_o_c_show_all, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_mentor_oc_showall);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        oclist = new ArrayList<>();
        adapter = new showAllOCAdapter(oclist);
        recyclerView.setAdapter(adapter);
        db.collection("Users").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        OCHelperClass oc = null;
                        if (error!=null) {
                            Log.d("TAG", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                oc = dc.getDocument().toObject(OCHelperClass.class);
                            }
                            if (oc.getUserLevel().equals("3")){
                                oclist.add(oc);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        return view;
    }
}