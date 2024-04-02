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

/**
 * Custom adapter for displaying workshop items in a ListView.
 */
public class WorkshopAdapter extends ArrayAdapter<Workshop> {

    private List<Workshop> workshops; // List of workshops to display
    private LayoutInflater inflater; // Layout inflater for inflating the item layout

    /**
     * Constructor for the WorkshopAdapter class.
     *
     * @param context   The context of the application.
     * @param workshops The list of workshops to display.
     */
    public WorkshopAdapter(Context context, List<Workshop> workshops) {
        super(context, 0, workshops);
        this.workshops = workshops;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Method to render the view for each workshop item in the ListView.
     *
     * @param position    The position of the item in the list.
     * @param convertView The view representing the item.
     * @param parent      The parent ViewGroup.
     * @return The view for the workshop item.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // Inflate the layout for the workshop item if it's not already inflated
            convertView = inflater.inflate(R.layout.custom_workshop_list_item, parent, false);
            // Initialise the view holder and bind views to its components
            viewHolder = new ViewHolder();
            viewHolder.textViewWorkshopName = convertView.findViewById(R.id.textViewWorkshopName);
            viewHolder.textViewWorkshopType = convertView.findViewById(R.id.textViewWorkshopType);
            viewHolder.textViewTeacher = convertView.findViewById(R.id.textViewTeacher);
            viewHolder.textViewDate = convertView.findViewById(R.id.textViewDate);
            viewHolder.imageButtonDelete = convertView.findViewById(R.id.imageButtonDelete);
            viewHolder.imageButtonView = convertView.findViewById(R.id.imageButtonView);
            convertView.setTag(viewHolder); // Set the tag to the view holder
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // Retrieve the view holder from the tag
        }

        Workshop workshop = workshops.get(position); // Get the workshop object at the specified position
        // Set the data to the corresponding views
        viewHolder.textViewWorkshopName.setText(workshop.getWorkshopName());
        viewHolder.textViewWorkshopType.setText(workshop.getWorkshopType());
        viewHolder.textViewTeacher.setText(workshop.getTeacher());
        viewHolder.textViewDate.setText(workshop.getDate());

        // Set tags for the delete and view buttons to identify their positions
        viewHolder.imageButtonDelete.setTag(position);
        viewHolder.imageButtonView.setTag(position);

        return convertView; // Return the configured view for the workshop item
    }

    /**
     * Inner class to hold the views for each workshop item.
     */
    static class ViewHolder {
        TextView textViewWorkshopName;
        TextView textViewWorkshopType;
        TextView textViewTeacher;
        TextView textViewDate;
        ImageButton imageButtonDelete;
        ImageButton imageButtonView; // Add ImageButton for viewing details
    }
}
