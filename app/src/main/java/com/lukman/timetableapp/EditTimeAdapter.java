package com.lukman.timetableapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.util.ArrayList;

public class EditTimeAdapter extends RecyclerView.Adapter<EditTimeAdapter.MyViewHolder> {
    private static final String TAG = EditTimeAdapter.class.getSimpleName();
    private Context mcontext;
    private ArrayList<TimeObject> list;
    View view;

    EditTimeAdapter(Context context, ArrayList<TimeObject> list) {
        mcontext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.each_time, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TimeObject itemPosition = list.get(position);
        holder.tstart.setText(itemPosition.getTimeStart());
        holder.tstop.setText(itemPosition.getTimeStop());
        holder.moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mcontext, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        TimeObject temp = new TimeObject(itemPosition.getId(), itemPosition.getTimeStart(), itemPosition.getTimeStop());
                        ;
                        switch (item.getItemId()) {
                            case R.id.popup_edit:
                                Toast.makeText(mcontext, "Editing", Toast.LENGTH_SHORT).show();

                                Intent t_intent = new Intent(mcontext, EditTime.class);
                                t_intent.putExtra("id", String.valueOf(itemPosition.getId()));
                                view.getContext().startActivity(t_intent);
                                break;
                            case R.id.popup_delete:
                                Toast.makeText(mcontext, "Deleting", Toast.LENGTH_SHORT).show();
                                deleteItem(holder.getAdapterPosition(), itemPosition.getId());
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }

    private void deleteItem(int positionId, int id) {
        list.remove(positionId);
        notifyItemRemoved(positionId);
        notifyItemRangeChanged(positionId, list.size());
        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(mcontext);
        SQLiteDatabase db = timeTabledbHelper.getWritableDatabase();
        long newRowId = db.delete(TImeTableContract.TimeEntry.TABLE_NAME, "_id = ?",
                new String[]{String.valueOf(id)});


    }

    @Override

    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView moreIcon;
        TextView tstart, tstop;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moreIcon = itemView.findViewById(R.id.morepopup);
            tstart = itemView.findViewById(R.id.ttsstart);
            tstop = itemView.findViewById(R.id.ttsstop);

        }
    }
}
