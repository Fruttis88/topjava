package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;


public class AuthorizedUser {

    public static int id() {
        return 1;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
