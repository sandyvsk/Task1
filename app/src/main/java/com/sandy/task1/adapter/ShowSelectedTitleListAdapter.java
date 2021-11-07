package com.sandy.task1.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sandy.task1.R;
import com.sandy.task1.controller.AddTitleListListener;
import com.sandy.task1.controller.RemoveTitleListListener;

import java.util.List;

public class ShowSelectedTitleListAdapter extends RecyclerView.Adapter<ShowSelectedTitleListAdapter.ViewHolder> {
    Context context;
    List<String> titlelist;
    View view;
    RemoveTitleListListener removeTitleListListener;

    public ShowSelectedTitleListAdapter(Context context, List<String> titlelist, RemoveTitleListListener removeTitleListListener) {
        this.context = context;
        this.titlelist=titlelist;
        this.removeTitleListListener = removeTitleListListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selected_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final  String title = titlelist.get(position);
        if (title!= null) {
            holder.txt_added_list.setText(title);
        }
        else {

            holder.txt_added_list.setText("");
        }

        holder.img_close.setOnClickListener(v -> {

            removeSelectedTitle(title,position);
        });

    }

    private void removeSelectedTitle(String title, int position) {

        titlelist.remove(title);

        notifyDataSetChanged();

        removeTitleListListener.removeTitleListListener(title);

    }

    @Override
    public int getItemCount() {
        return titlelist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_added_list;
        ImageView img_close;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_added_list = itemView.findViewById(R.id.txt_added_list);
            img_close = itemView.findViewById(R.id.img_close);


        }
    }
}