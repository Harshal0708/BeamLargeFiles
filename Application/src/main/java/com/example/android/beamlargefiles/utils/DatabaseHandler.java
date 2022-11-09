package com.example.android.beamlargefiles.utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.beamlargefiles.models.Contact;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_CODE_NO = "code_no";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_COLLECTED_AMOUNT = "totalCollectedAmount";
    private static final String KEY_LAST_UPDATE_DATE = "lastupdateDate";
    private static final String KEY_MOBILE_NUMBER = "mobilenumber";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CODE_NO + " INTEGER ," +  KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT,"+ KEY_AMOUNT + " DOUBLE,"+ KEY_COMMENT + " TEXT," + KEY_COLLECTED_AMOUNT
                + " DOUBLE,"+ KEY_LAST_UPDATE_DATE + " TEXT," + KEY_MOBILE_NUMBER + " TEXT"  + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CODE_NO, contact.getSubhasad_code_no());
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_ADDRESS, contact.getAddress()); // Contact Phone
        values.put(KEY_AMOUNT, contact.getAmount());
        values.put(KEY_COMMENT, contact.getComment());
        values.put(KEY_COLLECTED_AMOUNT, contact.getTotalCollectrdAmount());
        values.put(KEY_LAST_UPDATE_DATE, contact.getLastpdateDate());
        values.put(KEY_MOBILE_NUMBER, contact.getMobile_number());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
//    Contact getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,KEY_CODE_NO,
//                        KEY_NAME, KEY_ADDRESS,KEY_AMOUNT,KEY_COMMENT,KEY_COLLECTED_AMOUNT,KEY_LAST_UPDATE_DATE,KEY_MOBILE_NUMBER }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//        return new Contact(
//                cursor.getInt(0),
//                cursor.getInt(1),
//                cursor.getString(2),
//                cursor.getString(3),
//                cursor.getInt(4),
//                cursor.getString(5),
//                cursor.getInt(6),
//                cursor.getString(7),
//                cursor.getString(8)
//        );
//    }

    // code to get all contacts in a list view
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
//                //int _id,int subhasad_code_no, String name1, String address1, double amount, String comment, double totalCollectrdAmount, String lastpdateDate
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setSubhasad_code_no(Integer.parseInt(cursor.getString(1)));
                contact.setName(cursor.getString(2));
                contact.setAddress(cursor.getString(3));
                contact.setAmount(Integer.parseInt(cursor.getString(4)));
                contact.setComment(cursor.getString(5));
                contact.setTotalCollectrdAmount(Integer.parseInt(cursor.getString(6)));
                contact.setLastpdateDate(cursor.getString(7));
                contact.setMobile_number(cursor.getString(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CODE_NO, contact.getSubhasad_code_no());
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_AMOUNT, contact.getAmount());
        values.put(KEY_COMMENT, contact.getComment());
        values.put(KEY_COLLECTED_AMOUNT, contact.getTotalCollectrdAmount());
        values.put(KEY_LAST_UPDATE_DATE, contact.getLastpdateDate());
        values.put(KEY_MOBILE_NUMBER, contact.getMobile_number());
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}