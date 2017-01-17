package ru.javawebinar.topjava.service.postgres;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class PostgresJDBCMealServiceTest extends MealServiceTest {
}
