package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDaoImpl implements MealDao {

    private Map<Integer, Meal> map = new ConcurrentHashMap<>();
    private List<Meal> listOfMeals = MealsUtil.createMealList();
    AtomicInteger idGen = new AtomicInteger(0);

    public InMemoryMealDaoImpl() {
        for (Meal list : listOfMeals) {
            createOrEdit(list);
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void createOrEdit(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(idGen.incrementAndGet());
            map.put(meal.getId(), meal);
        } else {
            map.put(meal.getId(), meal);
        }
    }


    @Override
    public void delete(int id) {
        map.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return map.get(id);
    }
}
