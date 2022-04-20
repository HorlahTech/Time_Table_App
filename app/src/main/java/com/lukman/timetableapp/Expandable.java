package com.lukman.timetableapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Expandable extends BaseExpandableListAdapter {
    public static final String LOG_TAG = Expandable.class.getSimpleName();
ArrayList<String> listGroup;
HashMap <String, ArrayList<String>> listChild;
public String  sChild;

    public Expandable(ArrayList<String> listGroup, HashMap<String, ArrayList<String>> listChild) {
        this.listGroup = listGroup;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listChild.get(listGroup.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listChild.get(listGroup.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_expandable_list_item_1,
                viewGroup, false);
        TextView textView =view.findViewById(android.R.id.text1);
        String sGroup = String.valueOf(getGroup(i));
        textView.setText(sGroup);
       textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.parseColor("#006634"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_selectable_list_item
        ,viewGroup,false);
        TextView textView = view.findViewById(android.R.id.text1);
         sChild = String.valueOf(getChild(i, i1));
        textView.setText(sChild);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setPadding(200, 0,0, 0);
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

