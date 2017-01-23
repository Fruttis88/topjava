package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealController {

    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        int userId = AuthorizedUser.id();
        model.addAttribute(
                "meals",
                MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay())
        );
        return "meals";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request){
        int id = getId(request);
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
        return "redirect:meals";
    }
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        final Meal meal = service.get(getId(request), userId);
        request.setAttribute("meal", meal);
        return "meal";
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        int userId = AuthorizedUser.id();
        if (request.getParameter("id").isEmpty()) {
            checkNew(meal);
            service.save(meal, userId);
        } else {
            checkIdConsistent(meal, getId(request));
            service.update(meal, userId);
        }
        return "redirect:meals";
    }
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(HttpServletRequest request){
        int userId = AuthorizedUser.id();
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));

        List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay());

        request.setAttribute("meals", list);
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
