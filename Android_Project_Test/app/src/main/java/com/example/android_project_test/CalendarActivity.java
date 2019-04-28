package com.example.android_project_test;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project_test.CalendarAdapter.OnListItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements OnListItemClickListener {

    RecyclerView calendarRV;
    RecyclerView.Adapter calendarRVAdapter;
    ProgressDialog mProgressDialog;
    private DatabaseReference db;
    private CalendarDbHelper mDatabase;
    ArrayList<Day> listDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);

        //------------ToolBar--------------------------------//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //-----------RecyclerView------------------------------//
        calendarRV = findViewById(R.id.rv_calendar);
        calendarRV.hasFixedSize();
        calendarRV.setLayoutManager(new LinearLayoutManager(this));


        //Real initialization of the database
        db = FirebaseDatabase.getInstance().getReference();
        mDatabase = new CalendarDbHelper(db);
        listDays = new ArrayList<Day>();

        getDataFromServer();


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, CalendarEditor.class);
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
            case R.id.calendar:
                Toast.makeText(this, "Stay right where you are !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weather:
                finish();
                overridePendingTransition(0, 0);
                Intent intent2 = new Intent(CalendarActivity.this, WeatherActivty.class);
                startActivity(intent2);
                break;
            case R.id.dining:
                finish();
                overridePendingTransition(0, 0);
                Intent intent3 = new Intent(CalendarActivity.this, MealActivity.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void MyCustomAlertDialog(final String id){
        final Dialog MyDialog = new Dialog(CalendarActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);

        Button delete = (Button)MyDialog.findViewById(R.id.delete);
        Button close = (Button)MyDialog.findViewById(R.id.close);

        delete.setEnabled(true);
        close.setEnabled(true);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.deleteDay(id);
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
    public void onListItemClick(int clickedItemIndex) {

    }


    //-----RETRIEVING DATA FROM SERVER
    public void getDataFromServer()
    {
        showProgressDialog();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                    {
                        Day day=postSnapShot.getValue(Day.class);
                        listDays.add(day);
                        String name = day.getDayName();

                    }
                }
                hideProgressDialog();
                showingEverything();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(CalendarActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    public void showingEverything()
    {
        final List<Day> allDays = listDays;
        if(allDays.size() > 0){
            calendarRV.setVisibility(View.VISIBLE);

            calendarRVAdapter = new CalendarAdapter(this, allDays, new OnListItemClickListener() {
                @Override
                public void onListItemClick(int clickedItemIndex) {
                    String id = allDays.get(clickedItemIndex).getId();
                    MyCustomAlertDialog(id);
                }
            });
            calendarRV.setAdapter(calendarRVAdapter);
        }else {
            calendarRV.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }
}

