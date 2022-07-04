package com.example.dairaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class showAllRegisterationAdapter extends RecyclerView.Adapter<showAllRegisterationAdapter.ViewHolder>{

    ArrayList<RegisterationHelperClass> registerationList;

    public showAllRegisterationAdapter(ArrayList<RegisterationHelperClass> registerationList) {
        this.registerationList = registerationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_data2,parent,false);
        return new showAllRegisterationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView1.setText(registerationList.get(position).getParticipantName());
        holder.textView2.setText(registerationList.get(position).getEventName());
    }

    @Override
    public int getItemCount() {
        return registerationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textviewshowdata2);
            textView2 = itemView.findViewById(R.id.textviewshowdata3);
        }
    }
}
