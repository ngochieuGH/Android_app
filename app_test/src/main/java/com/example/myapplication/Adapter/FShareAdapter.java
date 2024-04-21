package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ItemClickListerner;
import com.example.myapplication.R;

public class FShareAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListerner listerner;
    private final Context context;
    public TextView file_title,nameShare, title, date;
    public FShareAdapter(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        file_title = itemView.findViewById(R.id.nameFile);
        nameShare = itemView.findViewById(R.id.nameShare);
        title = itemView.findViewById(R.id.title);
        date = itemView.findViewById(R.id.date);
    }

    @Override
    public void onClick(View v) {
        listerner.onClick(v, getAdapterPosition(), false);
    }
}
