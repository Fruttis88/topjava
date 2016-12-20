package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class MealServlet extends HttpServlet {

    private MealRepository repository;
    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepositoryImpl();
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (meal.getId() == null) {
            mealRestController.create(meal);
        } else {
            mealRestController.edit(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("userId");
        if (userId != null){
        AuthorizedUser.setId(Integer.valueOf(userId));}

        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", mealRestController.getAll());
            request.getRequestDispatcher("meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);

        } else if ("filterMeals".equals(action)){
            LocalDate startDate = request.getParameter("startDate").isEmpty() ? LocalDate.MIN : LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = request.getParameter("endDate").isEmpty() ? LocalDate.MAX : LocalDate.parse(request.getParameter("endDate"));
            LocalTime startTime = request.getParameter("startTime").isEmpty() ? LocalTime.MIN : LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = request.getParameter("endTime").isEmpty() ? LocalTime.MAX : LocalTime.parse(request.getParameter("endTime"));
            request.setAttribute("meals", mealRestController.getBetween(startTime, endTime, startDate, endDate));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

}
