package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::createOrEdit);
    }

    @Override
    public Meal createOrEdit(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if(repository.get(id).getUserId().equals(userId)){
        try {
            repository.remove(id);
            return true;
        } catch (Exception e) {
            return false;
        }} else {
            throw new NotFoundException("This user hasn`t such meals");
        }
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId().equals(userId)){
        return repository.get(id);
        } else {
            throw new NotFoundException("This user hasn`t such meals");
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> currentUserMeals = null;
        for (Map.Entry<Integer, Meal> map: repository.entrySet()){
            if(map.getValue().getUserId().equals(userId)){
                currentUserMeals.add(map.getValue());
            }
        }
        currentUserMeals.sort(Comparator.comparing(meal -> meal.getDateTime()));
        return currentUserMeals;
    }

    @Override
    public Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate) {
        List<Meal> currentUserMeals = null;
        for (Map.Entry<Integer, Meal> map: repository.entrySet()){
            if(map.getValue().getUserId().equals(userId) && (map.getValue().getDate().compareTo(startDate) >= 0 && map.getValue().getDate().compareTo(endDate) <= 0)){
                currentUserMeals.add(map.getValue());
            }
        }
        currentUserMeals.sort(Comparator.comparing(meal -> meal.getDateTime()));
        return currentUserMeals;
    }
}

