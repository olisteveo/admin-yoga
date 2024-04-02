package com.example.yoga_admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_admin.OliDB.Models.Workshop;
import com.example.yoga_admin.OliDB.WorkshopsTable;

public class ViewWorkshopDetailsActivity extends AppCompatActivity {
    private WorkshopsTable workshopsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workshop);

        // Retrieve workshop details from the intent
        Intent intent = getIntent();
        int id = (int) intent.getIntExtra("id", 1);
        Log.d("viewing", "ID: " + String.valueOf(id));
        workshopsDB = WorkshopsTable.getInstance();
        Workshop workshop = workshopsDB.getByID(id);
        Log.d("Loaded View", "ID: " + String.valueOf(workshop.getId()));
        String workshopName = workshop.getWorkshopName();
        String workshopDescription = workshop.getWorkshopDescription();
        String date = workshop.getDate();
        String startTime = workshop.getStartTime();
        String endTime = workshop.getEndTime();
        int capacity = workshop.getCapacity();
        float price = workshop.getPrice();
        String workshopType = workshop.getWorkshopType();

        // Display workshop details in corresponding text views
        TextView textViewWorkshopName = findViewById(R.id.textViewWorkshopName);
        textViewWorkshopName.setText(workshopName);

        TextView textViewWorkshopDescription = findViewById(R.id.textViewWorkshopDescription);
        textViewWorkshopDescription.setText(workshopDescription);

        TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(date);

        TextView textViewStartTime = findViewById(R.id.textViewStartTime);
        textViewStartTime.setText(startTime);

        TextView textViewEndTime = findViewById(R.id.textViewEndTime);
        textViewEndTime.setText(endTime);

        TextView textViewCapacity = findViewById(R.id.textViewCapacity);
        textViewCapacity.setText(String.valueOf(capacity));

        TextView textViewPrice = findViewById(R.id.textViewPrice);
        textViewPrice.setText(String.valueOf(price));

        TextView textViewWorkshopType = findViewById(R.id.textViewWorkshopType);
        textViewWorkshopType.setText(workshopType);
    }

    // Method to handle deleting a workshop
    public void deleteWorkshop(View view) {
        // Retrieve the position of the workshop item associated with the delete button
        int position = (int) view.getTag();

    }
}
