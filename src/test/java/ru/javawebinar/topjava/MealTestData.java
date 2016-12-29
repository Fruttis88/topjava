package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final int MEAL3_ID = START_SEQ + 4;
    public static final int MEAL4_ID = START_SEQ + 5;
    public static final int MEAL5_ID = START_SEQ + 6;
    public static final int MEAL6_ID = START_SEQ + 7;
    public static final int MEALADD1_ID = START_SEQ + 8;
    public static final int MEALADD2_ID = START_SEQ + 9;
    public static final int MEALADD3_ID = START_SEQ + 10;

    public static final LocalDateTime START_DATETIME = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
    public static final LocalDateTime END_DATETIME = LocalDateTime.of(2015, Month.MAY, 30, 14, 0);



    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL4_ID, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL5 = new Meal(MEAL5_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL6 = new Meal(MEAL6_ID, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal MEALADD1 = new Meal(MEALADD1_ID, LocalDateTime.of(2015, Month.MAY, 28, 10, 0), "Завтрак", 510);
    public static final Meal MEALADD2 = new Meal(MEALADD2_ID, LocalDateTime.of(2015, Month.MAY, 28, 13, 0), "Обед", 500);
    public static final Meal MEALADD3 = new Meal(MEALADD3_ID, LocalDateTime.of(2015, Month.MAY, 28, 20, 0), "Ужин", 1000);


    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();
}
