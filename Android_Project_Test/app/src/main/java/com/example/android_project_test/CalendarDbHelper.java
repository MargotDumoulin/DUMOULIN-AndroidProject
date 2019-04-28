package com.example.android_project_test;


import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;



public class CalendarDbHelper {


    DatabaseReference db;


    public CalendarDbHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public void addDay(Day day)
    {

            try
            {
                // Creating new user node, which returns the unique key value
                String Id = db.push().getKey();
                day.setId(Id);
                db.child(Id).setValue(day);


                //saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
            }
    }

    public void deleteDay(String id)
    {
        db.child(id).removeValue();
    }

}

