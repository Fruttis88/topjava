package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
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
        try {
            return repository.get(id).getUserId().equals(userId) && repository.remove(id) != null;
        } catch (NullPointerException e){
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            if (repository.get(id).getUserId().equals(userId)) {
                return repository.get(id);
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> list = new ArrayList<>(repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList()));
        return Optional.of(list).orElseGet(Collections::emptyList);
    }

    @Override
    public Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate) {
        List<Meal> list = getAll(userId).stream()
                .filter(meal -> (startDate == null || meal.getDate().compareTo(startDate) >= 0) && (endDate == null || meal.getDate().compareTo(endDate) <= 0))
//                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
        return Optional.of(list).orElseGet(Collections::emptyList);
    }
}