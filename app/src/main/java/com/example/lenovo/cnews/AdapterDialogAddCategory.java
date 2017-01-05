package com.example.lenovo.cnews;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 28-08-2016.
 */
public class AdapterDialogAddCategory extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> data;
    CheckBox checkBox;



    public AdapterDialogAddCategory(Context context,  ArrayList<String> objects) {
        super(context, 0, objects);
        this.context = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_dialog_edit_categories,parent,false);
        checkBox = (CheckBox)v.findViewById(R.id.checkbox);
        TextView categoryName = (TextView)v.findViewById(R.id.textview_dialog_edit_categories);
        categoryName.setText(data.get(position));
        return v;
    }

}
