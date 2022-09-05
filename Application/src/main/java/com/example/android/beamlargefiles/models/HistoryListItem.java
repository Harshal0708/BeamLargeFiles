package com.example.android.beamlargefiles.models;

import java.util.ArrayList;

public class HistoryListItem {

    public String date;
    public ArrayList<Contact> items;

    public HistoryListItem(String date, ArrayList<Contact> items) {
        this.date = date;
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Contact> getItems() {
        return items;
    }

    public void setItems(ArrayList<Contact> items) {
        this.items = items;
    }

}