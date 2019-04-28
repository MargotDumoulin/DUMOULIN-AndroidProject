package com.example.android_project_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class MealEditor extends AppCompatActivity {

    private EditText ETDishName;
    private MealDbHelper mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_editor);

        // Find all relevant views that we will need to read user input from
        ETDishName = (EditText) findViewById(R.id.edit_dish_name);
        mDatabase = new MealDbHelper(this);


        //------------ToolBar--------------------------------//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    private void addMealEditor(){

            final String dishName = ETDishName.getText().toString();

            if(TextUtils.isEmpty(dishName)){
                Toast.makeText(MealEditor.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
            }
            else{


                Meal newMeal = new Meal(dishName);
                mDatabase.addMeal(newMeal);


            }
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.dining:
                Toast.makeText(this, "Saving..", Toast.LENGTH_SHORT).show();
                addMealEditor();
                finish();
                overridePendingTransition(0, 0);
                Intent intent = new Intent(MealEditor.this, MealActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }






}


