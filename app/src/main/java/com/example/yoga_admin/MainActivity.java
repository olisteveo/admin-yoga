package com.example.yoga_admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_admin.OliDB.Models.Workshop;
import com.example.yoga_admin.OliDB.WorkshopsTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> workshopList;
    private ArrayAdapter<String> adapter;
    private static final int ADD_WORKSHOP_REQUEST = 1; // Request code for AddWorkshopActivity
    private static final int EDIT_WORKSHOP_REQUEST = 2; // Request code for EditWorkshopActivity
    private WorkshopsTable workshopsDB; // Preloaded Workshops from the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise workshop list and adapter with custom layout
        workshopList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.custom_workshop_list_item, R.id.textViewWorkshopName, workshopList);
        // Set up the ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);


        // Set click listener for list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editOrDeleteWorkshop(position);
            }
        });

        // Add bottom border to EditText

        // Initialise Workshops database
        workshopsDB = WorkshopsTable.initFor(getApplication(), "workshops_db", 1);
        Log.d("MainActivity", "onCreate");

        // If any Workshops are loaded from the database, add them to the list
        workshopsDB.load();
        if (!workshopsDB.loaded().isEmpty()) {
            for (Workshop workshop : workshopsDB.loaded()) {
                workshopList.add(workshop.getWorkshopName());
            }
            adapter.notifyDataSetChanged();
        }
    }

    // Method to handle editing or deleting a workshop
    private void editOrDeleteWorkshop(final int position) {
        // Placeholder method for editing or deleting a workshop
        final String workshop = workshopList.get(position);
        // For simplicity, currently showing a toast message with workshop details
        Toast.makeText(this, "Selected workshop: " + workshop + "\nPosition: " + position, Toast.LENGTH_SHORT).show();
    }

    // Method to delete a workshop
    public void deleteWorkshop(View view) {
        View listItem = (View) view.getParent(); // Find the parent view of the delete button
        final int position = ((ListView) findViewById(R.id.listView)).getPositionForView(listItem); // Find the index of the list item
        showDeleteConfirmationDialog(position); // Show the delete confirmation dialog
    }

    // Method to show delete confirmation dialog
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Context context = this;
        builder.setMessage("Are you sure you want to delete this workshop?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If user confirms deletion, delete the workshop
                        if (workshopsDB.deleteByPosition(position)) {
                            String workshopName = workshopList.get(position);
                            workshopList.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, "Deleted " + workshopName, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If user cancels deletion, do nothing
                    }
                })
                .create()
                .show();
    }

    // Method to navigate to AddWorkshopActivity
    public void navigateToAddWorkshop(View view) {
        Intent intent = new Intent(this, AddWorkshopActivity.class);
        startActivityForResult(intent, ADD_WORKSHOP_REQUEST); // Start AddWorkshopActivity with request code
    }

    // Method to navigate to EditWorkshopActivity
    public void navigateToEditWorkshop(View view) {
        Intent intent = new Intent(this, EditWorkshopActivity.class);
        startActivity(intent);
    }

    // Handle the result from AddWorkshopActivity and EditWorkshopActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_WORKSHOP_REQUEST) {
                // Extract the workshop details from the intent
                String workshopName = data.getStringExtra("workshopName");
                String workshopDescription = data.getStringExtra("workshopDescription");
                String date = data.getStringExtra("date");
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
                int capacity = data.getIntExtra("capacity", 0);
                float price = data.getFloatExtra("price", 0.0f);
                String workshopType = data.getStringExtra("workshopType");

                // Construct the workshop string
                String workshop = workshopName + ": " + workshopDescription;

                // Add the workshop to the top of the list
                workshopList.add(0, workshop);
                adapter.notifyDataSetChanged();

                // Insert workshop into the database
                workshopsDB.insertWorkshop(workshopName, workshopDescription, date, startTime, endTime, capacity, price, workshopType);
            } else if (requestCode == EDIT_WORKSHOP_REQUEST) {
                // Handle the result from EditWorkshopActivity if needed
            }
        }
    }

    // Method to add bottom border to EditText

}
