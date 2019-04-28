package com.example.android_project_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.android_project_test.MealContract.MealEntry.COLUMN_DISH_NAME;
import static com.example.android_project_test.MealContract.MealEntry.ID;

public class MealDbHelper extends SQLiteOpenHelper {

       private static final String  DATABASE_NAME = "meals.db";
       private static final int DATABASE_VERSION = 1;

       public MealDbHelper(Context context)
       {
           super(context, DATABASE_NAME, null, DATABASE_VERSION);
       }

       @Override
       public void onCreate(SQLiteDatabase db)
       {
           String SQL_CREATE_MEALS_TABLE = "CREATE TABLE  IF NOT EXISTS " + MealContract.MealEntry.TABLE_NAME + " ("
                   + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + COLUMN_DISH_NAME + " TEXT NOT NULL );";
              db.execSQL(SQL_CREATE_MEALS_TABLE);

       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP TABLE IF EXISTS " +  MealContract.MealEntry.TABLE_NAME);
           onCreate(db);
       }

       public List<Meal> listMeals(){
           String sql = "select * from " +  MealContract.MealEntry.TABLE_NAME;
           SQLiteDatabase db = this.getReadableDatabase();

           List<Meal> storeProducts = new ArrayList<>();
           Cursor cursor = db.rawQuery(sql, null);

           if(cursor.moveToFirst()){
               do {
                   int id = Integer.parseInt(cursor.getString(0));
                   String dish_name = cursor.getString(1);
                   storeProducts.add(new Meal(id, dish_name));
               } while (cursor.moveToNext());
           }
           cursor.close();
           return storeProducts;

       }
       public void addMeal(Meal meal){
           ContentValues values = new ContentValues();
           Log.d( "1" , meal.getDishName() );

           values.put(COLUMN_DISH_NAME, meal.getDishName());


           SQLiteDatabase db = this.getWritableDatabase();
           db.insert(MealContract.MealEntry.TABLE_NAME, null, values);
       }

       public void updateMeal(Meal meal){
           ContentValues values = new ContentValues();
           values.put(COLUMN_DISH_NAME, meal.getDishName());


           SQLiteDatabase db = this.getWritableDatabase();
           db.update(MealContract.MealEntry.TABLE_NAME, values, ID    + "    = ?", new String[] { String.valueOf(meal.getId())});
       }
       public Meal findProduct(String dishName){
           String query = "Select * FROM "    + MealContract.MealEntry.TABLE_NAME + " WHERE " + COLUMN_DISH_NAME + " = " + "dishName";
           SQLiteDatabase db = this.getWritableDatabase();

           Meal mMeal = null;
           Cursor cursor = db.rawQuery(query, null);

           if (cursor.moveToFirst()){
               int id = Integer.parseInt(cursor.getString(0));
               String dish_name = cursor.getString(1);
               mMeal = new Meal(id, dish_name);
           }
           cursor.close();
           return mMeal;
       }
       public void deleteMeal(int id){
           SQLiteDatabase db = this.getWritableDatabase();
           db.delete(MealContract.MealEntry.TABLE_NAME, ID    + "    = ?", new String[] { String.valueOf(id)});
       }


   }
