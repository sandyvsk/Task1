package com.sandy.task1.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sandy.task1.R;
import com.sandy.task1.controller.AddTitleListListener;

import java.util.List;

public class ShowTitleListAdapter extends RecyclerView.Adapter<ShowTitleListAdapter.ViewHolder> {
    Context context;
    List<String> titlelist;
    View view;
    AddTitleListListener addTitleListListener;

    public ShowTitleListAdapter(Context context,List<String> titlelist,AddTitleListListener addTitleListListener) {
        this.context = context;
        this.titlelist=titlelist;
        this.addTitleListListener = addTitleListListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_retrieve_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final  String title = titlelist.get(position);
        if (title!= null) {
            holder.txt_title.setText(title);
        }
        else {

            holder.txt_title.setText("");
        }

        holder.cb_title.setOnClickListener(v -> {

            addSelectedTitle(title,position);
        });

    }

    private void addSelectedTitle(String title, int position) {

        titlelist.remove(title);

        notifyDataSetChanged();

        addTitleListListener.addTitleListListener(title);

    }

    @Override
    public int getItemCount() {
        return titlelist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        CheckBox cb_title;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            cb_title = itemView.findViewById(R.id.cb_title);


        }
    }
}