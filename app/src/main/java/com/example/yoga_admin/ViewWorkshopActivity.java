package com.example.yoga_admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_admin.R;

public class ViewWorkshopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workshop);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Extract workshop details from the intent
        int workshopId = intent.getIntExtra("workshop_id", -1);
        String workshopName = intent.getStringExtra("workshop_name");
        String workshopDescription = intent.getStringExtra("workshop_description");
        String date = intent.getStringExtra("date");
        String startTime = intent.getStringExtra("start_time");
        String endTime = intent.getStringExtra("end_time");
        int capacity = intent.getIntExtra("capacity", 0);
        float price = intent.getFloatExtra("price", 0.0f);
        String workshopType = intent.getStringExtra("workshop_type");
        // Extract other details as needed

        // Display the workshop details in the appropriate TextViews or other views
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
}
