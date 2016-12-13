package com.example.hou.taskapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hou on 12/8/2016.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    Context context;
    List<TaskData> taskList = new ArrayList<>();
    LayoutInflater inflater;
    Listener listener;

    public ListAdapter(Context context, List<TaskData> taskList) {

        this.context = context;
        this.taskList = taskList;
        this.listener = (Listener) context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = inflater.inflate(R.layout.list_row, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        holder.deleteImage.setTag(position);
        holder.nameText.setText(taskList.get(position).name);

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.nameToChange(taskList.get((Integer) v.getTag()).name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameText;
        ImageView deleteImage;


        public ListViewHolder(View itemView){
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.name);
            deleteImage = (ImageView) itemView.findViewById(R.id.deleteButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = taskList.get(getLayoutPosition())._id;
            String name = taskList.get(getLayoutPosition()).name;
            String description = taskList.get(getLayoutPosition()).description;
            Intent intent = new Intent(context, TaskDetailActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("name",name);
            intent.putExtra("description",description);
            context.startActivity(intent);
        }
    }
}
