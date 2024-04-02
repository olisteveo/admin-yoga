package com.example.yoga_admin;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yoga_admin.OliDB.Models.Workshop;
import com.example.yoga_admin.OliDB.WorkshopsTable;
import com.example.yoga_admin.adapters.WorkshopAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Activity for searching and viewing workshops.
 */
public class SearchWorkshopActivity extends AppCompatActivity {

    // Views
    private ListView listView;
    private WorkshopAdapter adapter;
    private List<Workshop> workshopList;
    private EditText editTextSearch;
    private EditText editTextDate;
    private Button buttonSearch;
    private TextView textViewNoResults;
    private WorkshopsTable workshopsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_workshop);

        // Initialize views
        listView = findViewById(R.id.listViewWorkshop);
        editTextSearch = findViewById(R.id.editTextSearch);
        editTextDate = findViewById(R.id.editTextDate);
        buttonSearch = findViewById(R.id.buttonSearch);

        textViewNoResults = findViewById(R.id.textViewNoResults);
        textViewNoResults.setVisibility(View.GONE);

        // Initialize workshop list
        workshopList = new ArrayList<>();

        // Initialize adapter
        adapter = new WorkshopAdapter(this, workshopList);
        listView.setAdapter(adapter);

        workshopsDB = WorkshopsTable.getInstance();

        // Set up text watcher for search EditText
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the list based on the entered text and selected date
                filterWorkshops(s.toString(), editTextDate.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        // Set up click listener for date EditText
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        // Set up click listener for search button
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform search when the search button is clicked
                performSearch();
            }
        });
    }

    /**
     * Handles click events on the "View Workshop" button.
     * @param view The view that was clicked.
     */
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

    /**
     * Filters the workshops based on the entered text and/or selected date.
     * @param searchText The text to search for.
     * @param selectedDate The selected date.
     */
    private void filterWorkshops(String searchText, String selectedDate) {
        Log.d("Filter", "Search Text: " + searchText);
        Log.d("Filter", "Selected Date: " + selectedDate);
        List<Workshop> filteredList = new ArrayList<>();
        for (Workshop workshop : workshopsDB.loaded()) {
            // Perform case-insensitive search on teacher name
            boolean teacherMatches = (searchText.length() > 0 && workshop.getTeacher().toLowerCase().contains(searchText.toLowerCase()));
            boolean dateMatches = (workshop.getDate().contains(selectedDate));
            Log.d("Filter", "Teacher: " + workshop.getTeacher() + ", Date: " + workshop.getDate());
            Log.d("Filter", "Teacher matches: " + String.valueOf(teacherMatches));
            Log.d("Filter", "Date matches: " + String.valueOf(dateMatches));
            if (teacherMatches || dateMatches) {
                filteredList.add(workshop);
            }
        }
        // Update the adapter with the filtered list
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
        StringBuilder log = new StringBuilder("Count: ");
        log.append(workshopList.size());
        // Update visibility of TextView based on filtered list size
        Log.d("Filtered", log.toString() );
        if (filteredList.isEmpty()) {
            textViewNoResults.setVisibility(View.VISIBLE);
        } else {
            textViewNoResults.setVisibility(View.GONE);
        }
    }

    /**
     * Performs the search based on the entered text and selected date.
     */
    private void performSearch() {
        String searchText = editTextSearch.getText().toString().trim();
        String selectedDate = editTextDate.getText().toString().trim();
        filterWorkshops(searchText, selectedDate);
    }

    /**
     * Handles the deletion of a workshop.
     * @param view The view associated with the delete button.
     */
    public void deleteWorkshop(View view) {
        // Retrieve the position of the workshop item associated with the delete button
        int position = (int) view.getTag();

        // Call the method to edit or delete the workshop
        editOrDeleteWorkshop(position);
    }

    /**
     * Displays an alert dialog to confirm the deletion of a workshop.
     * @param position The position of the workshop to be deleted.
     */
    private void editOrDeleteWorkshop(final int position) {
        final Workshop workshop = workshopList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this yoga class?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (workshopsDB.deleteByPosition(position)) {
                            workshopList.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(SearchWorkshopActivity.this, "Deleted " + workshop.getWorkshopName(), Toast.LENGTH_SHORT).show();
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

    /**
     * Shows a DatePickerDialog to select a date.
     */
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

                        // Filter the workshops based on the selected date
                        filterWorkshops(editTextSearch.getText().toString().trim(), selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Override onBackPressed to handle back button press
    @Override
    public void onBackPressed() {
        // Perform any additional actions before navigating back
        // For example, saving data or showing a confirmation dialog

        // Call super.onBackPressed() to allow normal back navigation
        super.onBackPressed();
    }
}
