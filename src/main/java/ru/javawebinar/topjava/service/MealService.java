package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(int userId, Meal meal) {
        meal.setUserId(userId);
        return repository.save(meal);
    }

    public void update(int userId, Meal meal) {
        meal.setUserId(userId);
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    public void delete(int userId, int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(userId, id), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}