package com.example.yoga_admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_admin.OliDB.Models.Workshop;
import com.example.yoga_admin.OliDB.WorkshopsTable;
import com.example.yoga_admin.adapters.WorkshopAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Workshop> workshopList;
    private WorkshopAdapter adapter;
    private static final int ADD_WORKSHOP_REQUEST = 1;
    private static final int EDIT_WORKSHOP_REQUEST = 2;
    private WorkshopsTable workshopsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise workshop list and adapter
        workshopList = new ArrayList<>();
        workshopsDB = WorkshopsTable.getInstance();
        // Initialise the workshops database and load data
        workshopList.addAll(workshopsDB.loaded());
        adapter = new WorkshopAdapter(this, workshopList);

        // Set adapter for the ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

//        adapter.notifyDataSetChanged();

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("dafuq", "position: " + String.valueOf(position));

                editOrDeleteWorkshop(position);
            }
        });

        // Set the click listener for the search button
        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSearchWorkshop();
            }
        });
    }

    // The method to navigate to SearchWorkshopActivity
    private void navigateToSearchWorkshop() {
        Intent intent = new Intent(this, SearchWorkshopActivity.class);
        startActivity(intent);
    }

    public void onViewWorkshopClick(View view) {
        Intent intent = new Intent(this, ViewWorkshopDetailsActivity.class);
        // Retrieve the position of the workshop item associated with the delete button
        int position = (int) view.getTag();
        Log.d("view", "position: " + String.valueOf(position));

        Workshop workshop = workshopList.get(position);
        intent.putExtra("id", workshop.getId());
        Log.d("view", "ID: " + String.valueOf(workshop.getId()));

        startActivity(intent);
    }

    // Method to handle the editing or deleting of a workshop
    private void editOrDeleteWorkshop(final int position) {
        final Workshop workshop = workshopList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this yoga class?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (workshopsDB.deleteWorkshop(workshop)) {
                            workshopList.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Deleted " + workshop.getWorkshopName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .create()
                .show();
    }

    // Method to handle adding a workshop
    public void navigateToAddWorkshop(View view) {
        Intent intent = new Intent(this, AddWorkshopActivity.class);
        startActivityForResult(intent, ADD_WORKSHOP_REQUEST);
    }

    // Method to handle editing a workshop -needs work
    public void navigateToEditWorkshop(View view) {
        Intent intent = new Intent(this, EditWorkshopActivity.class);
        startActivity(intent);
    }

    // Method to handle deleting a workshop
    public void deleteWorkshop(View view) {
        // Retrieve the position of the workshop item associated with the delete button
        int position = (int) view.getTag();

        // Call the method to edit or delete the workshop
        editOrDeleteWorkshop(position);
    }

    // Method to handle the result of adding a workshop
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_WORKSHOP_REQUEST) {
            String workshopName = data.getStringExtra("workshopName");
            String workshopDescription = data.getStringExtra("workshopDescription");
            String date = data.getStringExtra("date");
            String startTime = data.getStringExtra("startTime");
            String endTime = data.getStringExtra("endTime");
            int capacity = data.getIntExtra("capacity", 0);
            float price = data.getFloatExtra("price", 0.0f);
            String workshopType = data.getStringExtra("workshopType");
            String teacher = data.getStringExtra("teacher");

            // Create a new Workshop object and set its properties
            Workshop workshop = new Workshop();
            workshop.setWorkshopName(workshopName);
            workshop.setWorkshopDescription(workshopDescription);
            workshop.setDate(date);
            workshop.setStartTime(startTime);
            workshop.setEndTime(endTime);
            workshop.setCapacity(capacity);
            workshop.setPrice(price);
            workshop.setWorkshopType(workshopType);
            workshop.setTeacher(teacher);

            // Check if the workshop was successfully inserted into the database
            long insertedId = workshopsDB.insertWorkshop(workshop);
            if (insertedId != -1) {
                // Set the ID of the workshop after it's inserted into the database
                workshop.setId((int) insertedId);

                // Add the new workshop to the list and notify the adapter
                workshopList.add(workshop);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to add workshop", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
