package com.gmail.arnasrad.recyclerviewdemo.data;

/*
 * Data model ListItem
 *
 */

public class ListItem {
    // Example:
    // private final String dateAndTime
    // here 'final' means that the variable can only be initialized in constructor and
    // cannot be changed later on (for example, setter won't work on this variable)
    private String dateAndTime;
    private String message;
    private int colorResource;

    public ListItem(String dateAndTime, String message, int colorResource) {
        this.dateAndTime = dateAndTime;
        this.message = message;
        this.colorResource = colorResource;
    }

    public String getDateAndTime() {

        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }
}
