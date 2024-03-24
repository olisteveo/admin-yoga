package com.example.yoga_admin.OliDB;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yoga_admin.OliDB.Models.Workshop;

import java.util.ArrayList;

/**
 * Represents a database manager for tasks.
 * Provides various methods for interacting with the tasks table in the database.
 */
public class WorkshopsTable extends DB {

    protected static final String TABLE_NAME = "workshops";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_WORKSHOP_NAME = "workshop_name";
    protected static final String COLUMN_WORKSHOP_DESCRIPTION = "workshop_description";
    protected static final String COLUMN_COMPLETED = "completed";
    private ArrayList<Workshop> loaded;
    private static WorkshopsTable instance = null;

    /**
     * Constructor for the TasksTable class.
     *
     * @param app       The application instance.
     * @param dbName    The name of the database.
     * @param dbVersion The version of the database schema.
     */
    public WorkshopsTable(Application app, String dbName, int dbVersion) {
        super(app, dbName, dbVersion);
        loaded = new ArrayList<Workshop>();
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
        super.onCreate(db);
    }

    // Method overridden to append custom table column definitions
    @Override
    protected StringBuilder getCreateTableSQL() {
        return super.getCreateTableSQL()
                .append(COLUMN_WORKSHOP_NAME + " TEXT,")
                .append(COLUMN_WORKSHOP_DESCRIPTION + " TEXT DEFAULT '',")
                .append(COLUMN_COMPLETED + " INTEGER DEFAULT 0)")
                // close the opening parenthesis created from the super call that
                // added the definition of the primary key field
                .append(')');
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    /**
     * Getter for the ID field name
     * @return the field ID name as a string
     */
    @Override
    public String getIdFieldName()
    {
        return COLUMN_ID;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Loads the tasks from the database.
     */
    public void load() {
        loadAllRecords();
    }

    /**
     * Returns the loaded tasks.
     *
     * @return The loaded tasks.
     */
    @Override
    public ArrayList<Workshop> loaded() {
        return loaded;
    }

    /**
     * Retrieves a model object from a cursor.
     *
     * @param cursor                    The cursor to retrieve data from.
     * @return                          Returns the model based task record.
     * @throws IllegalArgumentException Returns the zero-based index for the given column name,
     *                                   or throws IllegalArgumentException if the column doesn't exist.
     */
    protected Workshop getObjectModelFromCursor(Cursor cursor)
    {
        Workshop workshop = Workshop.newFromInserted(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKSHOP_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKSHOP_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMPLETED))
        );
        return workshop;
    }

    @Override
    protected void addLoadedModelledRecord(Object record)
    {
        this.loaded().add((Workshop) record);
    }

    /**
     * Inserts a new task into the database.
     *
     * @param workshopName        The name of the yoga session.
     * @param workshopDescription The description of the task.
     * @param completed       The completion status of the task.
     * @return                The ID of the inserted task.
     */
    public long insertWorkshop(String workshopName, String workshopDescription, int completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKSHOP_NAME, workshopName);
        values.put(COLUMN_WORKSHOP_DESCRIPTION, workshopDescription);
        values.put(COLUMN_COMPLETED, completed);
        long id = getWritableDatabase().insert(getTableName(), null, values);
        Workshop workshop = Workshop.newFromInserted((int) id, workshopName, workshopDescription, completed);
        workshop.setId((int) id);
        db.close();
        StringBuilder msg = new StringBuilder("Inserted Yoga Workshop - ID ");
        msg.append(workshop.getId());
        Log.i(LOG_TAG, msg.toString());
        loaded.add(0, workshop);
        return id;
    }

    /**
     * Deletes a task from the database by its position in the list.
     *
     * @param position The position of the task in the tasks list.
     * @return         True if the task was successfully deleted, false otherwise.
     */
    public boolean deleteByPosition(int position) {
        return true;
    }

}
