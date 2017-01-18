package ru.javawebinar.topjava.service.profiles;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;
    @Test
    public void testGetWithMeal() throws Exception{
        MATCHER.assertEquals(ADMIN, service.getWithMeal(ADMIN_ID));
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2), service.getWithMeal(ADMIN_ID).getMeals());
    }
}
