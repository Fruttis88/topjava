package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL8_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL9, MEAL7), service.getAll(ADMIN_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> meals = service.getBetweenDates(START_DATETIME.toLocalDate(), END_DATETIME.toLocalDate(), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1), meals);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> meals = service.getBetweenDateTimes(START_DATETIME, END_DATETIME, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1), meals);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL9, MEAL8, MEAL7), all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("NewDescription");
        updated.setCalories(777);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "NewMeal", 7777);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, MEAL9, MEAL8, MEAL7), service.getAll(ADMIN_ID));
    }




    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWrongUser() throws Exception {
        service.get(MEAL8_ID, USER_ID);
    }
    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() {
        service.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWrongUser() {
        service.delete(MEAL9_ID, USER_ID);
    }
    @Test(expected = NotFoundException.class)
    public void testUpdateWrongUser() throws Exception {
        service.update(new Meal(MEAL1), ADMIN_ID);
    }
}