package com.lukman.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{
    Context context;
    List<Table> tableList;

    public TableAdapter(Context context, List<Table> tableList) {
        this.context = context;
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder  holder, int position) {
        if (tableList != null && tableList.size()> 0){



            Table table = tableList.get(position);
            holder.table_Day.setText(table.getDay());
            holder.table_Course.setText(table.getCourse());
            holder.table_Venue.setText(table.getVenue());
            holder.table_Time.setText(table.getTimes());
        }else {
            return;
        }

    }

    @Override
    public int getItemCount() {

        return tableList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_Day, table_Course, table_Venue, table_Time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            table_Day = itemView.findViewById(R.id.table_Day);
            table_Course = itemView.findViewById(R.id.table_Course);
            table_Venue = itemView.findViewById(R.id.table_Venue);
            table_Time = itemView.findViewById(R.id.table_Time);
        }
    }
}
