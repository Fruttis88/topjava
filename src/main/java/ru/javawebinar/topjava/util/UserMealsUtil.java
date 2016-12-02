package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> map = new HashMap<>();
        for (UserMeal x : mealList) {
            map.merge(x.getDateTime().toLocalDate(), x.getCalories(), (v, nv) -> v + nv);
        }

        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal z : mealList) {
            if (TimeUtil.isBetween(z.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceed(z.getDateTime(), z.getDescription(), z.getCalories(), caloriesPerDay < map.getOrDefault(z.getDateTime().toLocalDate(), 0)));
            }
        }
        return list;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay){

        Map<LocalDate, Integer> map = mealList.stream().collect(Collectors.toMap(p -> p.getDateTime().toLocalDate(), UserMeal::getCalories,
                (calories1, calories2) -> calories1 + calories2));

        List<UserMealWithExceed> list = new ArrayList<>();
        mealList.stream()
                .filter(p -> TimeUtil.isBetween(p.getDateTime().toLocalTime(), startTime, endTime))
                .map(p -> list.add(new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), (map.get(p.getDateTime().toLocalDate()) > caloriesPerDay))))
                .collect(Collectors.toList());
        return list;
    }
}
