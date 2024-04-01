// AddworkshopActivity.java
package com.example.yoga_admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddWorkshopActivity extends AppCompatActivity {

    private EditText editTextWorkshopName;
    private EditText editTextWorkshopDescription;
    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private Spinner spinnerCapacity;
    private Spinner spinnerWorkshopType;
    private Spinner spinnerTeacher;
    private EditText editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);

        // Initialise views
        editTextWorkshopName = findViewById(R.id.editTextWorkshopName);
        editTextWorkshopDescription = findViewById(R.id.editTextWorkshopDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        spinnerCapacity = findViewById(R.id.spinnerCapacity);
        editTextPrice = findViewById(R.id.editTextPrice);
        spinnerWorkshopType = findViewById(R.id.spinnerWorkshopType);
        spinnerTeacher = findViewById(R.id.spinnerTeacher);

        // Set up Spinner for teacher selection
        ArrayAdapter<CharSequence> teacherAdapter = ArrayAdapter.createFromResource(this,
                R.array.teacher_names, android.R.layout.simple_spinner_item);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher.setAdapter(teacherAdapter);

        // Set up Spinner for capacity
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.capacity_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCapacity.setAdapter(adapter);

        // Set up Spinner for workshop type
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.yoga_types_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkshopType.setAdapter(typeAdapter);

        // Set OnClickListener for the date EditText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set OnClickListener for start time EditText
        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(editTextStartTime);
            }
        });

        // Set OnClickListener for end time EditText
        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(editTextEndTime);
            }
        });

        // Set click listener for "Add workshop" button
        Button buttonAddWorkshop = findViewById(R.id.buttonAddWorkshop);
        buttonAddWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkshop();
            }
        });
    }

    // Method to show DatePickerDialog
    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Create a Calendar instance to hold the selected date
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, monthOfYear, dayOfMonth);

                        // Format the selected date into "YYYY-MM-DD" format
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String selectedDate = sdf.format(selectedCalendar.getTime());

                        // Update the EditText with the formatted date
                        editTextDate.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Method to show the TimePickerDialog
    private void showTimePickerDialog(final EditText editText) {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create and show TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update EditText with selected time
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        editText.setText(selectedTime);
                    }
                }, hour, minute, true); // true for 24-hour time format
        timePickerDialog.show();
    }

    // Method to add the workshop
    private void addWorkshop() {
        // Get all the input values
        String workshopName = editTextWorkshopName.getText().toString().trim();
        String workshopDescription = editTextWorkshopDescription.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String startTime = editTextStartTime.getText().toString().trim();
        String endTime = editTextEndTime.getText().toString().trim();
        String capacity = spinnerCapacity.getSelectedItem().toString();
        String workshopType = spinnerWorkshopType.getSelectedItem().toString();
        String priceString = editTextPrice.getText().toString().trim();
        String selectedTeacher = spinnerTeacher.getSelectedItem().toString();

        // Check if all the required fields are filled
        if (!workshopName.isEmpty() && !workshopDescription.isEmpty() && !date.isEmpty() &&
                !startTime.isEmpty() && !endTime.isEmpty() && !capacity.isEmpty() && !priceString.isEmpty()) {
            try {
                float price = Float.parseFloat(priceString);
                // Check if price is greater than zero
                if (price > 0) {
                    // Create an intent to pass back to the MainActivity
                    Intent intent = new Intent();
                    // Add all data as intent extras
                    intent.putExtra("workshopName", workshopName);
                    intent.putExtra("workshopDescription", workshopDescription);
                    intent.putExtra("date", date);
                    intent.putExtra("startTime", startTime);
                    intent.putExtra("endTime", endTime);
                    intent.putExtra("capacity", capacity);
                    intent.putExtra("workshopType", workshopType);
                    intent.putExtra("price", price);
                    intent.putExtra("teacher", selectedTeacher);

                    // Log the intent extras
                    Log.d("AddWorkshopActivity", "Intent extras: " + intent.getExtras().toString());

                    // Set the result and finish the activity
                    setResult(RESULT_OK, intent);
                    finish(); // Finish the activity and return to MainActivity

                    // Show a toast indicating the workshop was successfully added
                    Toast.makeText(this, workshopName + " added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
        }
    }

}
