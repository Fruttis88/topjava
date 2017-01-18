package ru.javawebinar.topjava.service.profiles;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL4;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Autowired
    private MealService service;
    @Test
    public void testGetWithUser() throws Exception {
        MATCHER.assertEquals(MEAL4, service.getWithUser(MEAL1_ID + 3, USER_ID));
        UserTestData.MATCHER.assertEquals(USER, service.getWithUser(MEAL1_ID + 3, USER_ID).getUser());
    }
}
