package com.example.yoga_admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yoga_admin.OliDB.Models.Workshop;
import com.example.yoga_admin.R;
import com.example.yoga_admin.ViewWorkshopDetailsActivity;

import java.util.List;

public class WorkshopAdapter extends ArrayAdapter<Workshop> {

    private List<Workshop> workshops;
    private LayoutInflater inflater;

    public WorkshopAdapter(Context context, List<Workshop> workshops) {
        super(context, 0, workshops);
        this.workshops = workshops;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_workshop_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewWorkshopName = convertView.findViewById(R.id.textViewWorkshopName);
            viewHolder.textViewWorkshopType = convertView.findViewById(R.id.textViewWorkshopType);
            viewHolder.textViewTeacher = convertView.findViewById(R.id.textViewTeacher);
            viewHolder.textViewDate = convertView.findViewById(R.id.textViewDate);
            viewHolder.imageButtonDelete = convertView.findViewById(R.id.imageButtonDelete);
            viewHolder.imageButtonView = convertView.findViewById(R.id.imageButtonView); // Add this line
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Workshop workshop = workshops.get(position);
        viewHolder.textViewWorkshopName.setText(workshop.getWorkshopName());
        viewHolder.textViewWorkshopType.setText(workshop.getWorkshopType());
        viewHolder.textViewTeacher.setText(workshop.getTeacher());
        viewHolder.textViewDate.setText(workshop.getDate());

        // Set tag for the delete button
        viewHolder.imageButtonDelete.setTag(position);
        viewHolder.imageButtonView.setTag(position);

        return convertView;
    }

    static class ViewHolder {
        TextView textViewWorkshopName;
        TextView textViewWorkshopType;
        TextView textViewTeacher;
        TextView textViewDate;
        ImageButton imageButtonDelete;
        ImageButton imageButtonView;
    }
}
