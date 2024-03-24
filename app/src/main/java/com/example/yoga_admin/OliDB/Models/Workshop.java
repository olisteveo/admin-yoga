package com.example.yoga_admin.OliDB.Models;

/**
 * Represents a task in the to-do list.
 */
public class Workshop {
    private int id; // Task ID
    private String workshopName; // Name of the task
    private String taskDescription; // Description of the task
    private int completed; // Flag indicating whether the task is completed or not

    // Constructor
    public Workshop() {
    }

    /**
     * Create a new task table model object.
     *
     * @param taskName        The name of the task.
     * @param taskDescription The description of the task.
     * @param completed       The completion status of the task.
     * @return                The new modeled task object.
     */
    public static Workshop newFromBasic(String taskName, String taskDescription, int completed)
    {
        Workshop workshop = new Workshop();
        workshop.setWorkshopName(taskName);
        workshop.setTaskDescription(taskDescription);
        workshop.setCompleted(completed);
        return workshop;
    }

    /**
     * Create a new task model object from an existing entry in the database.
     *
     * @param id              The ID of the task.
     * @param taskName        The name of the task.
     * @param taskDescription The description of the task.
     * @param completed       The completion status of the task.
     * @return                The new modeled task object.
     */
    public static Workshop newFromInserted(int id, String taskName, String taskDescription, int completed)
    {
        Workshop workshop = newFromBasic(taskName, taskDescription, completed);
        workshop.setId(id);
        return workshop;
    }

    // Getters and setters

    /**
     * Gets the ID of the task.
     *
     * @return The task ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the task.
     *
     * @param id The task ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the task.
     *
     * @return The task name.
     */
    public String getWorkshopName() {
        return workshopName;
    }

    /**
     * Sets the name of the task.
     *
     * @param workshopName The task name to set.
     */
    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    /**
     * Gets the description of the task.
     *
     * @return The task description.
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Sets the description of the task.
     *
     * @param taskDescription The task description to set.
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Gets the completion status of the task.
     *
     * @return The completion status of the task.
     */
    public int getCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed The completion status of the task to set.
     */
    public void setCompleted(int completed) {
        this.completed = completed;
    }

    /**
     * Returns a string representation of the task (its name).
     *
     * @return The task name.
     */
    @Override
    public String toString() {
        return getWorkshopName();
    }
}
