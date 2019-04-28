package com.example.android_project_test;

import java.util.ArrayList;

public class Day {

    private String id;
    private String dayName;
    private String dayDate;
    private ArrayList<Meal> meals;

    Day(){}

    Day(String id, String dayName, String dayDate, ArrayList<Meal> meals) {
        this.id = id;
        this.dayName = dayName;
        this.dayDate = dayDate;
        this.meals = meals;

    }

    Day(String dayName, String dayDate, ArrayList<Meal> meals) {
        this.dayName = dayName;
        this.dayDate = dayDate;
        this.meals = meals;

    }

    Day(String dayName, String dayDate) {
        this.dayName = dayName;
        this.dayDate = dayDate;

    }

    public String getDayName() {
        return dayName;
    }
    public String getDayDate() { return dayDate; }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
