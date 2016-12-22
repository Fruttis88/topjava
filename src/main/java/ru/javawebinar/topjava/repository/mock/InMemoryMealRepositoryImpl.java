package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> createOrEdit(meal, 1));
    }


    @Override
    public Meal createOrEdit(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            if (!repository.get(meal.getId()).getUserId().equals(userId))
                return null;
        }
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }


    @Override
    public boolean delete(int id, int userId) {
        if(repository.get(id) != null && repository.get(id).getUserId().equals(userId)){
            repository.remove(id);
            return true;
        } else return false;
    }


    @Override
    public Meal get(int id, int userId) {
            if (repository.get(id) != null && repository.get(id).getUserId().equals(userId)) {
                return repository.get(id);
            } else {
                return null;
            }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream()
                .filter(meal -> (startDate == null || meal.getDate().compareTo(startDate) >= 0) && (endDate == null || meal.getDate().compareTo(endDate) <= 0))
                .collect(Collectors.toList());
    }
}