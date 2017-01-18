package ru.javawebinar.topjava.service.profiles;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
//        @Test
//    public Meal getWithUser(int id, int userId) throws Exception {
//        }
}
