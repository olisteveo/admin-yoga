// MainActivity.java
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

import com.example.yoga_admin.API.Requests.PingRequest;
import com.example.yoga_admin.OliDB.Models.Workshop;
import com.example.yoga_admin.OliDB.WorkshopsTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> workshopList;
    private ArrayAdapter<String> adapter;
    private static final int ADD_workshop_REQUEST = 1; // Request code for AddWorkshopActivity
    private static final int EDIT_workshop_REQUEST = 2; // Request code for EditWorkshopActivity
    private EditText editText;
    private WorkshopsTable WorkshopsDB; // Preloaded Workshops from the database

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

        // Find the EditText
        editText = findViewById(R.id.editText);

        // Set click listener for list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editOrDeleteworkshop(position);
            }
        });

        // Add bottom border to EditText
        addBottomBorder();

//        // Initialise Workshops database
//        WorkshopsDB = WorkshopsTable.getInstance();
//        Log.d("MyApp", "Activity onCreate()");
//
//        // If any Workshops are loaded from the database, add them to the list
//        if (!WorkshopsDB.loaded().isEmpty()) {
//            for (Workshop workshop : WorkshopsDB.loaded()) {
//                Log.d("Workshops", workshop.getWorkshopName());
//                workshopList.add(WorkshopsDB.loaded().indexOf(workshop), workshop.getWorkshopName());
//            }
//        }
    }

    // Method to add workshop
    public void addworkshop(View view) {
        String workshop = editText.getText().toString().trim();

        if (!workshop.isEmpty()) {
            // Add workshop to the beginning of the list
            workshopList.add(0, workshop);
            adapter.notifyDataSetChanged();
            editText.getText().clear();
            WorkshopsTable.getInstance().insertWorkshop(workshop, "", 0); // Insert workshop into the database
        } else {
            Toast.makeText(this, "Please enter a workshop", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle editing or deleting a workshop
    private void editOrDeleteworkshop(final int position) {
        // Placeholder method for editing or deleting a workshop
        final String workshop = workshopList.get(position);
        // For simplicity, currently showing a toast message with workshop details
        Toast.makeText(this, "Selected workshop: " + workshop + "\nPosition: " + position, Toast.LENGTH_SHORT).show();
    }

    // Method to delete a workshop
    public void deleteworkshop(View view) {
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
                        if (WorkshopsTable.getInstance().deleteByPosition(position)) {
                            String workshopName = workshopList.get(position).toString();
                            workshopList.remove(position);
                            adapter.notifyDataSetChanged();
                            StringBuilder msg = new StringBuilder("Deleted ");
                            msg.append(workshopName);
                            Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show();
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
        PingRequest request = new PingRequest(this);
        request.makeRequest();
//        Intent intent = new Intent(this, AddWorkshopActivity.class);
//        startActivityForResult(intent, ADD_workshop_REQUEST); // Start AddworkshopActivity with request code
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
            if (requestCode == ADD_workshop_REQUEST) {
                // Extract the workshop details from the intent
                String workshopName = data.getStringExtra("workshopName");
                String workshopDescription = data.getStringExtra("workshopDescription");

                // Construct the workshop string
                String workshop = workshopName + ": " + workshopDescription;

                // Add the workshop to the top of the list
                WorkshopsTable.getInstance().insertWorkshop(workshopName, workshopDescription, 0); // Insert workshop into the database
                workshopList.add(0, workshop);
                adapter.notifyDataSetChanged();
            } else if (requestCode == EDIT_workshop_REQUEST) {
                // Handle the result from EditworkshopActivity if needed
            }
        }
    }

    // Method to add bottom border to EditText
    private void addBottomBorder() {
        // Set bottom border color and height
        Drawable drawable = getResources().getDrawable(R.drawable.edittext_bottom_border);
        drawable.setColorFilter(Color.parseColor("#FF4081"), PorterDuff.Mode.SRC_ATOP);
        editText.setBackground(drawable);
    }
}
