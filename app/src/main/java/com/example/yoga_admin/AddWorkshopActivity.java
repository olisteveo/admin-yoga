// AddworkshopActivity.java
package com.example.yoga_admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddWorkshopActivity extends AppCompatActivity {

    private EditText editTextworkshopName;
    private EditText editTextworkshopDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);

        // Initialise views
        editTextworkshopName = findViewById(R.id.editTextWorkshopName);
        editTextworkshopDescription = findViewById(R.id.editTextWorkshopDescription);

        // Set click listener for "Add workshop" button
        Button buttonAddWorkshop = findViewById(R.id.buttonAddWorkshop);
        buttonAddWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkshop();
            }
        });
    }

    // Method to add the workshop
    private void addWorkshop() {
        String workshopName = editTextworkshopName.getText().toString().trim();
        String workshopDescription = editTextworkshopDescription.getText().toString().trim();

        // Check if workshop name is not empty
        if (!workshopName.isEmpty()) {
            // Pass data back to the MainActivity
            Intent intent = new Intent();
            intent.putExtra("workshopName", workshopName);
            intent.putExtra("workshopDescription", workshopDescription);
            setResult(RESULT_OK, intent);
            finish(); // Finish the activity and return to MainActivity
        } else {
            // Show a toast message if the workshop name is empty
            Toast.makeText(this, "Please enter a workshop name", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle back button press
    @Override
    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
        // Navigate back to MainActivity
        finish();
    }
}
