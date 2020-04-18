package com.healthylife.restapi.model;

public class Meal extends Food {
    private String typeOfMeal;
    private long date;

    public Meal(String name, double calories, double protein, double carbs, double fat, String typeOfMeal, long date) {
        super(name, calories, protein, carbs, fat);
        this.typeOfMeal = typeOfMeal;
        this.date = date;
    }

    public Meal() {

    }

    public String getTypeOfMeal() {
        return typeOfMeal;
    }

    public long getDate() {
        return date;
    }
}
