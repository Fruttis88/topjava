package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;


public class AuthorizedUser {

    private static int id;

    public static int id() {
        return id;
    }

    public static void setId(int userId) {
        id = userId;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
