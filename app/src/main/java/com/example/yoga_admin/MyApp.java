// MyApp.java
package com.example.yoga_admin;

import android.app.Application;
import android.util.Log;

import com.example.yoga_admin.OliDB.WorkshopsTable;

public class MyApp extends Application {

    // Database constants
    private static final String DATABASE_NAME = "yoga_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "MyApp"; // Log tag
    private WorkshopsTable workshopsDB;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Application onCreate()"); // Log application creation
        workshopsDB = WorkshopsTable.initFor(this, "workshops_db", 1);
        workshopsDB.load();
//        yogaDB = WorkshopsTable.initFor(this, DATABASE_NAME, DATABASE_VERSION); // Initialise database manager
//        yogaDB.load(); // Load tasks from the database
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "Application onTerminate()"); // Log application termination
    }
}
