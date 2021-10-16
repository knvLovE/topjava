package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.api.MealsApi;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealsApiJavaImpl implements MealsApi {
    @Override
    public List<Meal> getMeals() {
        return MealsUtil.generateMeals();
    }
}
