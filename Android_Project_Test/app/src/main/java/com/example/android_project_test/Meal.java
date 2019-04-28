package com.example.android_project_test;

public class Meal {

    private int id;
    private String dishName;

    Meal() {}

    Meal(int id, String dishName)
    {
        this.id = id;
        this.dishName = dishName;
    }

    Meal(String dishName)
    {
        this.dishName = dishName;
    }



    public String getDishName() {
        return dishName;
    }

    public int getId() {
        return id;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }



    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return dishName;
    }
}
