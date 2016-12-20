package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;


public interface MealRepository {

    Meal createOrEdit(Meal Meal, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate);
}
