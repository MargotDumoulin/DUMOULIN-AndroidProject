package com.example.android_project_test;

import android.provider.BaseColumns;

public final class MealContract {

    private MealContract() {}

    public static class MealEntry implements BaseColumns
    {
          public static final String TABLE_NAME = "meals";
          public static final String ID = BaseColumns._ID;
          public static final String COLUMN_DISH_NAME = "dish_name";

    }
}
