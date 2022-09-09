package com.example.android.beamlargefiles.models;

import java.util.ArrayList;

public class HistoryListItem implements Comparable<HistoryListItem> {

    public String date;
    public int date2Char;
    public ArrayList<Contact> items;

    public HistoryListItem(String date, ArrayList<Contact> items) {
        this.date = date;
        this.items = items;
    }


    public HistoryListItem(int date2Char,String date) {
        this.date2Char = date2Char;
        this.date = date;
    }

    public HistoryListItem(int date2Char,String date, ArrayList<Contact> items) {
        this.date2Char = date2Char;
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

    @Override
    public int compareTo(HistoryListItem historyListItem) {
        if (date2Char == historyListItem.date2Char)
            return 0;
        else if (date2Char > historyListItem.date2Char)
            return 1;
        else
            return -1;
    }
}