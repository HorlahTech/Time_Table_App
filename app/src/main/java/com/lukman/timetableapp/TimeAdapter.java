package com.lukman.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;







public class TimeAdapter extends ArrayAdapter<TimeObject> {

    public TimeAdapter(@NonNull Context context, ArrayList<TimeObject> time) {
        super(context, 0, time);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if( listViewItem==null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.time_layout, parent, false);
        }
        TimeObject currentWord = getItem(position);
        TextView start = listViewItem.findViewById(R.id.ttstart);
        start.setText(currentWord.getTimeStart());
        TextView stop = listViewItem.findViewById(R.id.ttstop);
        stop.setText(currentWord.getTimeStop());
        return listViewItem;
    }
}

