package ru.javawebinar.topjava.service.postgres;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class PostgresDataJpaMealServiceTest extends MealServiceTest {
//        @Test
//    public Meal getWithUser(int id, int userId) throws Exception {
//        }
}
