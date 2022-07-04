package com.example.dairaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class showAllSubEventAdapter extends RecyclerView.Adapter<showAllSubEventAdapter.ViewHolder>{

    ArrayList<SubEventHelperClass> EventsList;

    public showAllSubEventAdapter(ArrayList<SubEventHelperClass> eventsList) {
        EventsList = eventsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(EventsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return EventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textviewshowdata);
        }

    }
}
