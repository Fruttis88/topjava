package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;
//    public static void main(String[] args) {
//        MealRestController in = new MealRestController();
//        List<MealWithExceed> list = in.getAll();
//        List<Meal> list = MealsUtil.MEALS;
//        in.repository.forEach((id, val) -> System.out.println(val));
//    }
    public List<MealWithExceed> getAll(){
        LOG.info("getAll fo userId " + String.valueOf(AuthorizedUser.id()));
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getBetween(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate){
        LOG.info("getAllBetweenTime");
        return MealsUtil.getFilteredWithExceeded(service.getAllBetween(AuthorizedUser.id(), startDate, endDate), startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        meal.setUserId(AuthorizedUser.id());
        LOG.info("create " + meal);
        return service.createOrEdit(meal, AuthorizedUser.id());
    }

    public Meal edit(Meal meal){
        LOG.info("edit " + meal);
        return service.createOrEdit(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

}
