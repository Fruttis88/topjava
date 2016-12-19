package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;


public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Override
    public Meal create(Meal meal) {
        return repository.createOrEdit(meal);
    }

    @Override
    public Meal edit(Meal meal, int userId) throws NotFoundException {
        if (meal.getUserId().equals(userId)){
        return checkNotFound(repository.createOrEdit(meal), "this meal");}
        else {
            throw new NotFoundException("Julik detected! Put your hands on the yellow circles!!!");
        }
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllBetween(userId, startDate, endDate);
    }
}
