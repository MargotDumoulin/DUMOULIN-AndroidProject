package com.example.android_project_test;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_project_test.MealAdapter.OnListItemClickListener;

import java.util.List;

public class MealActivity extends AppCompatActivity implements OnListItemClickListener{


    RecyclerView mealView;
    RecyclerView.Adapter mAdapter;
    private MealDbHelper mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_main);

        //------------ToolBar--------------------------------//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //-----------RecyclerView------------------------------//
        mealView = (RecyclerView)findViewById(R.id.rv_meal);
        mealView.hasFixedSize();
        mealView.setLayoutManager(new LinearLayoutManager(this));


        mDatabase = new MealDbHelper(this);
        test();

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealActivity.this, MealEditor.class);
                startActivity(intent);
            }
        });


    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.dining:
                Toast.makeText(this, "Stay right where you are !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weather:
                finish();
                overridePendingTransition(0, 0);
                Intent intent2 = new Intent(MealActivity.this, WeatherActivty.class);
                startActivity(intent2);
                break;
            case R.id.calendar:
                finish();
                overridePendingTransition(0, 0);
                Intent intent3 = new Intent(MealActivity.this, CalendarActivity.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void MyCustomAlertDialog(final int id){
        final Dialog MyDialog = new Dialog(MealActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);

        Button delete = (Button)MyDialog.findViewById(R.id.delete);
        Button close = (Button)MyDialog.findViewById(R.id.close);

        delete.setEnabled(true);
        close.setEnabled(true);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.deleteMeal(id);
                MyDialog.cancel();

                //refresh the activity
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });

        MyDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void test()
    {
        showingEverything();
    }

    public void showingEverything()
    {
        final List<Meal> allMeals = mDatabase.listMeals();

        if(allMeals.size() > 0){
            mealView.setVisibility(View.VISIBLE);
            mAdapter = new MealAdapter(this, allMeals, new OnListItemClickListener() {
                @Override
                public void onListItemClick(int clickedItemIndex) {
                    int id = allMeals.get(clickedItemIndex).getId();
                    MyCustomAlertDialog(id);
                }
            });
            mealView.setAdapter(mAdapter);
        }else {
            mealView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }
}
