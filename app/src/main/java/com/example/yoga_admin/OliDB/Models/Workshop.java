package com.example.yoga_admin.OliDB.Models;

/**
 * Represents a workshop in the database.
 */
public class Workshop {
    private int id;
    private String workshopName;
    private String workshopDescription;
    private String date;
    private String startTime;
    private String endTime;
    private int capacity;
    private float price;
    private String workshopType;
    private String teacher; // New field for teacher
    private String createdAt;
    private String updatedAt;

    // Constructors
    public Workshop() {
    }

    /**
     * Constructor to create a new workshop object with all fields.
     *
     * @param id                   The ID of the workshop.
     * @param workshopName         The name of the workshop.
     * @param workshopDescription  The description of the workshop.
     * @param date                 The date of the workshop.
     * @param startTime            The start time of the workshop.
     * @param endTime              The end time of the workshop.
     * @param capacity             The capacity of the workshop.
     * @param price                The price of the workshop.
     * @param workshopType         The type of the workshop.
     * @param teacher              The teacher of the workshop. (New field)
     * @param createdAt            The timestamp for creation of the workshop.
     * @param updatedAt            The timestamp for last update of the workshop.
     */
    public Workshop(int id, String workshopName, String workshopDescription, String date, String startTime, String endTime, int capacity, float price, String workshopType, String teacher, String createdAt, String updatedAt) {
        this.id = id;
        this.workshopName = workshopName;
        this.workshopDescription = workshopDescription;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.price = price;
        this.workshopType = workshopType;
        this.teacher = teacher;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getWorkshopDescription() {
        return workshopDescription;
    }

    public void setWorkshopDescription(String workshopDescription) {
        this.workshopDescription = workshopDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWorkshopType() {
        return workshopType;
    }

    public void setWorkshopType(String workshopType) {
        this.workshopType = workshopType;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility methods

    /**
     * Creates a new workshop object with basic details.
     *
     * @param workshopName        The name of the workshop.
     * @param workshopDescription The description of the workshop.
     * @param date                The date of the workshop.
     * @param startTime           The start time of the workshop.
     * @param endTime             The end time of the workshop.
     * @param capacity            The capacity of the workshop.
     * @param price               The price of the workshop.
     * @param workshopType        The type of the workshop.
     * @param teacher             The teacher of the workshop.
     * @return                    The new workshop object.
     */
    public static Workshop newFromBasic(String workshopName, String workshopDescription, String date, String startTime, String endTime, int capacity, float price, String workshopType, String teacher) {
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
        return workshop;
    }

/**
 * Creates a new workshop object from an existing record in the database.
 *
 * @param id                   The ID of the workshop.
 * @param workshopName         The
 * @param id                   The ID of the workshop.
 * @param workshopName         The name of the workshop.
 * @param workshopDescription  The description of the workshop.
 * @param date                 The date of the workshop.
 * @param startTime            The start time of the workshop.
 * @param endTime              The end time of the workshop.
 * @param capacity             The capacity of the workshop.
 * @param price                The price of the workshop.
 * @param workshopType         The type of the workshop.
 * @param teacher              The teacher of the workshop.
 * @param createdAt            The timestamp for creation of the workshop.
 * @param updatedAt            The timestamp for last update of the workshop.
 * @return                     The new workshop object.
 */
public static Workshop newFromInserted(int id, String workshopName, String workshopDescription, String date, String startTime, String endTime, int capacity, float price, String workshopType, String teacher, String createdAt, String updatedAt) {
    Workshop workshop = newFromBasic(workshopName, workshopDescription, date, startTime, endTime, capacity, price, workshopType, teacher);
    workshop.setId(id);
    workshop.setCreatedAt(createdAt);
    workshop.setUpdatedAt(updatedAt);
    return workshop;
}

    @Override
    public String toString() {
        return workshopName;
    }
}
