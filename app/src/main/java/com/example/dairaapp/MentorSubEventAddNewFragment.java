package com.example.dairaapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MentorSubEventAddNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentorSubEventAddNewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText editTextName, editTextVenue, editTextDate, editTextTime;
    Button buttonUpload, buttonAdd;

    ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ProgressBar progressBar;
    DatabaseReference db;

    Spinner spinnerEvent;
    DatabaseReference reference;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MentorSubEventAddNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentorSubEventAddNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MentorSubEventAddNewFragment newInstance(String param1, String param2) {
        MentorSubEventAddNewFragment fragment = new MentorSubEventAddNewFragment();
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
        View view = inflater.inflate(R.layout.fragment_mentor_sub_event_add_new, container, false);

        spinnerEvent = view.findViewById(R.id.spinnerevent_mentor_subevent_addnew);
        reference = FirebaseDatabase.getInstance().getReference("Events");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spinnerEvent.setAdapter(adapter);
        listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for ( DataSnapshot snapshot1 : snapshot.getChildren() ) {
                    list.add(snapshot1.child("name").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editTextName = view.findViewById(R.id.edittextname_mentor_subevent_addnew);
        editTextVenue = view.findViewById(R.id.edittextvenue_mentor_subevent_addnew);
        editTextDate = view.findViewById(R.id.edittextdate_mentor_subevent_addnew);
        editTextTime = view.findViewById(R.id.edittexttime_mentor_subevent_addnew);
        buttonAdd = view.findViewById(R.id.buttonadd_mentor_subevent_addnew);
        buttonUpload = view.findViewById(R.id.buttonupload_mentor_subevent_addnew);
        imageView = view.findViewById(R.id.imageviewpic_mentor_subevent_addnew);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f3700d"));
        actionBar.setBackgroundDrawable(colorDrawable);

        storage = FirebaseStorage.getInstance();

        db = FirebaseDatabase.getInstance().getReference("SubEvents");

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image for the sub event"), PICK_IMAGE_REQUEST);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                if (filePath!=null){
                    String result = null;
                    if (filePath.getScheme().equals("content")) {
                        Cursor cursor = getContext().getContentResolver().query(filePath, null, null, null, null);
                        try {
                            if (cursor != null && cursor.moveToFirst()) {
                                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    }
                    if (result == null) {
                        result = filePath.getPath();
                        int cut = result.lastIndexOf('/');
                        if (cut != -1) {
                            result = result.substring(cut + 1);
                        }
                    }
                    storageReference = storage.getReference(editTextName.getText().toString()+result);
                    ProgressDialog progressDialog
                            = new ProgressDialog(getContext());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    storageReference.putFile(filePath)
                            .addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        @Override
                                        public void onSuccess(
                                                UploadTask.TaskSnapshot taskSnapshot) {

                                            // Image uploaded successfully
                                            // Dismiss dialog
                                            progressDialog.dismiss();
                                            Toast
                                                    .makeText(getContext(),
                                                            "Image Uploaded!!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    // Error, Image not uploaded
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getContext(),
                                                    "Failed " + e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .addOnProgressListener(
                                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                                        // Progress Listener for loading
                                        // percentage on the dialog box
                                        @Override
                                        public void onProgress(
                                                UploadTask.TaskSnapshot taskSnapshot) {
                                            double progress
                                                    = (100.0
                                                    * taskSnapshot.getBytesTransferred()
                                                    / taskSnapshot.getTotalByteCount());
                                            progressDialog.setMessage(
                                                    "Uploaded "
                                                            + (int) progress + "%");
                                        }
                                    });

                    SubEventHelperClass subEventHelperClass = new SubEventHelperClass(editTextName.getText().toString(), spinnerEvent.getSelectedItem().toString(), editTextVenue.getText().toString(), editTextDate.getText().toString(), editTextTime.getText().toString(), editTextName.getText().toString()+result);
                    db.child(editTextName.getText().toString()).setValue(subEventHelperClass);
                    Toast.makeText(getContext(), "Sub Event Added Successfully", Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                    editTextVenue.setText("");
                    editTextDate.setText("");
                    editTextTime.setText("");
                    imageView.setImageDrawable(null);
                }
            }

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == Activity.RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap( getContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}