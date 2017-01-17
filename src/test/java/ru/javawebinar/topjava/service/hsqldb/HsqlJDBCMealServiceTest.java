package ru.javawebinar.topjava.service.hsqldb;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles({Profiles.HSQLDB, Profiles.JDBC})
public class HsqlJDBCMealServiceTest extends MealServiceTest {
}
