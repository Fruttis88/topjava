package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    List<Meal> getAll();
    void createOrEdit(Meal meal);
    void delete(int id);
    Meal getById(int id);
}

