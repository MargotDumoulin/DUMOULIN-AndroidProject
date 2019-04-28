package com.example.android_project_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivty extends AppCompatActivity {

    private static final String APP_ID = "fb29a8e41198ee482ecbcee49234daeb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        double lat = 55.86066, lon = 9.85034;
        String units = "metric";
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s", lat, lon, units, APP_ID);

        TextView textView = (TextView) findViewById(R.id.textView);
        new GetWeatherTask(textView).execute(url);

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
            case R.id.weather:
                Toast.makeText(this, "Stay right where you are !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dining:
                finish();
                overridePendingTransition(0, 0);
                Intent intent2 = new Intent(WeatherActivty.this, MealActivity.class);
                startActivity(intent2);
                break;
            case R.id.calendar:
                finish();
                overridePendingTransition(0, 0);
                Intent intent3 = new Intent(WeatherActivty.this, CalendarActivity.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
