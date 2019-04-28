package com.example.android_project_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CalendarEditor extends AppCompatActivity  {

    private EditText ETDayName;
    private EditText ETDayDate;
    private CalendarDbHelper mDatabase;
    private MealDbHelper mDatabaseMeal;
    private DatabaseReference db;
    private List<Meal> listMealNames;
    private Meal meal1;
    private Meal meal2;
    private Meal meal3;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_editor);


        // Find all relevant views that we will need to read user input from
        ETDayName = (EditText) findViewById(R.id.edit_day_name);
        ETDayDate = (EditText) findViewById(R.id.edit_day_date);

        //Initialising calendar db
        db = FirebaseDatabase.getInstance().getReference();
        mDatabase = new CalendarDbHelper(db);

        //Initializing meal db
        mDatabaseMeal = new MealDbHelper(this);

        //------------ToolBar--------------------------------//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //-----------Spinner---------------------------------//
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);


        //getting all the Meal Names
        listMealNames = mDatabaseMeal.listMeals();


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<Meal> adapter = new ArrayAdapter<Meal>(this,
                android.R.layout.simple_spinner_item, listMealNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);

            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    meal1 = listMealNames.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    meal1 = listMealNames.get(parent.getFirstVisiblePosition());
                }
            });

            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    meal2 = listMealNames.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    meal2 = listMealNames.get(parent.getFirstVisiblePosition());
                }
            });

            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    meal3 = listMealNames.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    meal3 = listMealNames.get(parent.getFirstVisiblePosition());
                }
            });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void addDayEditor() {

        final String dayName = ETDayName.getText().toString();
        final String dayDate = ETDayDate.getText().toString();

        //TEST
        ArrayList<Meal> test = new ArrayList<Meal>();
        test.add(meal1);
        test.add(meal2);
        test.add(meal3);




        if (TextUtils.isEmpty(dayName)) {
            Toast.makeText(CalendarEditor.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
        } else {


            Day newDay = new Day(dayName, dayDate, test);
            mDatabase.addDay(newDay);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dining:
                Toast.makeText(this, "Saving..", Toast.LENGTH_SHORT).show();
                addDayEditor();
                finish();
                overridePendingTransition(0, 0);
                Intent intent = new Intent(CalendarEditor.this, CalendarActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);


    }





}

