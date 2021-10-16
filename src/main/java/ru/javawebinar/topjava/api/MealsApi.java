package ru.javawebinar.topjava.api;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealsApi {
    List<Meal> getMeals();
}
