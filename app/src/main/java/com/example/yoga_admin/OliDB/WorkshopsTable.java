package com.example.yoga_admin.OliDB;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yoga_admin.OliDB.Models.Workshop;

import java.util.ArrayList;

/**
 * Represents a database manager for workshops.
 * Provides various methods for interacting with the workshops table in the database.
 */
public class WorkshopsTable extends DB {

    protected static final String TABLE_NAME = "workshops";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_WORKSHOP_NAME = "workshopName";
    protected static final String COLUMN_WORKSHOP_DESCRIPTION = "workshopDescription";
    protected static final String COLUMN_DATE = "date";
    protected static final String COLUMN_START_TIME = "startTime";
    protected static final String COLUMN_END_TIME = "endTime";
    protected static final String COLUMN_CAPACITY = "capacity";
    protected static final String COLUMN_PRICE = "price";
    protected static final String COLUMN_WORKSHOP_TYPE = "workshopType";
    protected static final String COLUMN_CREATED_AT = "created_at";
    protected static final String COLUMN_UPDATED_AT = "updated_at";
    private ArrayList<Workshop> loaded;
    private static WorkshopsTable instance = null;

    /**
     * Constructor for the WorkshopsTable class.
     *
     * @param app       The application instance.
     * @param dbName    The name of the database.
     * @param dbVersion The version of the database schema.
     */
    public WorkshopsTable(Application app, String dbName, int dbVersion) {
        super(app, dbName, dbVersion);
        loaded = new ArrayList<>();
        Log.d(LOG_TAG, "Yoga DB init");
    }

    /**
     * Initialises a DB instance for the provided application, DB name, and version.
     *
     * @param app       The application instance.
     * @param dbName    The name of the database.
     * @param dbVersion The version of the database schema.
     * @return          The initialised DB instance.
     */
    public static WorkshopsTable initFor(Application app, String dbName, int dbVersion) {
        if (instance == null) {
            instance = new WorkshopsTable(app, dbName, dbVersion);
        }
        return instance;
    }

    /**
     * Retrieves the DB instance.
     *
     * @return The DB instance.
     */
    public static WorkshopsTable getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Define the SQL statement for creating the table
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WORKSHOP_NAME + " TEXT NOT NULL, " +
                COLUMN_WORKSHOP_DESCRIPTION + " TEXT DEFAULT '', " +
                COLUMN_DATE + " DATE NOT NULL, " +
                COLUMN_START_TIME + " TIME NOT NULL, " +
                COLUMN_END_TIME + " TIME NOT NULL, " +
                COLUMN_CAPACITY + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " REAL NOT NULL, " +
                COLUMN_WORKSHOP_TYPE + " TEXT NOT NULL, " +
                COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        // Execute the SQL statement to create the table
        db.execSQL(createTableSQL);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Getter for the ID field name
     *
     * @return the field ID name as a string
     */
    @Override
    public String getIdFieldName() {
        return COLUMN_ID;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Loads the workshops from the database.
     */
    public void load() {
        loadAllRecords();
    }

    /**
     * Returns the loaded workshops.
     *
     * @return The loaded workshops.
     */
    @Override
    public ArrayList<Workshop> loaded() {
        return loaded;
    }

    /**
     * Retrieves a model object from a cursor.
     *
     * @param cursor The cursor to retrieve data from.
     * @return Returns the model based workshop record.
     * @throws IllegalArgumentException Returns the zero-based index for the given column name,
     *                                  or throws IllegalArgumentException if the column doesn't exist.
     */
    protected Workshop getObjectModelFromCursor(Cursor cursor) {
        Workshop workshop = Workshop.newFromInserted(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKSHOP_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKSHOP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_TIME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAPACITY)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKSHOP_TYPE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT))
        );
        return workshop;
    }

    @Override
    protected void addLoadedModelledRecord(Object record) {
        this.loaded().add((Workshop) record);
    }


    /**
     * Inserts a new workshop into the database.
     *
     * @param workshopName        The name of the yoga session.
     * @param workshopDescription The description of the task.
     * @param date                The date of the workshop.
     * @param startTime           The start time of the workshop.
     * @param endTime             The end time of the workshop.
     * @param capacity            The capacity of the workshop.
     * @param price               The price of the workshop.
     * @param workshopType        The type of the workshop.
     * @return The ID of the inserted workshop.
     */
    public long insertWorkshop(String workshopName, String workshopDescription, String date,
                               String startTime, String endTime, int capacity, float price,
                               String workshopType) {
        if (date == null) {
            Log.e(LOG_TAG, "Date cannot be null. Workshop not inserted.");
            return -1; // or throw an exception, depending on your error handling strategy
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKSHOP_NAME, workshopName);
        values.put(COLUMN_WORKSHOP_DESCRIPTION, workshopDescription);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);
        values.put(COLUMN_CAPACITY, capacity);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_WORKSHOP_TYPE, workshopType);
        long id = db.insert(getTableName(), null, values);
        db.close();
        StringBuilder msg = new StringBuilder("Inserted Yoga Workshop - ID ");
        msg.append(id);
        Log.i(LOG_TAG, msg.toString());
        return id;
    }

    // Inside WorkshopsTable class

    /**
     * Deletes a workshop by its position in the list.
     *
     * @param position The position of the workshop in the list.
     * @return True if the workshop is successfully deleted, false otherwise.
     */
    public boolean deleteByPosition(int position) {
        // Check if the list is empty or the position is invalid
        if (loaded.isEmpty() || position < 0 || position >= loaded.size()) {
            return false; // Return false indicating failure
        }

        // Get the ID of the workshop based on its position in the list
        int workshopId = loaded.get(position).getId();

        SQLiteDatabase db = getWritableDatabase();
        // Delete the workshop from the database using its ID
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(workshopId)});
        db.close();

        // Return true if at least one row is deleted, indicating successful deletion
        return rowsDeleted > 0;
    }
}

